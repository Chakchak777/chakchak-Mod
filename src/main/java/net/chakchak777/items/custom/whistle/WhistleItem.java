package net.chakchak777.items.custom.whistle;

import net.chakchak777.dataComponent.ModDataComponents;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.custom.RatEntity;
import net.chakchak777.items.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class WhistleItem extends Item {
    public WhistleItem(Properties properties) {
        super(properties.component(ModDataComponents.RATS_COUNT.get(), 1));
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);


        int ratsCount = level.random.nextInt(3, 9);

        itemstack.set(ModDataComponents.RATS_COUNT.get(), ratsCount);


        player.startUsingItem(usedHand);

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {

        if (livingEntity instanceof Player player) {
            if (!level.isClientSide() && level instanceof ServerLevel serverLevel && stack.is(ModItems.WHISTLE)) {

                Integer ratsCount = stack.get(ModDataComponents.RATS_COUNT.get());
                if (ratsCount != null) {

                    if (remainingUseDuration % 30 == 0) {


                        RandomSource random = player.getRandom();

                        int radius = 12;


                        double offsetX = (random.nextDouble() - 0.5) * 2 * radius;

                        double offsetZ = (random.nextDouble() - 0.5) * 2 * radius;


                        double spawnX = player.getX() + offsetX;
                        double spawnY = player.getY();
                        double spawnZ = player.getZ() + offsetZ;

                        RatEntity ratEntity = ModEntities.RAT.get().create(serverLevel);


                        if (ratEntity != null) {
                            ratEntity.moveTo(spawnX, spawnY, spawnZ, 0, 0);
                            ratEntity.tame(player);
                            ratEntity.setOrderedToSit(false);
                            serverLevel.addFreshEntity(ratEntity);

                            serverLevel.sendParticles(
                                    ParticleTypes.SMOKE,
                                    spawnX,
                                    spawnY,
                                    spawnZ,
                                    10,
                                    0.4, 0.4,0.4,
                                    0
                            );
                            level.playSound(
                                    null,
                                    spawnX,
                                    spawnY,
                                    spawnZ,
                                    SoundEvents.ILLUSIONER_MIRROR_MOVE,
                                    SoundSource.PLAYERS,
                                    1.0F,
                                    1.0F

                            );
                        }
                    }
                }
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (!level.isClientSide()) {
                player.getCooldowns().addCooldown(this, 400);
                return super.finishUsingItem(stack, level, livingEntity);
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {

        Integer ratsCount = stack.get(ModDataComponents.RATS_COUNT.get());
        if (ratsCount != null) {
            return 30 * ratsCount;
        }
        return 72000;
    }
}