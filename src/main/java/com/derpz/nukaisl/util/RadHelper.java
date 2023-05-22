package com.derpz.nukaisl.util;

import com.derpz.nukaisl.effect.ModEffects;
import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.RadsDataSyncS2CPacket;
import com.derpz.nukaisl.rads.PlayerRadsProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public class RadHelper {
    public static void setRads(Player player, int amount) {

        player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(rads -> {
            rads.setRads(amount); //increase rad level
            if(player instanceof ServerPlayer serverPlayer) {
                ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(rads.getRads()), serverPlayer);
            }
        });
    }

    public static void addRads(Player player, int amount) {

        player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(rads -> {
            rads.addRads(amount); //increase rad level
            if(player instanceof ServerPlayer serverPlayer) {
                ModMessages.sendToPlayer(new RadsDataSyncS2CPacket(rads.getRads()), serverPlayer);
            }
        });
    }

    public static void addEffect(Player player) {
        player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(rads -> {
            int radmath = Math.min(rads.getRads(), 900);
            int amp = (int)Math.floor(radmath / 100.0);
            player.addEffect(new MobEffectInstance(ModEffects.RAD.get(), 9999999, amp, false, false));

        });
    }
}
