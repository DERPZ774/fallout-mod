package com.derpz.nukaisles.sound;

import com.derpz.nukaisles.FalloutMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    /*public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FalloutMod.MOD_ID);

    public static RegistryObject<SoundEvent> DEBUG_ITEM_FOUND_ORE = registerSoundEvents("debug_item_found_ore");

    public static RegistryObject<SoundEvent> LAMP_BREAK = registerSoundEvents("lamp_break");
    public static RegistryObject<SoundEvent> LAMP_USE = registerSoundEvents("lamp_use");
    public static RegistryObject<SoundEvent> LAMP_STEP = registerSoundEvents("lamp_step");
    public static RegistryObject<SoundEvent> LAMP_PLACE = registerSoundEvents("lamp_place");
    public static RegistryObject<SoundEvent> LAMP_HIT = registerSoundEvents("lamp_hit");
    public static RegistryObject<SoundEvent> LAMP_FALL = registerSoundEvents("lamp_fall");

    public static final ForgeSoundType LAMP_SOUNDS = new ForgeSoundType(1f, 1f,
            ModSounds.LAMP_BREAK, ModSounds.LAMP_STEP, ModSounds.LAMP_PLACE,
            ModSounds.LAMP_HIT, ModSounds.LAMP_FALL);

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(FalloutMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> new SoundEvent(id, 1f, true));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }*/
}
