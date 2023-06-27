package com.derpz.nukaisles.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ItemStackUtil {
    public static float getTagPropertyValue(ItemStack pStack, @Nullable ClientLevel pLevel, @Nullable LivingEntity pEntity, int pSeed) {
        return pStack.hasTag() ? 1.0F : 0.0F;
    }
}
