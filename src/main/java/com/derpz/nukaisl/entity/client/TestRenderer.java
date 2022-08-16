package com.derpz.nukaisl.entity.client;

import com.derpz.nukaisl.FalloutMod;
import com.derpz.nukaisl.entity.custom.TestEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TestRenderer extends GeoEntityRenderer<TestEntity> {
    public TestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TestModel());
        this.shadowRadius = 0.3f;
    }
    @Override
    public ResourceLocation getTextureLocation(TestEntity instance) {
        return new ResourceLocation(FalloutMod.MOD_ID, "textures/entity/test/test.png");
    }

    @Override
    public RenderType getRenderType(TestEntity animatable, float partialTicks, PoseStack stack,
                                    MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F); //scales size
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
