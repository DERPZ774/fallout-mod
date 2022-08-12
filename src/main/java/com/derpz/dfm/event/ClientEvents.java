package com.derpz.dfm.event;

import javax.swing.text.JTextComponent.KeyBinding;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.effect.ModEffect;
import com.derpz.dfm.networking.ModMessages;
import com.derpz.dfm.util.KeyInit;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    private ClientEvents() {
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        KeyInit.init();
    }




}