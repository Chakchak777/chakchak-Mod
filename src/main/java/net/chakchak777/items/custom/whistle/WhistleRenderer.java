package net.chakchak777.items.custom.whistle;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.chakchak777.ChakchakMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class WhistleRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/item/whistle.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/item/whistle.png"
    );

    public WhistleRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .disableAnimationInContexts(ItemDisplayContext.GUI)
                        .setShouldAnimateInContext(context -> context != ItemDisplayContext.GUI && context != ItemDisplayContext.FIXED)
                        .build()
        );
    }
}