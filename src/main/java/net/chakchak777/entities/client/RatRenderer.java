package net.chakchak777.entities.client;

import mod.azure.azurelib.common.render.entity.AzEntityRenderer;
import mod.azure.azurelib.common.render.entity.AzEntityRendererConfig;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.azure.RatAnimator;
import net.chakchak777.entities.custom.RatEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RatRenderer extends AzEntityRenderer<RatEntity> {

    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/entity/rat.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/entity/rat.png"
    );


    public RatRenderer( EntityRendererProvider.Context context) {
        super(AzEntityRendererConfig.<RatEntity>builder(GEO, TEX)
                        .setRenderEntry(contextPipeline -> {
                            contextPipeline.animatable();

                            return contextPipeline;
                        })
                        .setAnimatorProvider(RatAnimator::new)
                        .setShadowRadius(0.1F)
                        .build(),

                context);
    }
}
