package net.chakchak777.entities.azure;

import mod.azure.azurelib.common.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.common.animation.play_behavior.AzPlayBehaviors;
import net.chakchak777.entities.custom.CatostEntity;

public class CatostDispatcher {
    private static final AzCommand SLEEP_COMMAND = AzCommand.create(
            "catost_controller",
            "sleep",
            AzPlayBehaviors.LOOP
    );



    private final CatostEntity catostEntity;

    public CatostDispatcher(CatostEntity animatable) {
        this.catostEntity = animatable;
    }

    public void sleep() {
        SLEEP_COMMAND.sendForEntity(catostEntity);
    }
}
