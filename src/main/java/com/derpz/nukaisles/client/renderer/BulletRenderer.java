package com.derpz.nukaisles.client.renderer;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.entity.custom.BulletEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BulletRenderer extends EntityRenderer<BulletEntity> {

    public BulletRenderer(EntityRendererProvider.Context context) {
        super(context);

        // Renderer initialization
    }

    @Override
    public void render(BulletEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {

    }

    @Override
    public ResourceLocation getTextureLocation(BulletEntity pEntity) {
        return new ResourceLocation(FalloutMod.MOD_ID, "textures/item/bottle_cap.png");
    }

}
