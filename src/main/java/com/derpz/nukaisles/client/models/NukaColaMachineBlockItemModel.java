package com.derpz.nukaisles.client.models;

import com.derpz.nukaisles.FalloutMod;
import com.derpz.nukaisles.item.custom.NukaColaMachineBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class NukaColaMachineBlockItemModel extends GeoModel<NukaColaMachineBlockItem> {
    @Override
    public ResourceLocation getModelResource(NukaColaMachineBlockItem animatable) {
        return new ResourceLocation(FalloutMod.MOD_ID, "geo/nuka_cola_machine.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(NukaColaMachineBlockItem animatable) {
        return new ResourceLocation(FalloutMod.MOD_ID, "textures/block/nuka_cola_machine.png");
    }

    @Override
    public ResourceLocation getAnimationResource(NukaColaMachineBlockItem animatable) {
        return new ResourceLocation(FalloutMod.MOD_ID, "animations/machine_animation.json");
    }
}
