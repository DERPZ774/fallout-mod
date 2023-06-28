package com.derpz.nukaisles.block;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.block.custom.LampBlock;
import com.derpz.nukaisles.block.custom.NukaColaMachineBlock;
import com.derpz.nukaisles.block.custom.NukaColaMachineTop;
import com.derpz.nukaisles.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FalloutMod.MOD_ID);

    //Reg block registration
    public static final RegistryObject<Block> SCRAP_METAL_BLOCK = registerBlock("scrap_metal_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()));


    //Decorations

    //Stairs
    public static final RegistryObject<Block> SCRAP_METAL_STAIRS = registerBlock("scrap_metal_stairs",
            () -> new StairBlock(()-> ModBlocks.SCRAP_METAL_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()));


    //Slabs
    public static final RegistryObject<Block> SCRAP_METAL_SLAB = registerBlock("scrap_metal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()));


    //Special blocks

                    
    public static final RegistryObject<Block> LAMP = registerBlock("lamp",
            () -> new LampBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.METAL).
                    lightLevel((state) -> state.getValue(LampBlock.CLICKED) ? 8 : 0)));

        public static final RegistryObject<Block> NUKA_COLA_MACHINE = BLOCKS.register("nuka_cola_machine",
                () -> new NukaColaMachineBlock(BlockBehaviour.Properties.of(Material.METAL)
                        .strength(5f).requiresCorrectToolForDrops().noOcclusion()));

    public static final RegistryObject<Block> NUKA_COLA_MACHINE_TOP = BLOCKS.register("nuka_cola_machine_top",
            () -> new NukaColaMachineTop(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops().noOcclusion().noParticlesOnBreak()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block, String tooltipKey) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()) {
            @Override
            public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
                pTooltip.add(Component.translatable(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
