package com.derpz.dfm.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RadEffect extends MobEffect {
    public RadEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        /*
        if (pLivingEntity.getHealth()>(20.0f)-(2.0f)-(pAmplifier*2.0f)) {
            pLivingEntity.hurt(DamageSource.MAGIC, 1.0F);
        }
        if(pLivingEntity instanceof Player) {
            ((Player)pLivingEntity).causeFoodExhaustion(0.005F * (float)(1));
        }
         */
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}