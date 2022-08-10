package com.derpz.dfm.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab FALLOUT_TAB = new CreativeModeTab("fallout_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SCRAP_METAL.get());
        }
    };
}
