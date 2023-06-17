package com.derpz.nukaisl.block;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.block.custom.LampBlock;
import com.derpz.nukaisl.block.custom.NukaColaMachineBlock;
import com.derpz.nukaisl.item.ModCreativeModeTab;
import com.derpz.nukaisl.item.ModItems;
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
                    lightLevel((state) -> state.getValue(LampBlock.CLICKED) ? 15 : 0)));

        public static final RegistryObject<Block> NUKA_COLA_MACHINE = registerBlock("nuka_cola_machine",
                () -> new NukaColaMachineBlock(BlockBehaviour.Properties.of(Material.METAL)
                        .strength(5f).requiresCorrectToolForDrops().noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, String tooltipKey) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(Component.translatable(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
