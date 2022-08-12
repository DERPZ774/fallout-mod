package com.derpz.dfm.event;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.effect.ModEffect;
import com.derpz.dfm.networking.ModMessages;
import com.derpz.dfm.networking.packet.RadC2SPacket;
import com.derpz.dfm.util.KeyInit;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;

@EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientInputEvents {
	
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyInit.radCheckKeyMapping.consumeClick()) {
            ModMessages.sendToServer(new RadC2SPacket());
        }
        
    }

        //on tick event
    @SubscribeEvent
    public static void onTick(PlayerTickEvent event) {
            if(event.player.isAlive()) {
                MobEffectInstance effect = event.player.getEffect(ModEffect.RAD.get());
                if(effect != null) {
                    if(event.player.getHealth() > 20 - 2 - (effect.getAmplifier() * 2)) {
                        event.player.setHealth(event.player.getHealth() - 1);// + (effect.getAmplifier() * 2));
                    }
                }
            }
    }
}