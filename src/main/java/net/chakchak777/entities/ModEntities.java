package net.chakchak777.entities;

import net.chakchak777.ChakchakMod;
import net.chakchak777.entities.custom.*;
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
                    .sized(0.5f, 0.5f).build("fireball"));


    public static final Supplier<EntityType<LightningEntity>> LIGHTING =
            ENTITY_TYPES.register("lighting", () -> EntityType.Builder.<LightningEntity>of(LightningEntity::new, MobCategory.MISC)
                    .sized(1.5f, 0.5f).build("lighting"));

    public static final Supplier<EntityType<BubbleEntity>> BUBBLE =
            ENTITY_TYPES.register("bubble", () -> EntityType.Builder.<BubbleEntity>of(BubbleEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f).build("bubble"));

    public static final Supplier<EntityType<RatEntity>> RAT =
            ENTITY_TYPES.register("rat", ()->
                    EntityType.Builder.of(RatEntity::new, MobCategory.CREATURE)
                            .sized(0.5F, 0.4F).build("rat"));




    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
