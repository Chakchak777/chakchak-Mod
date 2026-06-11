package net.chakchak777.items.custom.bubbleGun;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import net.chakchak777.ChakchakMod;
import net.chakchak777.items.custom.physicBook.PhysicBookAnimator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class BubbleGunRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "geo/item/bubble_gun.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "textures/item/bubble_gun.png"
    );

    public BubbleGunRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .setAnimatorProvider(BubbleGunAnimator::new)
                        .disableAnimationInContexts(ItemDisplayContext.GUI)
                        .setAlpha(1.0F)
                        .setRenderType(itemStack -> RenderType.entityTranslucent(TEX))
                        .setShouldAnimateInContext(context -> context != ItemDisplayContext.GUI && context != ItemDisplayContext.FIXED) // Custom animation logic with predicate
                        .build()
        );
    }
}
