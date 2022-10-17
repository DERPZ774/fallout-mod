package com.derpz.nukaisl.entity;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.entity.custom.TestEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FalloutMod.MOD_ID);

    public static final RegistryObject<EntityType<TestEntity>> TEST =
            ENTITY_TYPES.register("test",
                    () -> EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE)
                            .sized(0.8f, 0.6f) //size of hitbox
                            .build(new ResourceLocation(FalloutMod.MOD_ID, "test").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
