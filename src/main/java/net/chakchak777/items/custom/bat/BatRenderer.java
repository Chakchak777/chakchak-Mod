package net.chakchak777.items.custom.bat;


import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.chakchak777.ChakchakMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;


public class BatRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/item/bat.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/item/bat.png"
    );

    BatRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX).build()
        );
    }
}
