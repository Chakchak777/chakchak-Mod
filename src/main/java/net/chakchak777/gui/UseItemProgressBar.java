package net.chakchak777.gui;

import net.chakchak777.items.custom.bubbleGun.BubbleGunItem;
import net.chakchak777.items.custom.physicBook.PhysicBookItem;
import net.chakchak777.items.custom.whistle.WhistleItem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class UseItemProgressBar implements LayeredDraw.Layer {
    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (mc.options.hideGui||player == null) return;

        if (player.isUsingItem()) {
            ItemStack useStack = player.getUseItem();


            if (useStack.getItem() instanceof PhysicBookItem ||useStack.getItem()
                    instanceof BubbleGunItem|useStack.getItem()
                    instanceof WhistleItem) {

                int maxDuration = useStack.getUseDuration(player);

                if (maxDuration <= 0) {
                    return;
                }

                float tickUsing = player.getTicksUsingItem();


                tickUsing += deltaTracker.getGameTimeDeltaTicks();


                float progress = tickUsing / (float) maxDuration;
                progress = Math.min(progress, 1.0f);


                int screenWidth = guiGraphics.guiWidth();
                int screenHeight = guiGraphics.guiHeight();
                int barWidth = 82;
                int barHeight = 8;
                int x = (screenWidth - barWidth) / 2;
                int y = screenHeight - 40;


                guiGraphics.fill(x, y, x + barWidth, y + barHeight, 0xFF000000);


                guiGraphics.fill(x + 1, y + 1, x + barWidth - 1, y + barHeight - 1, 0xFF222222);


                int progressWidth = (int) ((barWidth - 2) * progress);
                if (progressWidth > 0) {
                    guiGraphics.fill(x + 1, y + 1, x + 1 + progressWidth, y + barHeight - 1, 0xFF00AAFF);


                }
            }
        }
    }
}
