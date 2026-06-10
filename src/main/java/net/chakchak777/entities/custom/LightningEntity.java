package net.chakchak777.entities.custom;

import net.chakchak777.entities.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.UUID;

public class LightningEntity extends ThrowableProjectile {

    public static int LIFE_TIME = 200;


    private int maxBounces=10;

    private UUID lastHitEntity =null;

    public LightningEntity(Level level, LivingEntity shooter) {
        super(ModEntities.LIGHTING.get(), shooter, level);
    }


    public LightningEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        Entity hit= hitResult.getEntity();

        if (level().isClientSide)return;


        if (hit instanceof LivingEntity livingHit) {

            int damage = switch (this.maxBounces){
                case 10->6;
                case 9,8,7 -> 4;
                case 6,5,4 ->3;
                default -> 2;


            };
            livingHit.hurt(this.damageSources().magic(), damage);

        }

        this.maxBounces--;
        LIFE_TIME+=100;

        if (this.maxBounces<=0){
            this.discard();
            return;
        }

        LivingEntity nextTarget =  findNextTarget(hit);

        this.lastHitEntity = hit.getUUID();


        if (nextTarget!=null){
            Vec3 currentPos = this.position();

            Vec3 targetPos = nextTarget.position();

            Vec3 newMotion = targetPos.subtract(currentPos).normalize().scale(1.5);

            this.setDeltaMovement(newMotion);
            this.hasImpulse = true;
        }else {
            this.discard();

        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.maxBounces--;
            LIFE_TIME += 100;
            if (this.maxBounces <= 0) {
                this.discard();
                return;
            }
            LivingEntity nextTarget = findNextTarget(null);

            if (nextTarget != null) {
                Vec3 currentPos = this.position();

                Vec3 targetPos = nextTarget.position();

                Vec3 newMotion = targetPos.subtract(currentPos).normalize().scale(1.5);

                this.setDeltaMovement(newMotion);
                this.hasImpulse = true;
            } else {
                this.discard();
            }
        }
    }

    private LivingEntity findNextTarget(Entity currentHit) {

        int searchRadius = 10;

        AABB searchBox = this.getBoundingBox().inflate(searchRadius);


        List<LivingEntity> nearbyMobs = this.level().getEntitiesOfClass(LivingEntity.class, searchBox,
                entity -> entity != currentHit &&
                        entity != this.getOwner() &&
                        entity.isAlive()
                        && !entity.getUUID().equals(this.lastHitEntity)
        );


        LivingEntity closest = null;
        double closestDistance=searchRadius*searchRadius;

        for (LivingEntity mob : nearbyMobs){
            double distance = this.distanceToSqr(mob);
            if (distance<closestDistance){
                distance=closestDistance;
                closest=mob;
            }
        }
        return closest;
    }
    @Override
    public void tick() {
        this.setNoGravity(true);
        super.tick();
        if (!this.level().isClientSide && this.tickCount >= LIFE_TIME){
            this.discard();
        }
        Vec3 motion = this.getDeltaMovement();
        double horizontalDist = motion.horizontalDistance();

        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();

        this.setYRot((float)(Mth.atan2(motion.x, motion.z) * (180.0D / Math.PI)));
        this.setXRot((float)(Mth.atan2(motion.y, horizontalDist) * (180.0D / Math.PI)));
    }
}
