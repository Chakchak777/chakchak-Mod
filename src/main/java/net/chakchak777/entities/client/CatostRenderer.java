package net.chakchak777.entities.client;

import net.chakchak777.entities.custom.CatostEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CatostRenderer extends GeoEntityRenderer<CatostEntity> {
    public CatostRenderer(EntityRendererProvider.Context context) {
        super(context, new CatostModel());
        this.shadowRadius = 0.5F;
    }
}
