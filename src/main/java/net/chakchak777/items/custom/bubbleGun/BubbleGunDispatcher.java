package net.chakchak777.items.custom.bubbleGun;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class BubbleGunDispatcher {
    private static final AzCommand FIRE_SHOT     = AzCommand.create(
            "gun_controller",
            "fireshot", AzPlayBehaviors.LOOP);

    private static final AzCommand FIRE_SEMI  = AzCommand.create(
            "gun_controller",
            "firesemi", AzPlayBehaviors.LOOP);

    private static final AzCommand FIRE_AUTO  = AzCommand.create(
            "gun_controller",
            "fireauto", AzPlayBehaviors.LOOP);


    private static final AzCommand IDLE = AzCommand.create(
            "gun_controller",
            "idle", AzPlayBehaviors.LOOP);

    private static final AzCommand RUN_IDLE = AzCommand.create(
            "gun_controller",
            "idlerun", AzPlayBehaviors.LOOP);

    private static final AzCommand RELOAD = AzCommand.create(
            "gun_controller",
            "reload", AzPlayBehaviors.PLAY_ONCE);


    public void fire_shot( Entity entity, ItemStack itemStack) {
        FIRE_SHOT.sendForItem(entity, itemStack);
    }

    public void fire_semi_auto( Entity entity, ItemStack itemStack) {
        FIRE_SEMI.sendForItem(entity, itemStack);
    }

    public void fire_auto( Entity entity, ItemStack itemStack) {
        FIRE_AUTO.sendForItem(entity, itemStack);
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
