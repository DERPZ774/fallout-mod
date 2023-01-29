package com.derpz.nukaisl.block;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.block.custom.DebugBlock;
import com.derpz.nukaisl.block.custom.LampBlock;
import com.derpz.nukaisl.block.custom.NukaColaOpenerBlock;
import com.derpz.nukaisl.block.entity.NukaColaOpenerBlockEntity;
import com.derpz.nukaisl.item.ModCreativeModeTab;
import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.sound.ModSounds;
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
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.FALLOUT_TAB);


    //Decorations

    //Stairs
    public static final RegistryObject<Block> SCRAP_METAL_STAIRS = registerBlock("scrap_metal_stairs",
            () -> new StairBlock(()-> ModBlocks.SCRAP_METAL_BLOCK.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.FALLOUT_TAB);


    //Slabs
    public static final RegistryObject<Block> SCRAP_METAL_SLAB = registerBlock("scrap_metal_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.METAL)
                    .strength(5f).requiresCorrectToolForDrops()), ModCreativeModeTab.FALLOUT_TAB);


    //Special blocks

    public static final RegistryObject<Block> NUKA_COLA_BLOCK = registerBlock("nuka_cola_opener",
            () -> new NukaColaOpenerBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2f).requiresCorrectToolForDrops()), ModCreativeModeTab.FALLOUT_TAB);
    public static final RegistryObject<Block> LAMP = registerBlock("lamp",
            () -> new LampBlock(BlockBehaviour.Properties.of(Material.METAL).noOcclusion()
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.METAL).
                    lightLevel((state) -> state.getValue(LampBlock.CLICKED) ? 15 : 0)),
            ModCreativeModeTab.FALLOUT_TAB);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab, String tooltipKey) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab, tooltipKey);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, String tooltipKey) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)){
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(Component.translatable(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
