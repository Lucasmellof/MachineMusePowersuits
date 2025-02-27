package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.client.gui.frame.GUISpacer;
import com.github.lehjr.numina.util.client.gui.frame.MultiRectHolderFrame;
import com.github.lehjr.numina.util.client.gui.frame.RectHolderFrame;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A containerless GUI version of the tab toggle widget holder
 *
 * TODO: replace Modular Item Selection frame with this?
 */
public class ModularItemSelectionFrame extends MultiRectHolderFrame {
    IChanged changed;

    final EquipmentSlotType[] equipmentSlotTypes = new EquipmentSlotType[]{
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET,
            EquipmentSlotType.MAINHAND,
            EquipmentSlotType.OFFHAND
    };

    public final List<ModularItemTabToggleWidget> tabButtons = Lists.newArrayList();
    public ModularItemTabToggleWidget selectedTab = null;

    public ModularItemSelectionFrame() {
        super(false, true, 30, 0);
        /** 6 widgets * 27 high each = 162 + 5 spacers at 3 each = 177 gui height is 200 so 23 to split */
        // top spacer
        addRect(new GUISpacer(30, 11));
        int i=0;
        // look for modular items
        for (EquipmentSlotType slotType : equipmentSlotTypes) {
            ModularItemTabToggleWidget widget = new ModularItemTabToggleWidget(slotType);
            tabButtons.add(widget);
            widget.setOnPressed(pressed -> {
                if (widget != selectedTab) {
                    this.selectedTab.setStateActive(false);
                    this.selectedTab = widget;
                    this.selectedTab.setStateActive(true);
                    this.onChanged();
                    disableContainerSlots();
                }});

            addRect(new RectHolderFrame(widget, 30, 27, RectHolderFrame.RectPlacement.CENTER_RIGHT) {
                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int button) {
                    return widget.mouseClicked(mouseX, mouseY, button);
                }

                @Override
                public boolean mouseReleased(double mouseX, double mouseY, int button) {
                    return widget.mouseReleased(mouseX, mouseY, button);
                }

                @Override
                public List<ITextComponent> getToolTip(int x, int y) {
                    return widget.getToolTip(x, y);
                }

                @Override
                public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
                    super.render(matrixStack, mouseX, mouseY, frameTime);
                    widget.render(matrixStack, mouseX, mouseY, frameTime);
                }
            });

            // spacer under each widget
            if (i < 5) {
                addRect(new GUISpacer(30, 3));

                // bottom spacer
            } else {
                addRect(new GUISpacer(30, 12));
                doneAdding();
            }
            i++;
        }
    }

    void disableContainerSlots() {
    }

    public ItemStack getModularItemOrEmpty() {
        return getSelectedTab().map(tab -> {
            ItemStack stack = getStack(tab.getSlotType());
            return getModularItemCapability(tab.getSlotType()).isPresent() ? stack : ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    public Optional<IModularItem> getModularItemCapability () {
        return getModularItemOrEmpty().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast);
    }

    public boolean playerHasModularItems() {
        return Arrays.stream(equipmentSlotTypes)
                .filter(type->getModularItemCapability(type)
                        .isPresent()).findFirst().isPresent();
    }

    Optional<IModularItem> getModularItemCapability (EquipmentSlotType type) {
        return getStack(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                .filter(IModularItem.class::isInstance)
                .map(IModularItem.class::cast);
    }

    @Nonnull
    ItemStack getStack(EquipmentSlotType type) {
        return getMinecraft().player.getItemBySlot(type);
    }

    public Optional<EquipmentSlotType> selectedType() {
        return getSelectedTab().map(tab ->tab.getSlotType());
    }

    public boolean selectedIsSlotHovered() {
        return getSelectedTab().map(tab->tab.isHovered()).orElse(false);
    }

    public Optional<ModularItemTabToggleWidget> getSelectedTab() {
        if (this.selectedTab == null) {
            this.selectedTab = this.tabButtons.get(0);
            this.selectedTab.setStateActive(true);
        }
        for (ModularItemTabToggleWidget widget : tabButtons) {
            if (widget != selectedTab) {
                widget.setStateActive(false);
            }
        }
        return Optional.ofNullable(selectedTab);
    }

    @Override
    public void update(double mouseX, double mouseY) {
        super.update(mouseX, mouseY);
        getSelectedTab();
    }

    public void setOnChanged(IChanged changed) {
        this.changed = changed;
    }

    public void onChanged() {
        if(this.isEnabled() && this.isVisible()) {
            refreshRects();
            if (changed != null) {
                this.changed.onChanged();
            }
        }
    }

    public interface IChanged {
        void onChanged();
    }
}