package com.derpz.nukaisl;

import com.derpz.nukaisl.block.ModBlocks;
import com.derpz.nukaisl.block.entity.ModBlockEntities;
import com.derpz.nukaisl.config.FalloutModClientConfigs;
import com.derpz.nukaisl.config.FalloutModCommonConfigs;
import com.derpz.nukaisl.effect.ModEffects;
import com.derpz.nukaisl.enchantment.ModEnchantments;
import com.derpz.nukaisl.entity.ModEntityTypes;
import com.derpz.nukaisl.entity.client.TestRenderer;
import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.particle.ModParticles;
import com.derpz.nukaisl.recipe.ModRecipes;
import com.derpz.nukaisl.screen.ModMenuTypes;
import com.derpz.nukaisl.screen.NukaColaOpenerScreen;
import com.derpz.nukaisl.sound.ModSounds;
import com.derpz.nukaisl.util.ModItemProperties;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.SeparatePerspectiveModel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FalloutMod.MOD_ID)
public class FalloutMod {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "nukaisl";

    public FalloutMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        ModEnchantments.register(eventBus);
        ModSounds.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);


        ModRecipes.register(eventBus);
        ModParticles.register(eventBus);
        ModEffects.register(eventBus);
        ModEntityTypes.register(eventBus);

        eventBus.addListener(this::setup);


        GeckoLib.initialize();

        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FalloutModClientConfigs.SPEC, "nukaisl-client.toml");
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FalloutModCommonConfigs.SPEC, "nukaisl-common.toml");
        /// TODO: 8/14/2022 Uncomment when config is present.

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }



    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        event.enqueueWork(ModMessages::register);
    }

}
/// TODO: 8/10/2022 Make Cummie Whacker Dev only