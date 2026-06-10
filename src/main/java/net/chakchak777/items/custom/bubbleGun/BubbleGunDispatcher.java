package net.chakchak777.items.custom.bubbleGun;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class BubbleGunDispatcher {
    private static final AzCommand FIRE = AzCommand.create(
            "gun_controller",
            "fire", AzPlayBehaviors.LOOP);


    private static final AzCommand IDLE = AzCommand.create(
            "gun_controller",
            "idle", AzPlayBehaviors.LOOP);

    private static final AzCommand RUN_IDLE = AzCommand.create(
            "gun_controller",
            "idlerun", AzPlayBehaviors.LOOP);

    private static final AzCommand RELOAD = AzCommand.create(
            "gun_controller",
            "reload", AzPlayBehaviors.PLAY_ONCE);


    public void fire( Entity entity, ItemStack itemStack) {
        FIRE.sendForItem(entity, itemStack);
    }

    public void idle(Entity entity, ItemStack itemStack) {
        IDLE.sendForItem(entity, itemStack);
    }

    public void run_idle(Entity entity, ItemStack itemStack) {
        RUN_IDLE.sendForItem(entity, itemStack);
    }

    public void reload(Entity entity, ItemStack itemStack) {
        RELOAD.sendForItem(entity, itemStack);
    }

}
