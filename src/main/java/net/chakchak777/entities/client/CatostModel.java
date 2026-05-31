package net.chakchak777.entities.client;

import net.chakchak777.entities.custom.CatostEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CatostModel extends GeoModel<CatostEntity> {
    @Override
    public ResourceLocation getModelResource(CatostEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("chakchakmod", "geo/catost.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CatostEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("chakchakmod", "textures/entity/catost.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CatostEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("chakchakmod", "animations/catost.animation.json");
    }
}
