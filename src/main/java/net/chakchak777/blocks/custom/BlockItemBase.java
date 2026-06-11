package net.chakchak777.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.chakchak777.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BlockItemBase extends HorizontalDirectionalBlock {
    public BlockItemBase(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(getCount(), 0));
    }

    protected abstract IntegerProperty getCount();

    protected abstract int getMaxClicks();

    protected abstract ItemStack getBaseItem();
    protected abstract SoundEvent getPickupSound();
    protected abstract SoundEvent getPlaceSound();
    protected abstract VoxelShape getNS_Shape();
    protected abstract VoxelShape getWE_Shape();
    protected abstract boolean needShiftForPlace();


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getCount(), FACING);

    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH, SOUTH -> getNS_Shape();
            case WEST, EAST -> getWE_Shape();
            default -> getNS_Shape();
        };
    }



    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        IntegerProperty property = getCount();
        int currentClick = state.getValue(getCount());
        if (!level.isClientSide) {

            level.playSound(null, pos, getPickupSound(), SoundSource.BLOCKS, 1.0f, 1.0f);

            ItemStack stackToGive = new ItemStack(getBaseItem().getItem(), 1);
            if (!player.addItem(stackToGive)) {
                player.drop(stackToGive, false);
            }

            if (currentClick > 0) {
                level.setBlock(pos, state.setValue(property, currentClick - 1), 3);
            } else {
                level.removeBlock(pos, false);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {


        if (!itemStack.is(getBaseItem().getItem())||needShiftForPlace()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        IntegerProperty property = getCount();
        int currentClick = state.getValue(property);

        if (currentClick < getMaxClicks()) {
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                level.setBlock(pos, state.setValue(property, currentClick + 1), 3);
                level.playSound(null, pos, getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        int currentClick = state.getValue(getCount());
        int count = currentClick + 1;

        ItemStack stack =  getBaseItem().copy();
        stack.setCount(count);
        return List.of(stack);
    }
}

