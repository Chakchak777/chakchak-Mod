package net.chakchak777.blocks.custom;

import com.mojang.serialization.MapCodec;
import net.chakchak777.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class EmptyBottleOfVodkaBlock extends HorizontalDirectionalBlock {

    public static final IntegerProperty CLICK_COUNT = IntegerProperty.create("clicks", 0, 2);

    public EmptyBottleOfVodkaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CLICK_COUNT, 0));
    }

    public static final MapCodec<EmptyBottleOfVodkaBlock> CODEC = simpleCodec(EmptyBottleOfVodkaBlock::new);


    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    public static final VoxelShape NS_SHAPE = Block.box(4, 0, 2.5, 12, 14, 13.5);

    public static final VoxelShape WE_SHAPE = Block.box(2.5, 0, 4, 13.5, 14, 12);


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case NORTH, SOUTH -> NS_SHAPE;
            case WEST, EAST -> WE_SHAPE;
            default -> NS_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICK_COUNT);
        builder.add(FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int currentClick = state.getValue(CLICK_COUNT);
        if (!level.isClientSide) {

            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);

            ItemStack stackToGive = new ItemStack(ModBlocks.EMPTY_BOTTLE_OF_VODKA.asItem(), 1);
            if (!player.addItem(stackToGive)) {
                player.drop(stackToGive, false);
            }

            if (currentClick > 0) {
                level.setBlock(pos, state.setValue(CLICK_COUNT, currentClick - 1), 3);
            } else {
                level.removeBlock(pos, false);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {


        if (!itemStack.is(ModBlocks.EMPTY_BOTTLE_OF_VODKA.asItem())) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        int currentClick = state.getValue(CLICK_COUNT);

        if (currentClick != 2) {
            if (!level.isClientSide) {
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                level.setBlock(pos, state.setValue(CLICK_COUNT, currentClick + 1), 3);
                level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        int currentClick = state.getValue(CLICK_COUNT);

        int count = currentClick+1;
        return List.of(new ItemStack(ModBlocks.EMPTY_BOTTLE_OF_VODKA.asItem(), count));
    }
}

