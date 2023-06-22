
package com.derpz.nukaisles.integration;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.block.ModBlocks;
import com.derpz.nukaisles.recipe.NukaColaMachineRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NukaMachineRecipeCategory implements IRecipeCategory<NukaColaMachineRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(FalloutMod.MOD_ID, "cola_cooling");
    public final static ResourceLocation TEXTURE = new ResourceLocation(FalloutMod.MOD_ID, "textures/gui/nuka_cola_machine.png");
    private final IDrawable background;
    private final IDrawable icon;

    public NukaMachineRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.NUKA_COLA_MACHINE.get()));
    }
    @Override
    public @NotNull RecipeType<NukaColaMachineRecipe> getRecipeType() {
        return JEINukaislesPlugin.COLA_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Nuka Cola Machine");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NukaColaMachineRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.setShapeless();
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 37).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 37).addItemStack(recipe.getResultItem(null));
    }
}
/// TODO: 5/21/2023 Fix the JEI integration to change the item after a short amount of time
