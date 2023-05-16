package com.derpz.nukaisl.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class UnderArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation TEXTURE_MODEL = new ResourceLocation("nukaisl:models/textures/models/armor/vault_suit_full.png");
    private final UnderArmorModel<T> underArmorModel;


    public UnderArmorLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
        underArmorModel = new UnderArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(UnderArmorModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation(T pEntity) {
        return TEXTURE_MODEL;
    }



    public static class UnderArmorModel<T extends LivingEntity> extends EntityModel<T> {
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        public static ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("nukaisl:models/armor"), "nukaisl:models/textures/models/armor/vault_suit_full.png");
        public final ModelPart Head;
        public final ModelPart Body;
        public final ModelPart RightArm;
        public final ModelPart LeftArm;
        public final ModelPart RightLeg;
        public final ModelPart LeftLeg;


        public UnderArmorModel(ModelPart root) {
            this.Head = root.getChild("Head");
            this.Body = root.getChild("Body");
            this.RightArm = root.getChild("RightArm");
            this.LeftArm = root.getChild("LeftArm");
            this.RightLeg = root.getChild("RightLeg");
            this.LeftLeg = root.getChild("LeftLeg");
        }

/*
	public UnderArmorModel(ModelPart head, ModelPart body, ModelPart rightArm, ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg) {
		super(RenderType::armorCutoutNoCull);
		Head = head;
		Body = body;
		RightArm = rightArm;
		LeftArm = leftArm;
		RightLeg = rightLeg;
		LeftLeg = leftLeg;
	}
*/


        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F))
                    .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
                    .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
                    .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

            partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
                    .texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));

            partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
                    .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.1F))
                    .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 16, 16);
        }

        @Override
        public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
            Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            RightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            LeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }
}



