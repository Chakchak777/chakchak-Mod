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

public class EmptyBottleOfVodkaBlock extends BlockItemBase {

    public static final IntegerProperty CLICKS = IntegerProperty.create("clicks", 0, 2);
    public static final MapCodec<VodkaBlock> CODEC = simpleCodec(VodkaBlock::new);

    public EmptyBottleOfVodkaBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected IntegerProperty getCount() {
        return CLICKS;
    }

    @Override
    protected int getMaxClicks() {
        return 2;
    }

    @Override
    protected ItemStack getBaseItem() {
        return ModBlocks.EMPTY_BOTTLE_OF_VODKA.toStack();
    }

    @Override
    protected SoundEvent getPickupSound() {
        return SoundEvents.ITEM_PICKUP;
    }

    @Override
    protected SoundEvent getPlaceSound() {
        return SoundEvents.GLASS_PLACE;
    }
    public static final VoxelShape NS_SHAPE = Block.box(4, 0, 2.5, 12, 14, 13.5);

    public static final VoxelShape WE_SHAPE = Block.box(2.5, 0, 4, 13.5, 14, 12);

    @Override
    protected VoxelShape getNS_Shape() {
        return NS_SHAPE;
    }

    @Override
    protected VoxelShape getWE_Shape() {
        return WE_SHAPE;
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

