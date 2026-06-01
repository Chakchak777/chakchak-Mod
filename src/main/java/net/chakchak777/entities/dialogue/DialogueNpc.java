package net.chakchak777.entities.dialogue;

import net.minecraft.server.level.ServerPlayer;

public interface DialogueNpc {
    ServerPlayer getDialoguePlayer();

    void onDialoguePlayerDisconnect(ServerPlayer player);
}
