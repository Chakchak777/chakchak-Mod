package net.chakchak777.entities.custom;

import net.chakchak777.entities.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;


public class BubbleEntity extends ThrowableProjectile {



    public int LIFE_TIME = level().random.nextInt(170, 270);


    public BubbleEntity(Level level, LivingEntity shooter) {
        super(ModEntities.BUBBLE.get(), shooter, level);
    }


    public BubbleEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }





    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        this.getGravity();
        super.tick();
        if (!this.level().isClientSide && this.tickCount >= LIFE_TIME){
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity target = result.getEntity();

            if (target instanceof LivingEntity livingTarget) {
                livingTarget.invulnerableTime = 0;
            }

            Vec3 old = target.getDeltaMovement();

            target.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);

            target.setDeltaMovement(old);

            target.hasImpulse=false;


            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    protected void applyGravity() {
        double motionX = this.getDeltaMovement().x;
        double motionY = this.getDeltaMovement().y;
        double motionZ = this.getDeltaMovement().z;


        if (this.isInWater()) {
            motionY = +0.04;
        }

        motionY-=0.0001;



        double airResistance = 0.98;
        motionX *= airResistance;
        motionZ *= airResistance;

        this.setDeltaMovement(motionX, motionY, motionZ);
    }
}
