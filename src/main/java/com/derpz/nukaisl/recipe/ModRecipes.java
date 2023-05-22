package com.derpz.nukaisl.recipe;

import com.derpz.nukaisl.FalloutMod;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FalloutMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<NukaColaMachineRecipe>> NUKA_MACHINE_SERIALIZER =
            SERIALIZERS.register("cola_cooling", () -> NukaColaMachineRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
