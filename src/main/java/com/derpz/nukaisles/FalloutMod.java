package com.derpz.nukaisles;

import com.derpz.nukaisles.block.ModBlocks;
import com.derpz.nukaisles.block.entity.ModBlockEntities;
import com.derpz.nukaisles.effect.ModEffects;
import com.derpz.nukaisles.enchantment.ModEnchantments;
import com.derpz.nukaisles.entity.ModEntityTypes;
import com.derpz.nukaisles.item.ModCreativeModeTab;
import com.derpz.nukaisles.item.ModItems;
import com.derpz.nukaisles.loot.ModLootModifiers;
import com.derpz.nukaisles.networking.ModMessages;
import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.recipe.ModRecipes;
import com.derpz.nukaisles.screen.ModMenuTypes;
import com.derpz.nukaisles.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FalloutMod.MOD_ID)
public class FalloutMod {
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "nukaisles";

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
        ModLootModifiers.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        GeckoLib.initialize();

        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, FalloutModClientConfigs.SPEC, "nukaisles-client.toml");
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FalloutModCommonConfigs.SPEC, "nukaisles-common.toml");
        /// TODO: 8/14/2022 Uncomment when config is present.

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        eventBus.addListener(this::addCreative);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
       // ItemBlockRenderTypes.setRenderLayer(ModBlocks.NUKA_COLA_MACHINE.get(), RenderType.cutout());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("War, war never changes");

        event.enqueueWork(ModMessages::register);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        /// TODO: 6/16/2023 Add all the items to their perspective tabs
        if(event.getTab() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SCRAP_METAL);
        }
    }

}
/// TODO: 8/24/2022 Get started on gun system