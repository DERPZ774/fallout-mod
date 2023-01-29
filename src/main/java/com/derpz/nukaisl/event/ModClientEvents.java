package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.item.ModItems;

import com.derpz.nukaisl.util.DevUtil;
import com.derpz.nukaisl.util.KeyBinding;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.ParseResults;
import net.minecraft.CrashReport;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.GiveCommand;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event)
    {
        if (event.getEntity().getInventory().getArmor(2).is(ModItems.VAULT_SUIT.get())) {
            event.getRenderer().getModel().leftSleeve.visible = false;
            event.getRenderer().getModel().rightSleeve.visible = false;
            //event.getRenderer().getModel().hat.visible = false;
            event.getRenderer().getModel().jacket.visible = false;
            event.getRenderer().getModel().leftPants.visible = false;
            event.getRenderer().getModel().rightPants.visible = false;
        }

        /// TODO: 8/23/2022 "Flatten" the hat layer
        /// TODO: 8/19/2022 edit statements here for all armor
    }


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.DRINKING_KEY.consumeClick()) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Key pressed"));
        }
    }

    /*@SubscribeEvent
    public static void onWhackerHit(AttackEntityEvent event) {
       if (event.getEntity().getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CUMMIE_WHACKER.get()) && event.getEntity().getStringUUID().matches(DevUtil.derpz)) {
           Minecraft.getInstance().player.sendSystemMessage(Component.literal("Oni Chan!"));
        }
    }
     */

}
