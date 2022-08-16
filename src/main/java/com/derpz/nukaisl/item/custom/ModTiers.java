package com.derpz.nukaisl.item.custom;

import com.derpz.nukaisl.item.ModItems;
import net.minecraft.tags.BlockTags;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier SCRAP_METAL = new ForgeTier(2, 32, 6.0F, 2.0F, 5, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.SCRAP_METAL.get()));

    public static final ForgeTier CUSHION = new ForgeTier(1, 32, 6.0F, 2.0F, 5, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.SCRAP_METAL.get()));

}

/// TODO: 8/10/2022 Balance "CUSHION" tier, only knockback based doesn't need high dmg, speed, etc. (Dom can do this)