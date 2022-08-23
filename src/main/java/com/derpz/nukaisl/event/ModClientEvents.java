package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.item.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void renderPlayerPre(RenderPlayerEvent.Pre event)
    {

        if (event.getPlayer().getInventory().getArmor(2).is(ModItems.VAULT_SUIT.get())) {
            event.getRenderer().getModel().leftSleeve.visible = false;
            event.getRenderer().getModel().rightSleeve.visible = false;
            //event.getRenderer().getModel().hat.visible = false;
            event.getRenderer().getModel().jacket.visible = false;
            event.getRenderer().getModel().leftPants.visible = false;
            event.getRenderer().getModel().rightPants.visible = false;
        }


        /// TODO: 8/19/2022 edit statements here for all armor
    }
}
