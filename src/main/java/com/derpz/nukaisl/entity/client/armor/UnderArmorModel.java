package com.derpz.nukaisl.entity.client.armor;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.item.custom.UnderArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnderArmorModel extends AnimatedGeoModel<UnderArmorItem> {
    @Override
    public ResourceLocation getModelLocation(UnderArmorItem object) {
        return new ResourceLocation(FalloutMod.MOD_ID, "geo/custom_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(UnderArmorItem object) {
        return new ResourceLocation(FalloutMod.MOD_ID, "textures/models/armor/vault_suit_full.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(UnderArmorItem animatable) {
        return new ResourceLocation(FalloutMod.MOD_ID, "animations/armor_animation.json");
    }
}

/// TODO: 8/19/2022 Edit class for other under armors