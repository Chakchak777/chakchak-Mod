package net.chakchak777.datagen;

import net.chakchak777.ChakchakMod;
import net.chakchak777.items.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ChakchakMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        withExistingParent(ModItems.CATOST_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.RAT_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }
}