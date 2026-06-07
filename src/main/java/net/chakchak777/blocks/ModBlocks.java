package net.chakchak777.blocks;

import net.chakchak777.ChakchakMod;
import net.chakchak777.blocks.custom.EmptyBottleOfVodkaBlock;
import net.chakchak777.blocks.custom.LeafletBlock;
import net.chakchak777.blocks.custom.SyringeBlock;
import net.chakchak777.blocks.custom.VodkaBlock;
import net.chakchak777.items.ModItems;
import net.chakchak777.items.blockItems.EmptyBottleOfVodkaBlockItem;
import net.chakchak777.items.blockItems.VodkaBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ChakchakMod.MODID);


        private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> DeferredBlock<T> registerBlockItem(
            String name,
            Supplier<T> block,
            BiFunction<T, Item.Properties, ? extends BlockItem> itemFactory){

            DeferredBlock<T> toReturn = BLOCKS.register(name, block);

            ModItems.ITEMS.register(name,()-> itemFactory.apply(toReturn.get(), new Item.Properties()));
            return  toReturn;

    }


    public static final DeferredBlock<Block> SYRINGE_BLOCK = registerBlock("syringe_block",
            ()-> new SyringeBlock(BlockBehaviour.Properties.of().noOcclusion().strength(-1).noLootTable()));

    public static final DeferredBlock<Block> LEAFLET = registerBlock("leaflet",
            ()-> new LeafletBlock(BlockBehaviour.Properties.of().noOcclusion().strength(-1).noLootTable()));

    public static final DeferredBlock<Block> WALlPAPER_BLOCK = registerBlock("wallpaper",
            ()-> new Block(BlockBehaviour.Properties.of().noOcclusion().strength(10).noLootTable()));

    public static final DeferredBlock<Block> WALlPAPER_BLOCK2 = registerBlock("wallpaper2",
                ()-> new Block(BlockBehaviour.Properties.of().noOcclusion().strength(10).noLootTable()));

    public static final DeferredBlock<Block> WALlPAPER_BLOCK3 = registerBlock("wallpaper3",
                ()-> new Block(BlockBehaviour.Properties.of().noOcclusion().strength(10).noLootTable()));

    public static final DeferredBlock<Block> WALlPAPER_BLOCK4 = registerBlock("wallpaper4",
                ()-> new Block(BlockBehaviour.Properties.of().noOcclusion().strength(10).noLootTable()));


    public static final DeferredBlock<VodkaBlock> VODKA = registerBlockItem("vodka",
            () -> new VodkaBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).noOcclusion().instabreak().noLootTable()),
            (block, properties) -> new VodkaBlockItem(block,
                    properties.food(net.chakchak777.items.ModFoodProperties.VODKA).stacksTo(1).rarity(Rarity.EPIC))
    );

    public static final DeferredBlock<EmptyBottleOfVodkaBlock> EMPTY_BOTTLE_OF_VODKA = registerBlockItem("empty_bottle_of_vodka",
            () -> new EmptyBottleOfVodkaBlock(BlockBehaviour.Properties.of().sound(SoundType.GLASS).noOcclusion().instabreak().noLootTable()),
            (block, properties) -> new EmptyBottleOfVodkaBlockItem(block, properties.stacksTo(1).rarity(Rarity.EPIC))
    );


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }



}
