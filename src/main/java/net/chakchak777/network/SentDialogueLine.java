package net.chakchak777.network;

import io.netty.buffer.ByteBuf;
import net.chakchak777.ChakchakMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SentDialogueLine (String text, String icon, int durationTicks) implements CustomPacketPayload {


    public static final Type<SentDialogueLine> TYPE = new Type<>
            (ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID,"dialogue_line"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SentDialogueLine> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, SentDialogueLine::text,
                    ByteBufCodecs.STRING_UTF8, SentDialogueLine::icon,
                    ByteBufCodecs.VAR_INT, SentDialogueLine::durationTicks,
                    SentDialogueLine::new
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
