package com.derpz.nukaisles.client.renderer;

import com.derpz.nukaisles.client.models.NukaColaMachineBlockItemModel;
import com.derpz.nukaisles.item.custom.NukaColaMachineBlockItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class NukaColaMachineBlockItemRenderer extends GeoItemRenderer<NukaColaMachineBlockItem> {
    public NukaColaMachineBlockItemRenderer() {
        super(new NukaColaMachineBlockItemModel());
    }
}
