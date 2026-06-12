package net.chakchak777.entities.azure;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.RatEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RatAnimator extends AzEntityAnimator<RatEntity> {

    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "animations/entity/rat.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<RatEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "rat_controller")
                        .build()
        );
    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(RatEntity animatable) {
        return ANIMATIONS;
    }
}
