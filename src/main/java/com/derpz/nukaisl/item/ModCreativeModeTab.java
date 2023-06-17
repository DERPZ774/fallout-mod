package com.derpz.nukaisl.item;

import com.derpz.nukaisl.FalloutMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

    public static CreativeModeTab FALLOUT_TAB;
    public static CreativeModeTab COLA_TAB;
    public static CreativeModeTab MELEE_TAB;
   @SubscribeEvent
    public static void registerCreativeModeTabs(CreativeModeTabEvent.Register event) {
        FALLOUT_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "fallout_tab"),
             builder ->   builder.icon(() -> new ItemStack(ModItems.SCRAP_METAL.get())).title(Component.translatable("tab.nukaisl.fallout")).build());

        COLA_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "cola_tab"),
                builder ->   builder.icon(() -> new ItemStack(ModItems.NUKA_COLA.get())).title(Component.translatable("tab.nukaisl.cola")).build());

       MELEE_TAB = event.registerCreativeModeTab(new ResourceLocation(FalloutMod.MOD_ID, "melee_tab"),
               builder ->   builder.icon(() -> new ItemStack(ModItems.COMMIE_WHACKER.get())).title(Component.translatable("tab.nukaisl.melee")).build());
    }

}
