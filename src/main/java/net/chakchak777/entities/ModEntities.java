package net.chakchak777.entities;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.CatostEntity;
import net.chakchak777.entities.custom.FireBallEntity;
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


    public static final Supplier<EntityType<FireBallEntity>> FIREBALL =
            ENTITY_TYPES.register("fireball", () -> EntityType.Builder.<FireBallEntity>of(FireBallEntity::new, MobCategory.MISC)
                    .sized(0.5f, 1.15f).build("fireball"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
