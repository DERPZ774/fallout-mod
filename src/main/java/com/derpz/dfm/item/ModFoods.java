package com.derpz.dfm.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
   public static final FoodProperties NUKA_COLA = new FoodProperties.Builder().nutrition(10).saturationMod(0.2f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 1f).build();

}
/// TODO: 8/10/2022 Add all colas with special effects & add animations via geckolib
/// TODO: 8/10/2022 Make bottlecaps appear in inv after drinking one & add  
/// TODO: 8/10/2022 Register the other colas & empty bottles 

