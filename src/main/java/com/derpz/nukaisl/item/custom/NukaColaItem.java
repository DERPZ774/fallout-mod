package com.derpz.nukaisl.item.custom;

import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.networking.ModMessages;
import com.derpz.nukaisl.networking.packet.RadC2SPacket;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
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
                player.getInventory().add(new ItemStack(ModItems.EMPTY_NUKA_COLA.get()));
            }
        }

        pLevel.gameEvent(pEntityLiving, GameEvent.DRINK, pEntityLiving.getEyePosition());
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
            pTooltipComponents.add(Component.translatable("tooltip.nukaisl.nuka_cola_uncapped.tooltip"));
        }
    }

    public NukaColaItem(Properties pProperties) {
        super(pProperties);
    }

}

