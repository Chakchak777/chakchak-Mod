package net.chakchak777.items.custom.bubbleGun;

import net.chakchak777.blocks.ModBlocks;
import net.chakchak777.dataComponent.ModDataComponents;
import net.chakchak777.entities.custom.BubbleEntity;
import net.chakchak777.items.ModItems;
import net.chakchak777.network.SentDialogueLine;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BubbleGunItem extends Item {
    private final int maxBubbles;

    //TODO сделай ачивку за то чтоб выстрелить всю обойму за один раз

    private final BubbleGunDispatcher dispatcher;

    public enum BubblesMode {
        SHOTGUN,
        SEMI_AUTOMATIC,
        AUTOMATIC
    }

    public BubbleGunItem(Properties properties, int maxAmmo) {
        super(properties.component(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0)
                .component(ModDataComponents.BUBBLE_GUN_MODE.get(), 0));
        this.maxBubbles = maxAmmo;
        this.dispatcher = new BubbleGunDispatcher();
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (player.isCrouching()) {
            modeCycle(player, level, itemstack);
            return InteractionResultHolder.consume(itemstack);
        }


        int bubblesInMagazine = itemstack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);

        if (bubblesInMagazine > 0) {

            player.startUsingItem(usedHand);

            if (!level.isClientSide()) {
                BubblesMode currentMode = getCurrentMode(itemstack);
                switch (currentMode) {
                    case AUTOMATIC -> dispatcher.fire_auto(player, itemstack);
                    case SHOTGUN -> dispatcher.fire_shot(player, itemstack);
                    case SEMI_AUTOMATIC -> dispatcher.fire_semi_auto(player, itemstack);
                }
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
        BubblesMode currentMode = getCurrentMode(stack);
        switch (currentMode) {
            case SHOTGUN -> {
                return 10;
            }
            case SEMI_AUTOMATIC -> {
                return 7; //TODO кулдавн
            }
            case AUTOMATIC -> {
                return 400;
            }

        }
        return 15;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level level, LivingEntity livingEntity) {

        int bubblesInMagazine = itemstack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);
        if (livingEntity instanceof Player player) {
            if (!level.isClientSide()) {
                if (bubblesInMagazine > 0) {
                    BubblesMode currentMode = getCurrentMode(itemstack);


                    if (currentMode == BubblesMode.SHOTGUN) {
                        int bubblesCount = Math.min(bubblesInMagazine, level.random.nextInt(10, 30));


                        itemstack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), bubblesInMagazine - bubblesCount);
                        for (int i = 0; i < bubblesCount; i++) {
                            BubbleEntity bubbleEntity = new BubbleEntity(level, player);
                            bubbleEntity.setDamage(5F);
                            bubbleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.1F, 50F);
                            level.addFreshEntity(bubbleEntity);
                            player.getCooldowns().addCooldown(this, 5);


                        }
                    } else if (currentMode == BubblesMode.SEMI_AUTOMATIC) {

                        BubbleEntity bubbleEntity = new BubbleEntity(level, player);
                        bubbleEntity.setDamage(3F);
                        bubbleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4F, 0F);
                        level.addFreshEntity(bubbleEntity);
                        player.getCooldowns().addCooldown(this, 3);
                        itemstack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), bubblesInMagazine - 1);


                    }
                    level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
        return super.finishUsingItem(itemstack, level, livingEntity);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem() || slotChanged;
    }


    @Override
    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide) {
            BubblesMode currentMode = getCurrentMode(stack);
            if (currentMode == BubblesMode.AUTOMATIC) {
                int bubblesInMagazine = stack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);
                if (bubblesInMagazine > 0) {

                    if (remainingUseDuration % 2 == 0) {
                        BubbleEntity bubbleEntity = new BubbleEntity(level, player);
                        bubbleEntity.setDamage(0.5F);
                        bubbleEntity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 3F, 2F);
                        level.addFreshEntity(bubbleEntity);
                        stack.set(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), bubblesInMagazine - 1);
                        level.playSound(null, player.blockPosition(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
                    }
                    //если нет патрон
                } else {
                    if (!level.isClientSide()) {
                        finishUsingItem(stack, level, player);
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        int currentMode = getCurrentMode(stack).ordinal();

        tooltipComponents.add(Component.literal("Мыльное огнестрельное оружия" +
                        "требует мыльные пузыри для стрельбы")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));

        tooltipComponents.add(Component.literal("Режим:").withStyle(ChatFormatting.DARK_RED, ChatFormatting.UNDERLINE));

        if (currentMode==0){
            tooltipComponents.add(Component.literal("Дробовик:").withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE));

            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.literal("Выпускает рандомное кол-во пуль до 30").withStyle(ChatFormatting.YELLOW));

                tooltipComponents.add(Component.literal("• Урон •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Высокий").withStyle(ChatFormatting.RED)));

                tooltipComponents.add(Component.literal("• Точность •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Мелкая").withStyle(ChatFormatting.GREEN)));

                tooltipComponents.add(Component.literal("• Крутость во время применения •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Средняя").withStyle(ChatFormatting.YELLOW)));
            } else {
                tooltipComponents.add(Component.literal("Удерживайте [SHIFT] для просмотра свойств, чтобы менять режим [SHIFT] + [ПКМ]")
                        .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            }


        }else if (currentMode==1){
            tooltipComponents.add(Component.literal("Полуавтомат:").withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE));

            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.literal("Выпускает по одной пуле").withStyle(ChatFormatting.YELLOW));

                tooltipComponents.add(Component.literal("• Урон •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Средний").withStyle(ChatFormatting.YELLOW)));

                tooltipComponents.add(Component.literal("• Точность •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Высокая").withStyle(ChatFormatting.RED)));

                tooltipComponents.add(Component.literal("• Крутость во время применения •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Мелкая, ваще не круто").withStyle(ChatFormatting.GRAY)));
            } else {
                tooltipComponents.add(Component.literal("Удерживайте [SHIFT] для просмотра свойств, чтобы менять режим [SHIFT] + [ПКМ]")
                        .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            }


        } else if (currentMode==2) {
            tooltipComponents.add(Component.literal("Автомат:").withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE));

            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.literal("Выпускает пули с огромной скоростью").withStyle(ChatFormatting.YELLOW));

                tooltipComponents.add(Component.literal("• Урон •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Мелкий").withStyle(ChatFormatting.GRAY)));

                tooltipComponents.add(Component.literal("• Точность •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("Высокая, но чуть меньше").withStyle(ChatFormatting.RED)));

                tooltipComponents.add(Component.literal("• Крутость во время применения •")
                        .withStyle(ChatFormatting.DARK_RED).
                        append(Component.literal("МАКСИМАЛЬНАЯ ТЫ ТИПО РАТАТАТТАТАТАТАТАТА").withStyle(ChatFormatting.RED, ChatFormatting.UNDERLINE)));
            } else {
                tooltipComponents.add(Component.literal("Удерживайте [SHIFT] для просмотра свойств, чтобы менять режим [SHIFT] + [ПКМ]")
                        .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }










    public void modeCycle(Player player, Level level, ItemStack itemStack) {

        String text = "упс, баг, попробуй еще раз";
        BubblesMode currentMode = getCurrentMode(itemStack);
        BubblesMode nextMode;
        if (!level.isClientSide) {
            switch (currentMode) {
                case SHOTGUN -> {
                    nextMode = BubblesMode.SEMI_AUTOMATIC;
                    text = "ПОЛУАВТОМАТИЧЕСКИЙ";
                }
                case SEMI_AUTOMATIC -> {
                    nextMode = BubblesMode.AUTOMATIC;
                    text = "АВТОМАТИЧЕСКИЙ";
                }
                case AUTOMATIC -> {
                    nextMode = BubblesMode.SHOTGUN;
                    text = "ВЫСТРЕЛ С ДРОБАША";
                }
                default -> {
                    nextMode = BubblesMode.SHOTGUN;
                    text = "ВЫСТРЕЛ С ДРОБАША";
                }
            }
            setMode(itemStack, nextMode);
            player.displayClientMessage(
                    Component.literal("Мод изменен на " + text).withStyle(ChatFormatting.GOLD),
                    true);
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


        }
    }

    public int bubblesInInventory(Player player, boolean shrink, int count) {
        int bubblesInInventory = 0;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {

            ItemStack itemStack = player.getInventory().getItem(i);

            if (itemStack.getItem() == ModBlocks.BUBBLE_AMMO.asItem()) {
                if (shrink) {
                    itemStack.shrink(count);
                    bubblesInInventory += count;
                    break;
                }
                bubblesInInventory += itemStack.getCount();

            }
        }
        return bubblesInInventory;
    }

    public BubblesMode getCurrentMode(ItemStack stack) {

        int modeIndex = stack.getOrDefault(ModDataComponents.BUBBLE_GUN_MODE.get(), 0);

        BubblesMode[] modes = BubblesMode.values();
        return modes[modeIndex % modes.length];

    }

    public void setMode(ItemStack stack, BubblesMode mode) {
        stack.set(ModDataComponents.BUBBLE_GUN_MODE.get(), mode.ordinal());
    }
}
