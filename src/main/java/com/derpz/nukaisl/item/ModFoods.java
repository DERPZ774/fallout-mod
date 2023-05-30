package com.derpz.nukaisl.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
   //colas
   public static final FoodProperties NUKA_COLA = (new FoodProperties.Builder()).nutrition(5).saturationMod(0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1f).alwaysEat().build();
   public static final FoodProperties NUKA_COLA_CHERRY = (new FoodProperties.Builder()).nutrition(10).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 180), 1f).alwaysEat().build();
   public static final FoodProperties NUKA_COLA_QUANTUM = (new FoodProperties.Builder()).nutrition(12).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1f).alwaysEat().build();
   public static final FoodProperties NUKA_COLA_COLD = (new FoodProperties.Builder()).nutrition(7).saturationMod(0.3f)
           .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1f).alwaysEat().build();

//cold colas
   public static final FoodProperties NUKA_COLA_CHERRY_COLD = (new FoodProperties.Builder()).nutrition(12).saturationMod(0.4f)
           .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 180), 1f).alwaysEat().build();

   public static final FoodProperties NUKA_COLA_QUANTUM_COLD = (new FoodProperties.Builder()).nutrition(18).saturationMod(0.8f)
           .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1f).alwaysEat().build();


}
/// TODO: 8/10/2022 Add all colas with special effects & add animations via gecko-lib
//TODO: Balance tiers if needed