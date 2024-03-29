package com.derpz.nukaisles.enchantment;

import com.derpz.nukaisles.FalloutMod;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS
            = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, FalloutMod.MOD_ID);

    /*public static RegistryObject<Enchantment> LIGHTNING_STRIKER =
            ENCHANTMENTS.register("lightning_striker",
                    () -> new LightningStrikerEnchantment(Enchantment.Rarity.COMMON,
                            EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND));

     */

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
/// TODO: 8/12/2022 Add rad based enchantment (added temp one for registration purposes)