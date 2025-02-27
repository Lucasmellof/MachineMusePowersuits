/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.powersuits.client.gui.modding.module.tweak;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.numina.util.string.MuseStringUtils;
import com.github.lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.google.common.util.concurrent.AtomicDouble;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DetailedSummaryFrame extends ScrollableFrame {
    protected AtomicInteger energy = new AtomicInteger(0);
    protected AtomicDouble armor = new AtomicDouble(0);
    protected ModularItemSelectionFrame itemSelectionFrame;

    public DetailedSummaryFrame(
            MusePoint2D topleft,
            MusePoint2D bottomright,
            Colour background,
            Colour topBorder,
            Colour bottomBorder,
            ModularItemSelectionFrame itemSelectionFrame) {
        super(topleft, bottomright, background, topBorder, bottomBorder);
        this.itemSelectionFrame = itemSelectionFrame;
    }

    @Override
    public void update(double mousex, double mousey) {
        energy.set(0);
        armor.set(0);

        for (EquipmentSlotType type : EquipmentSlotType.values()) {
            getMinecraft().player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> {
                        Pair<Integer, Integer> range = iModularItem.getRangeForCategory(EnumModuleCategory.ARMOR);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                iModularItem.getStackInSlot(i).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL));
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY));
                                });
                            }
                        }

                        range =  iModularItem.getRangeForCategory(EnumModuleCategory.ENERGY_STORAGE);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                energy.getAndAdd(iModularItem.getStackInSlot(i).getCapability(CapabilityEnergy.ENERGY).map((energyHandler) -> energyHandler.getEnergyStored()).orElse(0));
                            }
                        }
                    });
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)  {
        if (getMinecraft().player != null) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            int margin = 4;
            int nexty = (int) getRect().top() + margin + 4;
            MuseRenderer.drawShadowedStringCentered(matrixStack, I18n.get("gui.powersuits.equippedTotals"), (getRect().left() + getRect().right()) / 2, nexty);
            nexty += 10;

            // Max Energy
            String formattedValue = MuseStringUtils.formatNumberFromUnits(energy.get(), "FE");
            String name = I18n.get("gui.powersuits.energyStorage");
            double valueWidth = MuseRenderer.getStringWidth(formattedValue);
            double allowedNameWidth = getRect().width() - valueWidth - margin * 2;
            List<String> namesList = MuseStringUtils.wrapStringToVisualLength(name, allowedNameWidth);
            for (int i = 0; i < namesList.size(); i++) {
                MuseRenderer.drawShadowedString(matrixStack, namesList.get(i), getRect().left() + margin, nexty + 9 * i);
            }
            MuseRenderer.drawRightAlignedShadowedString(matrixStack, formattedValue, getRect().right() - margin, nexty + 9 * (namesList.size() - 1) / 2);
            nexty += 10 * namesList.size() + 1;

            // Armor points
            formattedValue = MuseStringUtils.formatNumberFromUnits(armor.get(), "pts");
            name = I18n.get("gui.powersuits.armor");
            valueWidth = MuseRenderer.getStringWidth(formattedValue);
            allowedNameWidth = getRect().width() - valueWidth - margin * 2;
            namesList = MuseStringUtils.wrapStringToVisualLength(name, allowedNameWidth);
            assert namesList != null;
            for (int i = 0; i < namesList.size(); i++) {
                MuseRenderer.drawShadowedString(matrixStack, namesList.get(i), getRect().left() + margin, nexty + 9 * i);
            }
            MuseRenderer.drawRightAlignedShadowedString(matrixStack, formattedValue, getRect().right() - margin, nexty + 9 * (namesList.size() - 1) / 2);
        }
    }
}
