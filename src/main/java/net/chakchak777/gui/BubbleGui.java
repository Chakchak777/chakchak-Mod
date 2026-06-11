package net.chakchak777.gui;

import net.chakchak777.ChakchakMod;
import net.chakchak777.dataComponent.ModDataComponents;
import net.chakchak777.items.custom.bubbleGun.BubbleGunItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BubbleGui implements LayeredDraw.Layer {

    public static final List<ResourceLocation> Modes = List.of(
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "modes/shotgun"),
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "modes/semi"),
            ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "modes/auto")
    );


    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (mc.options.hideGui || player == null) return;

        ItemStack itemStack = player.getMainHandItem();



        if (itemStack.getItem() instanceof BubbleGunItem bubbleGunItem) {


            int bubblesInMagazine = itemStack.getOrDefault(ModDataComponents.BUBBLES_IN_MAGAZINE.get(), 0);

            int maxBubbles = 100;

            int bubblesInInventory = bubbleGunItem.bubblesInInventory(player, false, 0);

            int width = guiGraphics.guiWidth();
            int height = guiGraphics.guiHeight();


            int bubblesX = width/2+150;
            int bubblesY = height -20;
            int barWidth =10;
            int barHeight =70;



            ResourceLocation currentMode = Modes.get(bubbleGunItem.getCurrentMode(itemStack).ordinal());

            guiGraphics.blitSprite(currentMode, bubblesX-30, bubblesY, 90, 90);

            int fillBubbles = (int)(((double)bubblesInMagazine/maxBubbles)*barHeight);



            guiGraphics.fill(
                    bubblesX,
                    bubblesY-barHeight,
                    bubblesX+barWidth,
                    bubblesY,
                    0x80000000);

            if (fillBubbles>0){
                guiGraphics.fill(
                        bubblesX,
                        bubblesY-fillBubbles,
                        bubblesX+barWidth,
                        bubblesY,
                        0xFF33CCFF
                );}

            guiGraphics.drawString(mc.font,
                    String.valueOf(bubblesInMagazine),
                    bubblesX-20,
                    bubblesY-40,
                    0xFFFFFF,
                    true);




            guiGraphics.drawString(mc.font,
                    String.valueOf(bubblesInInventory),
                    bubblesX+20,
                    bubblesY-40,
                    0x4DDCFF,
                    true);
        }
    }
}
