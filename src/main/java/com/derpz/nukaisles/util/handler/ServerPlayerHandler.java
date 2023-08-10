package com.derpz.nukaisles.util.handler;

import com.derpz.nukaisles.item.custom.GunItem;
import com.derpz.nukaisles.networking.packet.ShootingSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ServerPlayerHandler {
    public static void handleShoot(ShootingSyncS2CPacket message, ServerPlayer player) {
        if(player.isSpectator())
            return;

        if(player.getUseItem().getItem() == Items.SHIELD)
            return;

        Level world = player.level;
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);

        if(heldItem.getItem() instanceof GunItem item || player.isCreative()) {

        }
    }
}
