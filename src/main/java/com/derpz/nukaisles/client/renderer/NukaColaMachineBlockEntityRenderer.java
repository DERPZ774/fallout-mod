package com.derpz.nukaisles.client.renderer;

import com.derpz.nukaisles.block.custom.NukaColaMachineBlock;
import com.derpz.nukaisles.block.entity.NukaColaMachineBlockEntity;
import com.derpz.nukaisles.client.models.NukaColaMachineBlockModel;
import com.derpz.nukaisles.item.custom.NukaColaItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

import java.util.Objects;


public class NukaColaMachineBlockEntityRenderer extends GeoBlockRenderer<NukaColaMachineBlockEntity> {
    public NukaColaMachineBlockEntityRenderer(BlockEntityRendererProvider.Context ignoredContext) {
        super(new NukaColaMachineBlockModel());
    }

    @Override
    public void actuallyRender(PoseStack pPoseStack, NukaColaMachineBlockEntity pBlockEntity, BakedGeoModel model, RenderType renderType, MultiBufferSource pBufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(pPoseStack, pBlockEntity, model, renderType, pBufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = pBlockEntity.getRenderStack();
        if(itemStack.getItem() instanceof NukaColaItem) {
            pPoseStack.pushPose();
            //1st slot render
            if (pBlockEntity.itemHandler.getStackInSlot(1).getItem() instanceof NukaColaItem) {
                pPoseStack.translate(0.1f, 0.74f, 0.72f);
                pPoseStack.scale(0.24f, 0.24f, 0.24f);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.XN.rotationDegrees(-25));
            } else if (pBlockEntity.itemHandler.getStackInSlot(2).getItem() instanceof NukaColaItem) {
                pPoseStack.translate(0.13f, 0.74f, 0.75f);
                pPoseStack.scale(0.24f, 0.24f, 0.24f);
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
                pPoseStack.mulPose(Axis.XN.rotationDegrees(25));
            }

            switch (pBlockEntity.getBlockState().getValue(NukaColaMachineBlock.FACING)) {
                case NORTH -> pPoseStack.mulPose(Axis.ZP.rotationDegrees(0));
                case EAST -> pPoseStack.mulPose(Axis.ZP.rotationDegrees(90));
                case SOUTH -> pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));
                case WEST -> pPoseStack.mulPose(Axis.ZP.rotationDegrees(270));
            }

            itemRenderer.m_269128_(itemStack, ItemDisplayContext.GUI, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()),
                            pBlockEntity.getBlockPos()),
                    OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), pBlockEntity.getRenderStack().getCount());
            pPoseStack.popPose();
        }
        /// TODO: 5/25/2023 Rendering all bottles
    }
    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    };
}