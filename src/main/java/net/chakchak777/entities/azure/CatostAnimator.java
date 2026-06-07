package net.chakchak777.entities.azure;

import mod.azure.azurelib.common.animation.AzAnimator;
import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.impl.AzEntityAnimator;
import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.CatostEntity;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CatostAnimator extends AzEntityAnimator<CatostEntity> {

    private static final ResourceLocation ANIMATIONS = ResourceLocation.fromNamespaceAndPath(
            ChakchakMod.MODID,
            "animations/entity/catost.animation.json"
    );

    @Override
    public void registerControllers(AzAnimationControllerContainer<CatostEntity> animationControllerContainer) {
        animationControllerContainer.add(
                AzAnimationController.builder(this, "catost_controller")
                        .build()
        );

    }

    @Override
    public @NotNull ResourceLocation getAnimationLocation(CatostEntity animatable) {
        return ANIMATIONS;
    }


}
