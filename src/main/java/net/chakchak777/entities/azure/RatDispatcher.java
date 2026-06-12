package net.chakchak777.entities.azure;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.chakchak777.entities.custom.RatEntity;

public class RatDispatcher extends RatAnimator {
    private static final AzCommand IDLE_COMMAND = AzCommand.create(
            "rat_controller",
            "idle",
            AzPlayBehaviors.LOOP
    );

    private static final AzCommand WALK_COMMAND = AzCommand.create(
            "rat_controller",
            "walk",
            AzPlayBehaviors.LOOP
    );



    private static final AzCommand SIT_SIT_COMMAND = AzCommand.create(
            "rat_controller",
            "sit",
            AzPlayBehaviors.HOLD_ON_LAST_FRAME
    );
    private static final AzCommand SIT_UP_COMMAND = AzCommand.create(
            "rat_controller",
            "up",
            AzPlayBehaviors.PLAY_ONCE
    );
    public static final AzCommand RUN=AzCommand.create(
            "rat_controller",
            "run",
            AzPlayBehaviors.LOOP
    );



    private final RatEntity ratEntity;

    public RatDispatcher(RatEntity animatable) {
        this.ratEntity = animatable;
    }

    public void idle() {
        IDLE_COMMAND.sendForEntity(ratEntity);
    }

    public void walk() {
        WALK_COMMAND.sendForEntity(ratEntity);
    }

    public void run() {
        RUN.sendForEntity(ratEntity);
    }


    public void sitSit() {
        SIT_SIT_COMMAND.sendForEntity(ratEntity);
    }
    public void sitUp() {
        SIT_UP_COMMAND.sendForEntity(ratEntity);
    }
}
