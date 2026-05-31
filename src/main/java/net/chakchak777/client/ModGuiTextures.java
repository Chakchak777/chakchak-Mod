package net.chakchak777.client;

import net.chakchak777.ChakchakMod;
import net.minecraft.resources.ResourceLocation;


public final class ModGuiTextures {

    private static final String DIALOGUE_ICONS = "textures/gui/dialogue/";

    public static final ResourceLocation CATOST_DIALOGUE = dialogueIcon("catost");

    private ModGuiTextures() {
    }

    public static ResourceLocation dialogueIcon(String name) {
        return ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, DIALOGUE_ICONS + name + ".png");
    }
}
