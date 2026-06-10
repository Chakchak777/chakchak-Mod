package net.chakchak777.items.custom.bubbleGun;

import net.chakchak777.dataComponent.ModDataComponents;
import net.chakchak777.entities.custom.BubbleEntity;
import net.chakchak777.entities.custom.LightningEntity;
import net.chakchak777.items.ModItems;
import net.chakchak777.items.custom.physicBook.PhysicBookDispatcher;
import net.chakchak777.network.SentDialogueLine;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BubbleGunItem extends Item {
    private final int maxBubbles;

    private final BubbleGunDispatcher dispatcher;



    public BubbleGunItem(Properties properties, int maxAmmo) {
        super(properties.component(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0));
        this.maxBubbles = maxAmmo;
        this.dispatcher = new BubbleGunDispatcher();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);


        int bubblesInMagazine = itemstack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);

        if (bubblesInMagazine > 0) {
            player.startUsingItem(usedHand);

            if (!level.isClientSide()) {
                dispatcher.fire(player, itemstack);
            }

            return InteractionResultHolder.consume(itemstack);

            } else {
            if (!level.isClientSide()) {
                reload(player, itemstack);
                player.getCooldowns().addCooldown(this, 104);
            } else {

                dispatcher.reload(player, itemstack);
            }
            return InteractionResultHolder.consume(itemstack);
        }
    }


    public void reload(Player player, ItemStack itemStack) {
        int currentBubbles = itemStack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);

        if (currentBubbles >= maxBubbles) return;

        if (!player.level().isClientSide()) {
            if (player.isCreative()) {
                itemStack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), maxBubbles);
            } else {
                if (bubblesInInventory(player, true, 1) == 0) {
                    PacketDistributor.sendToPlayer((ServerPlayer) player,
                            new SentDialogueLine(" у меня нет пузырьков", "player", 40));
                    return;
                } else {
                    itemStack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), maxBubbles);


                    player.level().playSound(null, player.blockPosition(), SoundEvents.IRON_DOOR_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }

            //клиент
        }/*else {
            dispatcher.reload(player,itemStack);
        }
        */
    }

    public int bubblesInInventory(Player player, boolean shrink, int count) {
        int bubblesInInventory = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {

            ItemStack itemStack = player.getInventory().getItem(i);


            if (itemStack.getItem() == ModItems.BUBBLES_AMMO.get()) {
                if (shrink) {
                    itemStack.shrink(count);
                    bubblesInInventory+=count;
                    break;
                }
                bubblesInInventory += itemStack.getCount();

            }
        }
        return bubblesInInventory;
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
                        !livingEntity.isUsingItem() && livingEntity instanceof Player player
        ) {
            if (player.getCooldowns().isOnCooldown(stack.getItem())) {
                dispatcher.reload(entity, stack);
            } else if (entity.isSprinting()) {
                dispatcher.run_idle(entity, stack);
            } else {
                dispatcher.idle(entity, stack);
            }
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 15;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level level, LivingEntity player) {

        int bubblesInMagazine = itemstack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);
        if (!level.isClientSide()) {
            if (bubblesInMagazine > 0) {

                int bubblesCount = level.random.nextInt(10, 30);

                for (int i = 0; i < bubblesCount; i++) {
                    BubbleEntity bubbleEntity = new BubbleEntity(level, player);
                    bubbleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.1F, 50F);
                    level.addFreshEntity(bubbleEntity);
                }
                itemstack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), bubblesInMagazine - 1);
                level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);


            }
        }
        return super.finishUsingItem(itemstack, level, player);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem() || slotChanged;
    }
}
