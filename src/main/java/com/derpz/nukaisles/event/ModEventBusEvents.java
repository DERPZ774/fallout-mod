package com.derpz.nukaisles.event;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.recipe.NukaColaMachineRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, recipeTypeRegisterHelper -> recipeTypeRegisterHelper.register(new ResourceLocation(FalloutMod.MOD_ID, NukaColaMachineRecipe.Type.ID),  NukaColaMachineRecipe.Type.INSTANCE));
    }

}
