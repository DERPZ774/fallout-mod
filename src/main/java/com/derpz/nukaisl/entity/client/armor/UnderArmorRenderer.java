package com.derpz.nukaisl.entity.client.armor;

import com.derpz.nukaisl.item.custom.UnderArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class UnderArmorRenderer extends GeoArmorRenderer<UnderArmorItem> {

    public UnderArmorRenderer() {
        super(new UnderArmorModel());

        this.headBone = "Head";
        this.bodyBone = "Body";
        this.rightArmBone = "RightArm";
        this.leftArmBone = "LeftArm";
        this.rightLegBone = "RightLeg"; //these are switched for whatever reason
        this.leftLegBone = "LeftLeg";
        this.rightBootBone = "RightLeg";
        this.leftBootBone = "LeftLeg";
    }

    @Override
    public GeoArmorRenderer applySlot(EquipmentSlot slot) {

        if (slot == EquipmentSlot.CHEST) {
            if (bodyBone != null)
                body.visible = true;
        }
        return this;
    }
}

/// TODO: 8/19/2022 Fix armor rendering on legs
