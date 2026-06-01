package net.chakchak777.network;

import net.chakchak777.ChakchakMod;
import net.chakchak777.blocks.ModBlocks;
import net.chakchak777.items.ModItems;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public record TotemAnimationPacket(ItemStack itemStack) implements CustomPacketPayload {

    public static final Type<TotemAnimationPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "totem_animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TotemAnimationPacket> STREAM_CODEC =
            ItemStack.STREAM_CODEC.map((ItemStack t) -> new TotemAnimationPacket(new ItemStack(ModBlocks.VODKA.asItem())), TotemAnimationPacket::itemStack);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
