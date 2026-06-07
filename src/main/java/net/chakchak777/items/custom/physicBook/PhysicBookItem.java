package net.chakchak777.items.custom.physicBook;

import net.chakchak777.entities.custom.FireBallEntity;
import net.chakchak777.gui.PhysicBookScreen;
import net.chakchak777.gui.QuestMenuScreen;
import net.chakchak777.network.SentDialogueLine;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class PhysicBookItem extends Item {

    private final PhysicBookDispatcher dispatcher;


    public PhysicBookItem(Properties properties) {
        super(properties);
        this.dispatcher = new PhysicBookDispatcher();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Minecraft mc = Minecraft.getInstance();
        ItemStack itemstack = player.getItemInHand(usedHand);



        if (player.isCrouching()){
            if (level.isClientSide) {
                mc.setScreen(new PhysicBookScreen());
                return InteractionResultHolder.consume(itemstack);
            }
            return InteractionResultHolder.consume(itemstack);
        }

        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void onUseTick(
            @NotNull Level level,
            @NotNull LivingEntity livingEntity,
            @NotNull ItemStack stack,
            int remainingUseDuration
    ) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        if (livingEntity instanceof Player player && !level.isClientSide()) {
            dispatcher.attack(player, stack);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 80;
    }

    @Override
    public void inventoryTick(
            @NotNull ItemStack stack,
            Level level,
            @NotNull Entity entity,
            int slotId,
            boolean isSelected
    ) {
        if (
                !level.isClientSide() && stack.is(this) && entity instanceof LivingEntity livingEntity &&
                        !livingEntity.isUsingItem() && livingEntity instanceof Player player &&
                        !player.getCooldowns().isOnCooldown(stack.getItem())
        ) {
            dispatcher.idle(entity, stack);
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide && livingEntity instanceof Player player) {
            dispatcher.idle(player, stack);
            FireBallEntity fireBallEntity = new FireBallEntity(level, player);
            player.getCooldowns().addCooldown(this, 300);
            PacketDistributor.sendToPlayer((ServerPlayer) player, new SentDialogueLine("Кажется мой мозг устал, смогу только через 15 секунд ", "player", 60));
            fireBallEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
            level.addFreshEntity(fireBallEntity);
        }



        return super.finishUsingItem(stack, level, livingEntity);
    }

}








