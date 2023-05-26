package com.derpz.nukaisl.block.entity.renderer;

import com.derpz.nukaisl.block.custom.NukaColaMachineBlock;
import com.derpz.nukaisl.block.entity.NukaColaMachineBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class NukaColaMachineBlockEntityRenderer implements BlockEntityRenderer<NukaColaMachineBlockEntity> {
    public NukaColaMachineBlockEntityRenderer(BlockEntityRendererProvider.Context ignoredContext) {

    }
    @Override
    public void render(@NotNull NukaColaMachineBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.0f, 0.73f, 0.8f);
        pPoseStack.scale(0.24f, 0.24f, 0.24f);
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90));

        switch (pBlockEntity.getBlockState().getValue(NukaColaMachineBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case WEST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()),
                        pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
        pPoseStack.popPose();
        /// TODO: 5/25/2023 Rendering all bottles
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
