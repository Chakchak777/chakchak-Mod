package net.chakchak777.network;

import net.chakchak777.ChakchakMod;
import net.chakchak777.client.ClientHooks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.time.format.TextStyle;

@EventBusSubscriber(modid = ChakchakMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModNetworking {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(ChakchakMod.MODID);
        registrar.playToClient(
                DialoguePacket.TYPE,
                DialoguePacket.STREAM_CODEC,
                ClientHooks::handleDialogue
        );
        registrar.playToClient(
                TotemAnimationPacket.TYPE,
                TotemAnimationPacket.STREAM_CODEC,
                ClientHooks::handleTotemAnimation
        );
        registrar.playToClient(
                SentDialogueLine.TYPE,
                SentDialogueLine.STREAM_CODEC,
                ClientHooks::handleDialogueLine
        );
    }
}
