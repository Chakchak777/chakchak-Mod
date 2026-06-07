package net.chakchak777.entities.custom;

import net.chakchak777.entities.ModEntities;
import net.chakchak777.items.ModItems;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class FireBallEntity extends ThrowableProjectile {

    public static final int LIFE_TIME = 200;


    public FireBallEntity(Level level, LivingEntity shooter) {
        super(ModEntities.FIREBALL.get(), shooter, level);
    }


    public FireBallEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }





    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        this.setNoGravity(true);
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
            target.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
            target.igniteForSeconds(5);
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
}
