package com.derpz.nukaisles.item;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.entity.ModEntityTypes;
import com.derpz.nukaisles.item.custom.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
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
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOTTLE_CAP = ITEMS.register("bottle_cap",
            () -> new Item(new Item.Properties()));

    //Cola
    public static final RegistryObject<Item> EMPTY_NUKA_COLA = ITEMS.register("empty_nuka_cola",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NUKA_COLA = ITEMS.register("nuka_cola",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA)));

    public static final RegistryObject<Item> NUKA_COLA_CHERRY = ITEMS.register("nuka_cola_cherry",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA_CHERRY)));

    public static final RegistryObject<Item> NUKA_COLA_QUANTUM = ITEMS.register("nuka_cola_quantum",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA_QUANTUM)));

    //ice-cold colas
    public static final RegistryObject<Item> NUKA_COLA_QUANTUM_COLD = ITEMS.register("ice_cold_nuka_cola_quantum",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA_QUANTUM_COLD)));

    public static final RegistryObject<Item> NUKA_COLA_CHERRY_COLD = ITEMS.register("ice_cold_nuka_cola_cherry",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA_CHERRY_COLD)));
    public static final RegistryObject<Item> NUKA_COLA_COLD = ITEMS.register("ice_cold_nuka_cola",
            () -> new NukaColaItem(new Item.Properties().stacksTo(1).food(ModFoods.NUKA_COLA_COLD)));


    //weapons

    //melees
    public static final RegistryObject<Item> COMMIE_WHACKER = ITEMS.register("commie_whacker",
            () ->   new CommieWhackerItem(ModTiers.CUSHION, 0, 0f,
                    new Item.Properties()));

    //ranged
    public static final RegistryObject<Item> THIRST_ZAPPER = ITEMS.register("thirst_zapper",
            () ->   new Item(new Item.Properties()));

    //tools
    public static final RegistryObject<Item> SCRAP_METAL_SWORD = ITEMS.register("scrap_metal_sword",
            () -> new SwordItem(ModTiers.SCRAP_METAL, 2, 1f,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_PICKAXE = ITEMS.register("scrap_metal_pickaxe",
            () -> new PickaxeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_HOE = ITEMS.register("scrap_metal_hoe",
            () -> new HoeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_SHOVEL = ITEMS.register("scrap_metal_shovel",
            () -> new ShovelItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_AXE = ITEMS.register("scrap_metal_axe",
            () -> new AxeItem(ModTiers.SCRAP_METAL, 2, 3f,
                    new Item.Properties()));

    //Armor
    public static final RegistryObject<Item> SCRAP_METAL_HELMET = ITEMS.register("scrap_metal_helmet",
            () -> new ModArmorItem(ModArmorMaterials.SCRAP_METAL, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_CHESTPLATE = ITEMS.register("scrap_metal_chestplate",
            () -> new ModArmorItem(ModArmorMaterials.SCRAP_METAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));

    public static final RegistryObject<Item> SCRAP_METAL_LEGGINGS = ITEMS.register("scrap_metal_leggings",
            () -> new ArmorItem(ModArmorMaterials.SCRAP_METAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));

    public static final RegistryObject<Item> VAULT_SUIT = ITEMS.register("vault_suit",
            () -> new UnderArmorItem(ModArmorMaterials.UNDER_ARMOR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}