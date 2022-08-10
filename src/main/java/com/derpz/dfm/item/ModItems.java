package com.derpz.dfm.item;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.item.custom.DebugItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FalloutMod.MOD_ID);


    //copy and edit for new items
    //items
    public static final RegistryObject<Item> SCRAP_METAL = ITEMS.register("scrap_metal",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> DEBUG_ITEM = ITEMS.register("debug_item",
            () -> new DebugItem(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    //weapon
    public static final RegistryObject<Item> COMMIE_WACKER = ITEMS.register("commie_wacker",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
