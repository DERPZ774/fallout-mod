package com.derpz.nukaisles.item.custom;

import com.derpz.nukaisles.item.ModItems;
import com.derpz.nukaisles.particle.ModParticles;
import com.derpz.nukaisles.util.RadHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NukaColaItem extends Item {

    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving) {
        pLevel.addParticle(ModParticles.RADIATION_PARTICLES.get(),pEntityLiving.getX() + 0.5,pEntityLiving.getY() + 1,pEntityLiving.getZ() + 0.5, 1, 1, 1 );
        if (pEntityLiving instanceof ServerPlayer player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                RadHelper.addRads(player, 20);
                RadHelper.addEffect(player);
                if (pStack.isEmpty()) {
                    return new ItemStack(ModItems.BOTTLE_CAP.get());
                } else {
                    pStack.shrink(0);
                }

                player.getInventory().add(new ItemStack(ModItems.BOTTLE_CAP.get()));
                player.getInventory().add(new ItemStack(ModItems.EMPTY_NUKA_COLA.get()));
            }
        }

        pLevel.gameEvent(pEntityLiving, GameEvent.DRINK, pEntityLiving.getEyePosition());
        return this.isEdible() ? pEntityLiving.eat(pLevel, pStack) : pStack;
    }

    @Override
    public @NotNull SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack pStack) {
        return pStack.getItem().isEdible() ? UseAnim.DRINK : UseAnim.NONE;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if(pStack.is(ModItems.NUKA_COLA_COLD.get()) || pStack.is(ModItems.NUKA_COLA_CHERRY_COLD.get()) || pStack.is(ModItems.NUKA_COLA_QUANTUM_COLD.get())) {
            pTooltipComponents.add(Component.translatable("tooltip.nukaisles.nuka_cola_cold.tooltip").withStyle(ChatFormatting.AQUA));
        }
        assert pStack.getTag() != null;
        if(pStack.hasTag()) {
            pTooltipComponents.add(Component.translatable("tooltip.nukaisles.nuka_cola_uncapped.tooltip").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        }
    }


    @Override
    public boolean isFoil(ItemStack pStack) {
        return pStack.is(ModItems.NUKA_COLA_COLD.get()) || pStack.is(ModItems.NUKA_COLA_CHERRY_COLD.get()) || pStack.is(ModItems.NUKA_COLA_QUANTUM_COLD.get());
    }

    public NukaColaItem(Properties pProperties) {
        super(pProperties);
    }

   //pEntityLiving.getX() + 0.5,pEntityLiving.getY() + 1,pEntityLiving.getZ() + 0.5,

}

/// TODO: 5/30/2023 Crouch right click fix for decap