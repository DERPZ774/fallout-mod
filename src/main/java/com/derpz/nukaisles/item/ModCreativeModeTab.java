package com.derpz.nukaisles.item;

import com.derpz.nukaisles.FalloutMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCreativeModeTab {
 /*   public static CreativeModeTab FALLOUT_TAB = new CreativeModeTab("fallout_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SCRAP_METAL.get());
        }
    };

    public static CreativeModeTab NUKA_COLA_TAB = new CreativeModeTab("nuka_cola_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.NUKA_COLA.get());
        }
    };
    public static  CreativeModeTab MELEE_WEAPONS = new CreativeModeTab("melee_weapons_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COMMIE_WHACKER.get());
        }
    };*/

    public static CreativeModeTab FALLOUT_TAB, COLA_TAB, MELEE_TAB;
   @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        FALLOUT_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "fallout"), builder -> builder.icon(ModItems.SCRAP_METAL.get()::getDefaultInstance).title(Component.translatable("tab.nukaisles.fallout")).displayItems((featureFlags, tab) -> {
            for (RegistryObject<Item> registryObject : ModItems.ITEMS.getEntries()) {
                tab.accept(registryObject.get());
            }
        }));

        COLA_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "cola_tab"),
                builder ->   builder.icon(() -> new ItemStack(ModItems.NUKA_COLA.get())).title(Component.translatable("tab.nukaisles.cola")).build());

       MELEE_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "melee_tab"),
               builder ->   builder.icon(() -> new ItemStack(ModItems.COMMIE_WHACKER.get())).title(Component.translatable("tab.nukaisles.melee")).build());
    }

}
