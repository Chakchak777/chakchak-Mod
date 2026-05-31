package net.chakchak777.items.custom;

import net.chakchak777.items.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Vodka extends Item {
    public Vodka(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide) {
            int roll = level.random.nextInt(2);


            if (roll == 1) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
            } else
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));

            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));





        }

        if (livingEntity instanceof Player player&&!player.hasInfiniteMaterials()){
            ItemStack emptyBottle = new ItemStack(ModItems.EMPTY_BOTTLE_OF_VODKA.get());

            if (resultStack.isEmpty()){
                return emptyBottle;
            } else if (!player.getInventory().add(emptyBottle)) {
                player.drop(emptyBottle,false);

            }
        }
        return resultStack;
    }
}