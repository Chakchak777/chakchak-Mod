package net.chakchak777.network;

import net.chakchak777.ChakchakMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DialoguePacket(String txt, String icon) implements CustomPacketPayload {

    public static final Type<DialoguePacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "dialogue"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DialoguePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            DialoguePacket::txt,
            ByteBufCodecs.STRING_UTF8,
            DialoguePacket::icon,
            DialoguePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
