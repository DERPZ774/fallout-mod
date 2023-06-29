package com.derpz.nukaisles.event;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.block.entity.ModBlockEntities;
import com.derpz.nukaisles.client.renderer.BulletRenderer;
import com.derpz.nukaisles.client.renderer.NukaColaMachineBlockEntityRenderer;
import com.derpz.nukaisles.entity.ModEntityTypes;
import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.particle.custom.RadiationParticles;
import com.derpz.nukaisles.screen.ModMenuTypes;
import com.derpz.nukaisles.screen.NukaColaMachineScreen;
import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {


    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        //ModItemProperties.addCustomItemProperties();
        MenuScreens.register(ModMenuTypes.NUKA_COLA_MACHINE_MENU.get(), NukaColaMachineScreen::new);
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.NUKA_COLA_MACHINE.get(), NukaColaMachineBlockEntityRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BULLET.get(), BulletRenderer::new);

    }

    /*
    @SubscribeEvent
    public static void registerArmorRenderer(final EntityRenderersEvent.AddLayers event) {
        GeoArmorRenderer.registerArmorRenderer(UnderArmorItem.class, new UnderArmorRenderer());
        /// TODO: 8/19/2022 Armor renders here
    }

 */
@SubscribeEvent
public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
{
    //event.registerLayerDefinition(VAULT_SUIT, UnderArmorModel::createBodyLayer);
}



    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.RADIATION_PARTICLES.get(),
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
