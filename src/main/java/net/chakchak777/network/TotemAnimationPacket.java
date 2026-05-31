package net.chakchak777.network;

import net.chakchak777.ChakchakMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TotemAnimationPacket() implements CustomPacketPayload {

    public static final Type<TotemAnimationPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "totem_animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TotemAnimationPacket> STREAM_CODEC =
            StreamCodec.unit(new TotemAnimationPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
