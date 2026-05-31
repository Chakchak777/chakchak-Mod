package net.chakchak777.gui;

import net.chakchak777.ChakchakMod;
import net.chakchak777.client.ModKeyBinds;
import net.chakchak777.quest.QuestAdvancements;
import net.chakchak777.quest.QuestDefinitions;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestMenuScreen extends Screen {

    private static final int LEFT_PANEL_X = 20;
    private static final int TOP_Y = 30;
    private static final int BUTTON_WIDTH = 130;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_SPACING = 4;
    private static final int DIVIDER_X = 165;
    private static final int RIGHT_PANEL_X = DIVIDER_X + 12;

    private String selectedQuestId;

    public QuestMenuScreen() {
        super(Component.literal("Квесты"));
    }

    @Override
    protected void init() {
        super.init();
        clearWidgets();

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) {
            return;
        }

        ClientAdvancements clientAdvancements = mc.player.connection.getAdvancements();
        int visibleCount = 0;

        for (String questId : QuestDefinitions.QUEST_IDS) {
            if (!hasAdvancement(clientAdvancements, QuestAdvancements.unlockedId(questId))) {
                continue;
            }

            boolean completed = hasAdvancement(clientAdvancements, QuestAdvancements.completedId(questId));
            String title = QuestDefinitions.get(questId)
                    .map(QuestDefinitions.QuestInfo::title)
                    .orElse(questId);

            Component label = completed
                    ? Component.literal(title + " ✓").withStyle(ChatFormatting.GREEN)
                    : Component.literal(title);

            int buttonY = TOP_Y + visibleCount * (BUTTON_HEIGHT + BUTTON_SPACING);
            String capturedQuestId = questId;
            addRenderableWidget(Button.builder(label, button -> {
                selectedQuestId = capturedQuestId;
            }).bounds(LEFT_PANEL_X, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT).build());

            visibleCount++;
        }

        if (selectedQuestId != null && !isQuestVisible(clientAdvancements, selectedQuestId)) {
            selectedQuestId = null;
        }
    }

    private boolean isQuestVisible(ClientAdvancements clientAdvancements, String questId) {
        return hasAdvancement(clientAdvancements, QuestAdvancements.unlockedId(questId));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int dividerTop = TOP_Y - 6;
        int dividerBottom = this.height - 20;
        guiGraphics.fill(DIVIDER_X, dividerTop, DIVIDER_X + 1, dividerBottom, 0xFFFFFFFF);

        if (selectedQuestId == null) {
            guiGraphics.drawString(
                    this.font,
                    Component.literal("Выберите квест слева").withStyle(ChatFormatting.GRAY),
                    RIGHT_PANEL_X,
                    TOP_Y,
                    0xAAAAAA,
                    false
            );
            return;
        }

        QuestDefinitions.get(selectedQuestId).ifPresent(info -> drawQuestDetails(guiGraphics, info));
    }

    private void drawQuestDetails(GuiGraphics guiGraphics, QuestDefinitions.QuestInfo info) {
        int y = TOP_Y;

        guiGraphics.drawString(this.font, Component.literal(info.title()).withStyle(ChatFormatting.GOLD), RIGHT_PANEL_X, y, 0xFFFFFF, false);
        y += this.font.lineHeight + 8;

        guiGraphics.drawString(this.font, Component.literal("Описание").withStyle(ChatFormatting.YELLOW), RIGHT_PANEL_X, y, 0xFFFFFF, false);
        y += this.font.lineHeight + 2;
        y = drawWrapped(guiGraphics, info.description(), RIGHT_PANEL_X, y, this.width - RIGHT_PANEL_X - 20);
        y += 6;

        guiGraphics.drawString(this.font, Component.literal("Цели").withStyle(ChatFormatting.AQUA), RIGHT_PANEL_X, y, 0xFFFFFF, false);
        y += this.font.lineHeight + 2;
        for (String goal : info.goals()) {
            guiGraphics.drawString(this.font, Component.literal("• " + goal), RIGHT_PANEL_X, y, 0xDDDDDD, false);
            y += this.font.lineHeight + 2;
        }
        y += 4;

        guiGraphics.drawString(this.font, Component.literal("Награды").withStyle(ChatFormatting.LIGHT_PURPLE), RIGHT_PANEL_X, y, 0xFFFFFF, false);
        y += this.font.lineHeight + 2;
        for (String reward : info.rewards()) {
            guiGraphics.drawString(this.font, Component.literal("• " + reward), RIGHT_PANEL_X, y, 0xDDDDDD, false);
            y += this.font.lineHeight + 2;
        }
    }

    private int drawWrapped(GuiGraphics guiGraphics, String text, int x, int y, int maxWidth) {
        for (var line : this.font.split(Component.literal(text), maxWidth)) {
            guiGraphics.drawString(this.font, line, x, y, 0xCCCCCC, false);
            y += this.font.lineHeight;
        }
        return y;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ModKeyBinds.OPEN_QUEST_MENU.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean hasAdvancement(ClientAdvancements clientAdvancements, ResourceLocation id) {
        AdvancementHolder advancement = clientAdvancements.get(id);
        if (advancement == null) {
            return false;
        }

        try {
            java.lang.reflect.Field progressField = ClientAdvancements.class.getDeclaredField("progress");
            progressField.setAccessible(true);

            @SuppressWarnings("unchecked")
            Map<AdvancementHolder, AdvancementProgress> progressMap =
                    (Map<AdvancementHolder, AdvancementProgress>) progressField.get(clientAdvancements);

            AdvancementProgress progress = progressMap.get(advancement);
            return progress != null && progress.isDone();
        } catch (ReflectiveOperationException e) {
            ChakchakMod.LOGGER.error("Failed to read client advancement progress for {}", id, e);
            return false;
        }
    }
}
