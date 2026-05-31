package net.chakchak777.blocks;

import net.chakchak777.ChakchakMod;
import net.chakchak777.blocks.custom.LeafletBlock;
import net.chakchak777.blocks.custom.SyringeBlock;
import net.chakchak777.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChakchakMod.MODID);


        private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    public static final DeferredBlock<Block> SYRINGE_BLOCK = registerBlock("syringe_block",
            ()-> new SyringeBlock(BlockBehaviour.Properties.of().noOcclusion().strength(-1).noLootTable()));

    public static final DeferredBlock<Block> LEAFLET = registerBlock("leaflet",
            ()-> new LeafletBlock(BlockBehaviour.Properties.of().noOcclusion().strength(-1).noLootTable()));

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }



}
