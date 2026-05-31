package net.chakchak777.items;

import net.chakchak777.ChakchakMod;
import net.chakchak777.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChakchakMod.MODID);


    public static final Supplier<CreativeModeTab> CHAKCHAK_MOD_CREATIVE_TAB = CREATIVE_MODE_TAB.register("classic_vortex_items_tab",
            ()-> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.VODKA.get()))
                    .title(Component.translatable("creativetab.chakchakmod"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.CATOST_SPAWN_EGG);
                        output.accept(ModItems.CATOST_FIGURINE);
                        output.accept(ModItems.CAT_FOOD);
                        output.accept(ModItems.VODKA);
                        output.accept(ModItems.EMPTY_BOTTLE_OF_VODKA);


                    }).build());



    public static void register (IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }



}