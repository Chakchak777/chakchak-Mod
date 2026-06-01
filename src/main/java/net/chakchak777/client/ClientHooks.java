package net.chakchak777.client;

import net.chakchak777.items.ModItems;
import net.chakchak777.network.DialoguePacket;
import net.chakchak777.network.TotemAnimationPacket;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientHooks {

    public static void handleDialogue(DialoguePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> DialogueClientState.updateDialogue(packet.txt(), packet.icon()));
    }

    public static void handleTotemAnimation(TotemAnimationPacket packet, IPayloadContext context) {

        context.enqueueWork(() -> ClientEffectsHelper.showTotemAnimation(packet.itemStack()));
    }

}
