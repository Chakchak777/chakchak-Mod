package net.chakchak777.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class LeafletBlock extends HorizontalDirectionalBlock {

    public static final MapCodec<LeafletBlock> CODEC = simpleCodec(LeafletBlock::new);

    public LeafletBlock(Properties properties) {
        super(properties);
    }

    public static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 15, 16, 16, 16);

    public static final VoxelShape WEST_SHAPE = Block.box(15, 0, 0, 16, 16, 16);

    public static final VoxelShape EAST_SHAPE = Block.box(0, 0, 0, 1, 16, 16);

    public static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 0, 16, 16, 1);


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH -> NORTH_SHAPE;
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
