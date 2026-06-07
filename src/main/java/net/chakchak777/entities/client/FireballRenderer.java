package net.chakchak777.entities.client;


import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.FireBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FireballRenderer extends AzEntityRenderer<FireBallEntity> {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/entity/fireball.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/entity/fireball.png"
    );

    public FireballRenderer(EntityRendererProvider.Context context) {
        super(
                AzEntityRendererConfig.<FireBallEntity>builder(GEO, TEX)
                        .build(),
                context
        );
    }
}