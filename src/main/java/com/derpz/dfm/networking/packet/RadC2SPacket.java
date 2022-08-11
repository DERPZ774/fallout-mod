package com.derpz.dfm.networking.packet;

import java.util.function.Supplier;

import javax.swing.text.html.parser.Entity;

import com.derpz.dfm.rads.PlayerRadsProvider;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

public class RadC2SPacket {

    public RadC2SPacket() {

    }

    public RadC2SPacket(FriendlyByteBuf buf){

    }
    
    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

           
            player.getCapability(PlayerRadsProvider.PLAYER_RADS).ifPresent(rads -> {
                rads.addRads(5); //increase rad level
                //send rad count increasse message to player
                if(rads.getRads() >100 && rads.getRads()<200){

            }

            });
        });
        return true;
    }
}
