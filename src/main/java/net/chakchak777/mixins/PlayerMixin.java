package net.chakchak777.mixins;


import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void swing(InteractionHand hand) {
        if (!(this.getUseItem().getItem() instanceof PhysicBookItem))
            super.swing(hand);
    }
}
