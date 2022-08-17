package com.derpz.nukaisl.item.custom;

import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.RadC2SPacket;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NukaColaItem extends Item {


    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (player == null  && !pStack.hasTag()|| !player.getAbilities().instabuild  && !pStack.hasTag()) {
            ModMessages.sendToServer(new RadC2SPacket());
            if (pStack.isEmpty()) {
                return new ItemStack(ModItems.BOTTLE_CAP.get());
            } else {
                pStack.shrink(0);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(ModItems.BOTTLE_CAP.get()));
            }
        }

        pLevel.gameEvent(pEntityLiving, GameEvent.DRINKING_FINISH, pEntityLiving.eyeBlockPosition());
        return this.isEdible() ? pEntityLiving.eat(pLevel, pStack) : pStack;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return pStack.getItem().isEdible() ? UseAnim.DRINK : UseAnim.NONE;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(pStack.hasTag()) {
            pTooltipComponents.add(new TranslatableComponent("tooltip.nukaisl.nuka_cola_uncapped.tooltip"));
        };
    }

    public NukaColaItem(Properties pProperties) {
        super(pProperties);
    }

}
