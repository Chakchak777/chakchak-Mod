package net.chakchak777.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties CAT_FOOD=new FoodProperties.Builder().nutrition(1).alwaysEdible().saturationModifier(1)
            .effect(()->new MobEffectInstance(MobEffects.CONFUSION, 100,1),1).build();

    public static final FoodProperties VODKA=new FoodProperties.Builder().nutrition(1).saturationModifier(1).alwaysEdible().build();
}
