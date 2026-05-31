package net.chakchak777.datagen;


import net.chakchak777.ChakchakMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChakchakMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
}