package net.chakchak777.items.custom.physicBook;

import mod.azure.azurelib.common.animation.controller.AzAnimationController;
import mod.azure.azurelib.common.animation.controller.AzAnimationControllerContainer;
import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.impl.AzItemAnimator;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.chakchak777.ChakchakMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PhysicBookDispatcher {
    private static final AzCommand ATTACK = AzCommand.create(
            "book_controller",
            "attack1", AzPlayBehaviors.LOOP);


    private static final AzCommand IDLE = AzCommand.create(
            "book_controller",
            "idle", AzPlayBehaviors.LOOP);


    public void attack( Entity entity, ItemStack itemStack) {
        ATTACK.sendForItem(entity, itemStack);
    }

    public void idle(Entity entity, ItemStack itemStack) {
        IDLE.sendForItem(entity, itemStack);
    }
}
