package com.derpz.dfm.networking.packet;

import java.util.function.Supplier;

import javax.swing.text.html.parser.Entity;

import com.derpz.dfm.effect.ModEffect;
import com.derpz.dfm.effect.RadEffect;
import com.derpz.dfm.rads.PlayerRadsProvider;

import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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
                rads.addRads(20); //increase rad level
                //send rad count increasse message to player
                
                int amp = 0;
                if(rads.getRads() >100 && rads.getRads()<200){
                    amp = 0;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >200 && rads.getRads()<300){
                    amp = 1;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >300 && rads.getRads()<400){
                    amp = 2;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >400 && rads.getRads()<500){
                    amp = 3;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >500 && rads.getRads()<600){
                    amp = 4;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >600 && rads.getRads()<700){
                    amp = 5;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >700 && rads.getRads()<800){
                    amp = 6;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >800 && rads.getRads()<900){
                    amp = 7;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >900 && rads.getRads()<1000){
                    amp = 8;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                if(rads.getRads() >1000){
                    amp = 9;
                    player.addEffect(new MobEffectInstance(ModEffect.RAD.get(), 9999999, amp, false, false));
                }
                

            });
        });
        return true;
    }
}
