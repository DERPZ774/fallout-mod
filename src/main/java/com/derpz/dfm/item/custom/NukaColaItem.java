package com.derpz.dfm.item.custom;

import com.derpz.dfm.item.ModItems;
import com.derpz.dfm.networking.ModMessages;
import com.derpz.dfm.networking.packet.ExampleC2SPacket;
import com.derpz.dfm.networking.packet.RadC2SPacket;

import net.minecraft.advancements.CriteriaTriggers;
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

public class NukaColaItem extends Item {


    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (pStack.isEmpty()) {
                return new ItemStack(ModItems.BOTTLE_CAP.get());
            } else {
                pStack.shrink(0);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(ModItems.BOTTLE_CAP.get()));
                ModMessages.sendToServer(new RadC2SPacket());
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

    public NukaColaItem(Properties pProperties) {
        super(pProperties);
    }
}

