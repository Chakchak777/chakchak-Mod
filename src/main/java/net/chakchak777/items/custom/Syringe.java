package net.chakchak777.items.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class Syringe extends Item {
    private static final int USE_DURATION = 24;

    public Syringe(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide()) {
            int roll = level.random.nextInt(4);

            if (roll == 1) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));
            } else {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));
            }

            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));

            level.playSound(
                    null,
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    SoundEvents.BEE_STING,
                    SoundSource.PLAYERS,
                    0.8F,
                    1.0F + level.random.nextFloat() * 0.2F
            );

            if (livingEntity instanceof Player player && !player.hasInfiniteMaterials()) {
                stack.shrink(1);
            }
        }

        return stack;
    }
}
