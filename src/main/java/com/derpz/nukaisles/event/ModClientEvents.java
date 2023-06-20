package com.derpz.nukaisles.event;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.item.ModItems;

import com.derpz.nukaisles.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
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
    }


    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.DRINKING_KEY.consumeClick()) {
            assert Minecraft.getInstance().player != null;
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
