package net.chakchak777.blocks.custom;

import net.chakchak777.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SyringeBlock extends Block{

    public static final IntegerProperty CLICK_COUNT =IntegerProperty.create("clicks", 0,2);
    public SyringeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(CLICK_COUNT, 0));
    }


    public static final VoxelShape SHAPE = Block.box(0,0,0 ,16, 4,16);


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CLICK_COUNT);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (!level.isClientSide){
            int currentClick = state.getValue(CLICK_COUNT);

            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f);

            ItemStack stackToGive = new ItemStack(ModItems.SYRINGE.get(), 1);
            if (!player.addItem(stackToGive)) {
                player.drop(stackToGive, false);
            }

            if (currentClick!=2){
                level.setBlock(pos, state.setValue(CLICK_COUNT, currentClick+1), 3);
            }else {
                level.removeBlock(pos, false);
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
