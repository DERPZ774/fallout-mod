package com.derpz.nukaisles.util.handler;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.item.custom.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT)

public class ShootingHandler {
    /*Credits to MrCrayfish gun mod*/
    @SubscribeEvent
    public static void onLeftClick(InputEvent.InteractionKeyMappingTriggered event) {
        if (event.isCanceled())
            return;
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if(player == null)
            return;

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof GunItem) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }
}