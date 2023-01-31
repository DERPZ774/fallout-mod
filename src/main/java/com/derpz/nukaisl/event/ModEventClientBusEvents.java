package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.block.ModBlocks;
import com.derpz.nukaisl.client.RadHudOverlay;
import com.derpz.nukaisl.entity.ModEntityTypes;
import com.derpz.nukaisl.entity.client.TestRenderer;
import com.derpz.nukaisl.entity.client.armor.UnderArmorRenderer;
import com.derpz.nukaisl.item.custom.UnderArmorItem;
import com.derpz.nukaisl.particle.ModParticles;
import com.derpz.nukaisl.particle.custom.RadiationParticles;
import com.derpz.nukaisl.screen.ModMenuTypes;
import com.derpz.nukaisl.screen.NukaColaMachineScreen;
import com.derpz.nukaisl.screen.NukaColaOpenerScreen;
import com.derpz.nukaisl.util.KeyBinding;
import com.derpz.nukaisl.util.ModItemProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {


    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.LAMP.get(), RenderType.cutout());
        //ModItemProperties.addCustomItemProperties();
        MenuScreens.register(ModMenuTypes.NUKA_COLA_OPENER_MENU.get(), NukaColaOpenerScreen::new);
        MenuScreens.register(ModMenuTypes.NUKA_COLA_MACHINE_MENU.get(), NukaColaMachineScreen::new);
        EntityRenderers.register(ModEntityTypes.TEST.get(), TestRenderer::new);
    }

    @SubscribeEvent
    public static void registerArmorRenderer(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(UnderArmorItem.class, new UnderArmorRenderer());
        /// TODO: 8/19/2022 Armor renders here
    }

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.RADIATION_PARTICLES.get(),
                RadiationParticles.Provider::new);
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.DRINKING_KEY);
    }

    /*@SubscribeEvent
    public static void  registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("rads", RadHudOverlay.HUD_RADS);
    }
     */
    /// ToDo : Re-add hud when fixed
}
