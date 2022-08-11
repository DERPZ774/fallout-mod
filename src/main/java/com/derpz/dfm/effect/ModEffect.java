package com.derpz.dfm.effect;

import com.derpz.dfm.FalloutMod;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffect {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FalloutMod.MOD_ID);

    public static final RegistryObject<MobEffect> RAD
            = MOB_EFFECTS.register("rad", () -> new RadEffect(MobEffectCategory.HARMFUL, 0xFF0000));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
