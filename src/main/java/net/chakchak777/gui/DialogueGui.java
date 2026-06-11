package net.chakchak777.gui;

import net.chakchak777.client.DialogueClientState;
import net.chakchak777.client.DialogueLineClientState;
import net.chakchak777.client.ModGuiTextures;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.components.PlayerFaceRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class DialogueGui implements LayeredDraw.Layer {

    private static final int BOX_WIDTH = 250;
    private static final int PADDING = 5;
    private static final int ICON_SIZE = 22;
    private static final int ICON_GAP = 5;
    private static final int BOTTOM_MARGIN = 28;
    private static final int ICON_TEXTURE_SIZE = 64;
    private static final String DEFAULT_ICON = "catost";

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {

        boolean isDialogueActive = DialogueClientState.isVisible();
        boolean isLineActive = DialogueLineClientState.isVisible();


        if (!isDialogueActive&&!isLineActive) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (mc.options.hideGui||player == null) {
            return;
        }
        PlayerSkin playerSkin = player.getSkin();

        Font font = mc.font;
        String text;
        String icon;

        if (isLineActive) {
            text = DialogueLineClientState.getText();
            icon = DialogueLineClientState.getIcon();
        } else {
            text = DialogueClientState.getCurrentText();
            icon = DialogueClientState.getCurrentIcon();
        }

        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();

        int maxTextWidth = BOX_WIDTH - PADDING * 2 - ICON_SIZE - ICON_GAP;
        List<FormattedCharSequence> lines = font.split(Component.literal(text), maxTextWidth);
        int textHeight = lines.size() * font.lineHeight;
        int boxHeight = Math.max(ICON_SIZE, textHeight) + PADDING * 2;

        int x = (screenWidth - BOX_WIDTH) / 2;
        int y = screenHeight - boxHeight - BOTTOM_MARGIN;

        guiGraphics.fill(x, y, x + BOX_WIDTH, y + boxHeight, 0xAA000000);
        guiGraphics.renderOutline(x, y, BOX_WIDTH, boxHeight, 0xFFD4AF37);


        int iconX = x + PADDING;
        int iconY = y + (boxHeight - ICON_SIZE) / 2;
        guiGraphics.fill(iconX - 1, iconY - 1, iconX + ICON_SIZE + 1, iconY + ICON_SIZE + 1, 0xFF2A2A2A);
        if ("player".equals(icon)) {
            PlayerFaceRenderer.draw(guiGraphics,
                    playerSkin, iconX, iconY, ICON_SIZE);
        } else {
            guiGraphics.blit(
                    iconTexture(icon),
                    iconX,
                    iconY,
                    ICON_SIZE,
                    ICON_SIZE,
                    0,
                    0,
                    ICON_TEXTURE_SIZE,
                    ICON_TEXTURE_SIZE,
                    ICON_TEXTURE_SIZE,
                    ICON_TEXTURE_SIZE
            );
        }


        int textX = x + PADDING + ICON_SIZE + ICON_GAP;
        int textY = y + (boxHeight - textHeight) / 2;

        for (int i = 0; i < lines.size(); i++) {
            guiGraphics.drawString(font, lines.get(i), textX, textY + i * font.lineHeight, 0xFFFFFF, true);
        }
    }

    private static ResourceLocation iconTexture(String icon) {
        if (icon == null || icon.isEmpty()) {
            return ModGuiTextures.dialogueIcon(DEFAULT_ICON);
        }
        return ModGuiTextures.dialogueIcon(icon);
    }
}
