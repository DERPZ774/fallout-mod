package com.derpz.nukaisles.item.custom;

import com.derpz.nukaisles.item.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier SCRAP_METAL = new ForgeTier(2, 32, 6.0F, 2.0F, 5, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(ModItems.SCRAP_METAL.get()));

    public static final ForgeTier CUSHION = new ForgeTier(1, 32, 0.0F, 0.0F, 0, BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.WHITE_WOOL));

}