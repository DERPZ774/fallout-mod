package com.derpz.dfm.item;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.item.custom.DebugItem;
import com.derpz.dfm.item.custom.NukaColaItem;
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

    public static final RegistryObject<Item> BOTTLE_CAP = ITEMS.register("bottle_cap",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    //food
    public static final RegistryObject<Item> NUKA_COLA = ITEMS.register("nuka_cola",
            () -> new NukaColaItem(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB).food(ModFoods.NUKA_COLA).stacksTo(1)));

    //weapon
    public static final RegistryObject<Item> COMMIE_WACKER = ITEMS.register("commie_wacker",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
