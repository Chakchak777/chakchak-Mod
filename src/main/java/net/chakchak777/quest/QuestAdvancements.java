package net.chakchak777.quest;

import net.chakchak777.ChakchakMod;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class QuestAdvancements {

    public static final String CRITERION = "grant";

    private QuestAdvancements() {
    }

    public static ResourceLocation unlockedId(String questId) {
        return ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "quests/" + questId + "_unlocked");
    }

    public static ResourceLocation completedId(String questId) {
        return ResourceLocation.fromNamespaceAndPath(ChakchakMod.MODID, "quests/" + questId + "_completed");
    }

    public static void unlock(ServerPlayer player, String questId) {
        award(player, unlockedId(questId));
    }

    public static void complete(ServerPlayer player, String questId) {
        award(player, completedId(questId));
    }

    private static void award(ServerPlayer player, ResourceLocation advancementId) {
        if (player.getServer() == null) {
            return;
        }
        AdvancementHolder holder = player.getServer().getAdvancements().get(advancementId);
        if (holder != null) {
            player.getAdvancements().award(holder, CRITERION);
        }
    }
}
