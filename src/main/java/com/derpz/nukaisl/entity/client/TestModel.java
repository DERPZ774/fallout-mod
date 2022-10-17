package com.derpz.nukaisl.entity.client;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.entity.custom.TestEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TestModel extends AnimatedGeoModel<TestEntity> {
    @Override
    public ResourceLocation getModelResource(TestEntity object) {
        return new ResourceLocation(FalloutMod.MOD_ID, "geo/test.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TestEntity object) {
        return new ResourceLocation(FalloutMod.MOD_ID, "textures/entity/test/test.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TestEntity animatable) {
        return new ResourceLocation(FalloutMod.MOD_ID, "animations/test.animation.json");
    }
}
