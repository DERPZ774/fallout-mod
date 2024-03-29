package com.derpz.nukaisles.recipe;

import com.derpz.nukaisles.FalloutMod;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NukaColaMachineRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient input;

    private int slot;

    public NukaColaMachineRecipe(ResourceLocation id, ItemStack output, Ingredient input) {
        this.id = id;
        this.output = output;
        this.input = input;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public boolean matches(@NotNull SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        for (int i = 1; i <= 6; ++i) {
            if (input.test(pContainer.getItem(i))) {
                slot = i;
                return true;
            }
        }
        return false;
    }


    public Ingredient getIngredient() {
        return input;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull SimpleContainer pContainer, @NotNull RegistryAccess p_267165_) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess p_267052_) {
        return output.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static  class Type implements RecipeType<NukaColaMachineRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "cola_cooling";
    }

    public static class Serializer implements RecipeSerializer<NukaColaMachineRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(FalloutMod.MOD_ID, "cola_cooling");

        @Override
        public @NotNull NukaColaMachineRecipe fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
            return new NukaColaMachineRecipe(pRecipeId, output, ingredient);
        }


        @Override
        public @Nullable NukaColaMachineRecipe fromNetwork(@NotNull ResourceLocation id, FriendlyByteBuf buf) {
            ItemStack output = buf.readItem();
            Ingredient ingredient = Ingredient.fromNetwork(buf);
            return new NukaColaMachineRecipe(id, output, ingredient);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, NukaColaMachineRecipe recipe) {
            buf.writeItemStack(recipe.getResultItem(null), false);
            recipe.input.toNetwork(buf);
        }
    }
}