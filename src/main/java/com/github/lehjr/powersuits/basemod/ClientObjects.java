package com.github.lehjr.powersuits.basemod;

import com.github.lehjr.powersuits.client.control.KeybindKeyHandler;
import com.github.lehjr.powersuits.client.event.ClientTickHandler;
import com.github.lehjr.powersuits.client.event.ModelBakeEventHandler;
import com.github.lehjr.powersuits.client.event.RenderEventHandler;
import com.github.lehjr.powersuits.client.gui.modding.module.craft_install_salvage.CraftInstallSalvageGui;
import com.github.lehjr.powersuits.client.render.entity.LuxCapacitorEntityRenderer;
import com.github.lehjr.powersuits.client.render.entity.PlasmaBoltEntityRenderer;
import com.github.lehjr.powersuits.client.render.entity.RailGunBoltRenderer;
import com.github.lehjr.powersuits.client.render.entity.SpinningBladeEntityRenderer;
import com.github.lehjr.powersuits.event.LogoutEventHandler;
import com.github.lehjr.powersuits.event.PlayerLoginHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientObjects {
	public static void register() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(ModelBakeEventHandler.INSTANCE::onModelBake);
		modEventBus.addListener(RenderEventHandler.INSTANCE::preTextureStitch);

		MinecraftForge.EVENT_BUS.register(RenderEventHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new KeybindKeyHandler());
		MinecraftForge.EVENT_BUS.register(new LogoutEventHandler());

		MinecraftForge.EVENT_BUS.addListener(PlayerLoginHandler::onPlayerLoginClient);// just to populated keybinds -_-

		RenderingRegistry.registerEntityRenderingHandler(MPSObjects.RAILGUN_BOLT_ENTITY_TYPE.get(), RailGunBoltRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(MPSObjects.LUX_CAPACITOR_ENTITY_TYPE.get(), LuxCapacitorEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(MPSObjects.PLASMA_BALL_ENTITY_TYPE.get(), PlasmaBoltEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(MPSObjects.SPINNING_BLADE_ENTITY_TYPE.get(), SpinningBladeEntityRenderer::new);

		//        ScreenManager.registerFactory(MPSObjects.MODULE_CONFIG_CONTAINER_TYPE, TinkerModuleGui::new);
		ScreenManager.register(MPSObjects.SALVAGE_CRAFT_CONTAINER_TYPE.get(), CraftInstallSalvageGui::new);
	}
}
