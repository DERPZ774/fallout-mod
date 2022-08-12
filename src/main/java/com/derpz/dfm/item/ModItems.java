package com.derpz.dfm.item;

import com.derpz.dfm.FalloutMod;
import com.derpz.dfm.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
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

    public static final RegistryObject<Item> BOTTLE_CAP = ITEMS.register("bottle_cap",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> CAP_STASH = ITEMS.register("cap_stash",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> DEBUG_ITEM = ITEMS.register("debug_item",
            () -> new DebugItem(new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    //Cola
    public static final RegistryObject<Item> NUKA_COLA = ITEMS.register("nuka_cola",
            () -> new NukaColaItem(new Item.Properties().tab(ModCreativeModeTab.NUKA_COLA_TAB).food(ModFoods.NUKA_COLA)));

    public static final RegistryObject<Item> NUKA_COLA_CHERRY = ITEMS.register("nuka_cola_cherry",
            () -> new NukaColaItem(new Item.Properties().tab(ModCreativeModeTab.NUKA_COLA_TAB).food(ModFoods.NUKA_COLA_CHERRY)));

    public static final RegistryObject<Item> NUKA_COLA_QUANTUM = ITEMS.register("nuka_cola_quantum",
            () -> new NukaColaItem(new Item.Properties().tab(ModCreativeModeTab.NUKA_COLA_TAB).food(ModFoods.NUKA_COLA_QUANTUM)));

    //weapon
    public static final RegistryObject<Item> COMMIE_WHACKER = ITEMS.register("commie_whacker",
            () ->   new CommieWhackerItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.MELEE_WEAPONS)));

    public static final RegistryObject<Item> CUMMIE_WHACKER = ITEMS.register("cummie_whacker",
            () -> new CommieWhackerItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.MELEE_WEAPONS)));

    //tools
    public static final RegistryObject<Item> SCRAP_METAL_SWORD = ITEMS.register("scrap_metal_sword",
            () -> new SwordItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_PICKAXE = ITEMS.register("scrap_metal_pickaxe",
            () -> new PickaxeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_HOE = ITEMS.register("scrap_metal_hoe",
            () -> new HoeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_SHOVEL = ITEMS.register("scrap_metal_shovel",
            () -> new ShovelItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_AXE = ITEMS.register("scrap_metal_axe",
            () -> new AxeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    //Armor
    public static final RegistryObject<Item> SCRAP_METAL_HELMET = ITEMS.register("scrap_metal_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SCRAP_METAL, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_CHESTPLATE = ITEMS.register("scrap_metal_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SCRAP_METAL, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_LEGGINGS = ITEMS.register("scrap_metal_leggings",
            () -> new ArmorItem(ModArmorMaterials.SCRAP_METAL, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));

    public static final RegistryObject<Item> SCRAP_METAL_BOOTS = ITEMS.register("scrap_metal_boots",
            () -> new ArmorItem(ModArmorMaterials.SCRAP_METAL, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.FALLOUT_TAB)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
/// TODO: 8/10/2022 Balance tool modifiers