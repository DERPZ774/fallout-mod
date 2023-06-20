/*
package com.derpz.nukaisles.integration;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.recipe.NukaColaMachineRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEInukaislesPlugin implements IModPlugin {
    public static mezz.jei.api.recipe.RecipeType<NukaColaMachineRecipe> COLA_TYPE = new RecipeType<>(NukaMachineRecipeCategory.UID, NukaColaMachineRecipe.class);
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(FalloutMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new NukaMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<NukaColaMachineRecipe> recipesCola = rm.getAllRecipesFor(NukaColaMachineRecipe.Type.INSTANCE);
        registration.addRecipes(COLA_TYPE, recipesCola);
    }

}
*/
