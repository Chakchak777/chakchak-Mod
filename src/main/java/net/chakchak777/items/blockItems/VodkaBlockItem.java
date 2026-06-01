package net.chakchak777.items.blockItems;

import net.chakchak777.blocks.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class VodkaBlockItem extends BlockItem {
    public VodkaBlockItem(Block block, Properties properties) {
        super(block, properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("Чистая водка. Говорят, если выпить, то станет хорошим оружием ближнего боя")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack resultStack = super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide) {
            int roll = level.random.nextInt(2);


            if (roll == 1) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
            } else
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0));

            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0));





        }

        if (livingEntity instanceof Player player&&!player.hasInfiniteMaterials()){
            ItemStack emptyBottle = new ItemStack(ModBlocks.EMPTY_BOTTLE_OF_VODKA.asItem());

            if (resultStack.isEmpty()){
                return emptyBottle;
            } else if (!player.getInventory().add(emptyBottle)) {
                player.drop(emptyBottle,false);

            }
        }
        return resultStack;
    }
}
