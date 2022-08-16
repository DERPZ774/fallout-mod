package com.derpz.nukaisl.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab FALLOUT_TAB = new CreativeModeTab("fallout_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SCRAP_METAL.get());
        }
    };

    public static final CreativeModeTab NUKA_COLA_TAB = new CreativeModeTab("nuka_cola_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.NUKA_COLA.get());
        }
    };
    public static final CreativeModeTab MELEE_WEAPONS = new CreativeModeTab("melee_weapons_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COMMIE_WHACKER.get());
        }
    };
}
