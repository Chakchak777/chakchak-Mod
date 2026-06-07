package net.chakchak777.items.custom.physicBook;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.chakchak777.ChakchakMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class PhysicBookRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/item/physic_book.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/item/physic_book.png"
    );

    public PhysicBookRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .setAnimatorProvider(PhysicBookAnimator::new)
                        .disableAnimationInContexts(ItemDisplayContext.GUI)
                        .setShouldAnimateInContext(context -> context != ItemDisplayContext.GUI && context != ItemDisplayContext.FIXED) // Custom animation logic with predicate
                        .build()
        );
    }
}
