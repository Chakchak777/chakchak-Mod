package net.chakchak777.entities;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.CatostEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ChakchakMod.MODID);

    public static final Supplier<EntityType<CatostEntity>> CATOST =
            ENTITY_TYPES.register("catost", ()->
                    EntityType.Builder.of(CatostEntity::new, MobCategory.CREATURE)
                            .sized(1, 1).build("catost"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
