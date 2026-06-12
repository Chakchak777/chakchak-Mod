package net.chakchak777.effects;


import net.chakchak777.ChakchakMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {


    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, ChakchakMod.MODID);

    public static final Holder<MobEffect> PHYSICS_KNOWLEDGE_EFFECT = MOB_EFFECTS.register("physics_knowledge",
            ()-> new PhysicsKnowledge(MobEffectCategory.NEUTRAL, 0xFF1E90FF));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
