package net.chakchak777.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.chakchak777.blocks.ModBlocks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BubbleAmmoBlock extends BlockItemBase{

    public static final IntegerProperty CLICKS = IntegerProperty.create("clicks", 0, 4);
    public static final MapCodec<BubbleAmmoBlock> CODEC = simpleCodec(BubbleAmmoBlock::new);


    public BubbleAmmoBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected IntegerProperty getCount() {
        return CLICKS;
    }

    @Override
    protected int getMaxClicks() {
        return 4;
    }

    @Override
    protected ItemStack getBaseItem() {
        return ModBlocks.BUBBLE_AMMO.toStack();
    }

    @Override
    protected SoundEvent getPickupSound() {
        return SoundEvents.ITEM_PICKUP;
    }

    @Override
    protected SoundEvent getPlaceSound() {
        return SoundEvents.GLASS_PLACE;
    }

    public static final VoxelShape NSWE_SHAPE = Block.box(2, 0, 2, 14, 3, 14);


    @Override
    protected VoxelShape getNS_Shape() {
        return NSWE_SHAPE ;
    }

    @Override
    protected VoxelShape getWE_Shape() {
        return NSWE_SHAPE;
    }

    @Override
    protected boolean needShiftForPlace() {
        return false;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
