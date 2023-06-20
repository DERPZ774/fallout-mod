package com.derpz.nukaisles.block.custom;

import net.minecraft.world.item.Item;

public class DebugTabletItem extends Item {
    public DebugTabletItem(Properties pProperties) {
        super(pProperties);
    }
/*
    //Using the item will erase data on it with method below
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pPlayer.getItemInHand(pUsedHand).hasTag()) {
            pPlayer.getItemInHand(pUsedHand).setTag(new CompoundTag());
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    //makes it glow if tag is detected
    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.hasTag();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.hasTag()) {
            String currentOre = pStack.getTag().getString("nukaisles.last_ore");
            pTooltipComponents.add(Component.translatable(currentOre));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

 */
}

