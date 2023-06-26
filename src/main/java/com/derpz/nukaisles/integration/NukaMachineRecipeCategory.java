
package com.derpz.nukaisles.integration;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.block.ModBlocks;
import com.derpz.nukaisles.recipe.NukaColaMachineRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
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
    public final static ResourceLocation TEXTURE = new ResourceLocation(FalloutMod.MOD_ID, "textures/gui/jei/nuka_cola_machine_jei.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated animatedArrow;
//NOTE UV determines start point of TEXTURE

    public NukaMachineRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 141, 56);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.NUKA_COLA_MACHINE.get()));
        ITickTimer progress = helper.createTickTimer(1, 78, false);
        IDrawableStatic staticArrow = helper.createDrawable(TEXTURE, 142, 6, 24, 17);
        this.animatedArrow = helper.createAnimatedDrawable(staticArrow, progress.getMaxValue(), IDrawableAnimated.StartDirection.LEFT, false);
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

    public IDrawableAnimated getAnimatedArrow() {
        return animatedArrow;
    }

    @Override
    public void draw(@NotNull NukaColaMachineRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull PoseStack stack, double mouseX, double mouseY) {
        getAnimatedArrow().draw(stack, 84, 28);
        IRecipeCategory.super.draw(recipe, recipeSlotsView, stack, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NukaColaMachineRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.setShapeless();
        builder.addSlot(RecipeIngredientRole.INPUT, 25, 20).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 29).addItemStack(recipe.getResultItem(null));

    }
}
/// TODO: 5/21/2023 Fix the JEI integration to change the item after a short amount of time
