package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.block.ModBlocks;
import com.derpz.nukaisl.entity.ModEntityTypes;
import com.derpz.nukaisl.entity.client.TestRenderer;
import com.derpz.nukaisl.screen.ModMenuTypes;
import com.derpz.nukaisl.screen.NukaColaOpenerScreen;
import com.derpz.nukaisl.util.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SeparatePerspectiveModel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {


    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LAMP.get(), RenderType.cutout());
        ModItemProperties.addCustomItemProperties();
        MenuScreens.register(ModMenuTypes.NUKA_COLA_OPENER_MENU.get(), NukaColaOpenerScreen::new);
        EntityRenderers.register(ModEntityTypes.TEST.get(), TestRenderer::new);
    }
}
