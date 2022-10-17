package com.derpz.nukaisl.event;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.entity.ModEntityTypes;
import com.derpz.nukaisl.entity.client.armor.UnderArmorRenderer;
import com.derpz.nukaisl.entity.custom.TestEntity;
import com.derpz.nukaisl.event.loot.BottleCapInChestsAdditionModifier;
import com.derpz.nukaisl.item.ModItems;
import com.derpz.nukaisl.item.custom.UnderArmorItem;
import com.derpz.nukaisl.particle.ModParticles;
import com.derpz.nukaisl.particle.custom.RadiationParticles;
import com.derpz.nukaisl.recipe.NukaColaBottleOpenerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = FalloutMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegisterEvent event) {

        event.register(ForgeRegistries.Keys.RECIPE_TYPES, recipeTypeRegisterHelper -> {
            recipeTypeRegisterHelper.register(new ResourceLocation(FalloutMod.MOD_ID,NukaColaBottleOpenerRecipe.Type.ID),  NukaColaBottleOpenerRecipe.Type.INSTANCE);
        });
    }




    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.TEST.get(), TestEntity.setAttributes());
        /// TODO: 8/15/2022 REMEMBER TO CHANGE THIS
    }



}
