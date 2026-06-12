package net.chakchak777.dataComponent;

import com.mojang.serialization.Codec;
import net.chakchak777.ChakchakMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, ChakchakMod.MODID);

    public static final Supplier<DataComponentType<Integer>> BUBBLES_IN_MAGAZINE =
            DATA_COMPONENTS.register("bubbles_in_magazine", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build());

    public static final Supplier<DataComponentType<Integer>> BUBBLE_GUN_MODE =
            DATA_COMPONENTS.register("bubble_gun_mode", () ->
                    DataComponentType.<Integer>builder().persistent(Codec.INT).build());

    public static final Supplier<DataComponentType<Integer>> RATS_COUNT =
            DATA_COMPONENTS.register("rats_count", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.VAR_INT)
                            .build());




    public static void register(IEventBus eventBus){
        DATA_COMPONENTS.register(eventBus);
    }
}
