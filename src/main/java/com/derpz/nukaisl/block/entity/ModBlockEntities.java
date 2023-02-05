package com.derpz.nukaisl.block.entity;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FalloutMod.MOD_ID);

        public static final RegistryObject<BlockEntityType<NukaColaMachineBlockEntity>> NUKA_COLA_MACHINE =
                BLOCK_ENTITIES.register("nuka_cola_machine", () ->
                        BlockEntityType.Builder.of(NukaColaMachineBlockEntity::new,
                        ModBlocks.NUKA_COLA_MACHINE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
