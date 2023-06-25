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
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
//        ItemStack itemStack = pBlockEntity.getRenderStack();


        for (int i = 1; i < pBlockEntity.itemHandler.getSlots(); i++) {
            ItemStack stack = pBlockEntity.getRenderStack(i);
            if (stack.getItem() instanceof NukaColaItem) {
                pPoseStack.pushPose();
                //1st slot render
                Direction facing = pBlockEntity.getBlockState().getValue(NukaColaMachineBlock.FACING);
                switch (i) {
                    case 1 -> {
                        if (facing == Direction.WEST) {
                            pPoseStack.translate(0.18f, 0.74f, 0.71f);
                            pPoseStack.mulPose(Axis.YN.rotationDegrees(25));
                        } else if (facing == Direction.EAST) {
                            pPoseStack.translate(0.82f, 0.74f, 0.29f);
                            pPoseStack.mulPose(Axis.YN.rotationDegrees(25));
                        } else if (facing == Direction.NORTH) {
                            pPoseStack.translate(0.29f, 0.74f, 0.16f);
                            pPoseStack.mulPose(Axis.YP.rotationDegrees(65));
                        } else if (facing == Direction.SOUTH) {
                            pPoseStack.translate(0.71f, 0.74f, 0.82f);
                            pPoseStack.mulPose(Axis.YP.rotationDegrees(65));
                        }
                        pPoseStack.scale(0.24f, 0.24f, 0.24f);
                    }
                    case 2 -> {
                        if (facing == Direction.WEST) {
                            pPoseStack.translate(0.18f, 0.74f, 0.91f);
                            pPoseStack.mulPose(Axis.YP.rotationDegrees(27));
                        } else if (facing == Direction.EAST){
                            pPoseStack.translate(0.18f, 0.74f, 0.91f);
                            pPoseStack.mulPose(Axis.YP.rotationDegrees(27));
                        }
//                            pPoseStack.translate(0.13f, 0.74f, 0.75f);
//                        }
                            pPoseStack.scale(0.24f, 0.24f, 0.24f);

                            // Add more cases as needed
                        }
                    }

                    itemRenderer.m_269128_(stack, ItemDisplayContext.FIXED, getLightLevel(Objects.requireNonNull(pBlockEntity.getLevel()), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), pBlockEntity.getRenderStack(i).getCount());
                    pPoseStack.popPose();
                }
                /// TODO: 5/25/2023 Rendering all bottles
            }
        }
        private int getLightLevel(Level level, BlockPos pos) {
            int bLight = level.getBrightness(LightLayer.BLOCK, pos);
            int sLight = level.getBrightness(LightLayer.SKY, pos);
            return LightTexture.pack(bLight, sLight);
        }
    }