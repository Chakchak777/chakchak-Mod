package net.chakchak777.entities.custom;

import mod.azure.azurelib.common.util.MoveAnalysis;
import net.chakchak777.entities.ModEntities;
import net.chakchak777.entities.azure.RatDispatcher;
import net.chakchak777.items.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class RatEntity extends TamableAnimal {

    public final MoveAnalysis moveAnalysis;

    public final RatDispatcher dispatcher;

    public RatEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.dispatcher = new RatDispatcher(this);
        this.moveAnalysis = new MoveAnalysis(this);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.MANGO);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.RAT.get().create(serverLevel);
    }
    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, CatostEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.0D, itemStack -> itemStack.is(ModItems.MANGO), false));
        this.goalSelector.addGoal(6, new MeleeAttackGoal(this, 1.1D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!this.isTame() && itemStack.is(ModItems.MANGO)) {
            if (!this.level().isClientSide()) {
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte) 7);

                } else this.level().broadcastEntityEvent(this, (byte) 6);


            }
            return InteractionResult.sidedSuccess(this.level().isClientSide());

        }
        if (this.isTame() && this.isOwnedBy(player)) {
            if ( hand == InteractionHand.MAIN_HAND && !itemStack.is(ModItems.MANGO)) {
                if (!this.level().isClientSide) {
                    Runnable animationRunner;
                    this.setOrderedToSit(!this.isOrderedToSit());
                    if (!this.isOrderedToSit()) {
                        animationRunner = dispatcher::sitUp;
                    }else {
                        animationRunner = dispatcher::sitSit;
                    }
                    animationRunner.run();
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }
    @Override
    public void tick() {
        super.tick();
        moveAnalysis.update();

        if (this.level().isClientSide) {
            var isMoving = moveAnalysis.isMovingHorizontally();
            Runnable animationRunner;
            if (!this.isAlive()) {
                animationRunner = dispatcher::idle;
            } else if (isMoving) {
                double currentSpeed = this.getDeltaMovement().horizontalDistance();
                if (this.isAggressive()|| currentSpeed > 0.02D) {
                    animationRunner = dispatcher::run;
                } else {
                    animationRunner = dispatcher::walk;
                }
            } else {
                animationRunner = dispatcher::idle;
            }
            animationRunner.run();
        }
    }
}
