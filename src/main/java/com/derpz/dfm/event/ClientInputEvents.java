package com.derpz.dfm.event;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.networking.ModMessages;
import com.derpz.dfm.networking.packet.ExampleC2SPacket;
import com.derpz.dfm.networking.packet.RadC2SPacket;
import com.derpz.dfm.util.KeyInit;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.api.distmarker.Dist;

@EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientInputEvents {
	
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyInit.radCheckKeyMapping.consumeClick()) {
            ModMessages.sendToServer(new RadC2SPacket());
        }
        
    }
}