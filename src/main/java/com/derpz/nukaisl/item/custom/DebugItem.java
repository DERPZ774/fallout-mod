package com.derpz.nukaisl.item.custom;

import net.minecraft.world.item.Item;

public class DebugItem extends Item {
    public DebugItem(Properties pProperties) {
        super(pProperties);
    }
/*
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();
            boolean foundBlock = false;

            //ex of sound code
            pContext.getLevel().playSound(player, positionClicked, ModSounds.DEBUG_ITEM_FOUND_ORE.get(),
                    SoundSource.BLOCKS, 1f, 1f);

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                Block blockBelow = pContext.getLevel().getBlockState(positionClicked.below(i)).getBlock();

                if(isValuableBlock(blockBelow)) {
                    outputValuableCoordinates(positionClicked.below(i), player, blockBelow);
                    foundBlock = true;

                    if(InventoryUtil.hasPlayerStackInventory(player, ModItems.DEBUG_TABLET_ITEM.get())) {
                        addNbtToDataTablet(player, positionClicked.below(i), blockBelow);
                    }

                    break;
                }
            }

            if(!foundBlock) {
                player.sendSystemMessage(Component.translatable("item.nukaisl.debug_item.no_valuables"));
            }
        }

        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return super.useOn(pContext);
    }

    private void addNbtToDataTablet(Player player, BlockPos pos, Block blockBelow) {
        ItemStack dataTablet =
                player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.DEBUG_TABLET_ITEM.get()));

        CompoundTag nbtData = new CompoundTag();
        nbtData.putString("nukaisl.last_ore", "Found " + blockBelow.getName() + " at (" +
                pos.getX() + ", "+ pos.getY() + ", "+ pos.getZ() + ")");

        dataTablet.setTag(nbtData);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
       if(Screen.hasShiftDown()) {
           pTooltipComponents.add(Component.translatable("tooltip.nukaisl.debug_item.tooltip.shift"));
       }
       else {
           pTooltipComponents.add(Component.translatable("tooltip.nukaisl.debug_item.tooltip"));
       }
    }

    private void outputValuableCoordinates(BlockPos blockPos, Player player, Block blockBelow) {
        player.sendSystemMessage(Component.literal("Found " + blockBelow.getName() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + "," + blockPos.getZ() + ")"));
    }

    private boolean isValuableBlock(Block block) {
        return Registry.BLOCK.getHolderOrThrow(Registry.BLOCK.getResourceKey(block).get()).is(ModTags.Blocks.DEBUG_ITEM_VALUABLES);

    }

    /*
    This passes the valuables manually. Above code changes this to a json system for mod compatibility.

     private boolean isValuableBlock(Block block) {
        return block == Blocks.COAL_ORE || block == Blocks.COPPER_ORE
                || block == Blocks.DIAMOND_BLOCK || block == Blocks.IRON_ORE;
    }
     */

}
