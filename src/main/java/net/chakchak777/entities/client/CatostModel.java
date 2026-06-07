package net.chakchak777.entities.client;

import mod.azure.azurelib.common.render.entity.AzEntityModelRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.CatostEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


public class CatostModel extends AzEntityRenderer<CatostEntity> {

    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/entity/catost.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/entity/catost.png"
    );

    protected CatostModel(AzEntityRendererConfig<CatostEntity> config, EntityRendererProvider.Context context) {
        super(config, context);
    }
}
