package net.chakchak777.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;

public class ClientEffectsHelper {
    public static void showTotemAnimation(ItemStack itemStack){
        Minecraft.getInstance().gameRenderer.displayItemActivation(itemStack);
    }
}
