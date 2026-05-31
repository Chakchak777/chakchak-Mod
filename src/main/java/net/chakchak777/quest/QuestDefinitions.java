package net.chakchak777.quest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class QuestDefinitions {

    public record QuestInfo(String title, String description, List<String> goals, List<String> rewards) {
    }

    public static final List<String> QUEST_IDS = List.of("quest1", "quest2", "quest3", "quest4", "quest5");

    private static final Map<String, QuestInfo> QUESTS = Map.ofEntries(
            Map.entry("quest1", new QuestInfo(
                    "Базар с котостью",
                    "Котость любить базарить",
                    List.of("Поговорите с Котостью"),
                    List.of("Чекушка")
            ))
    );

    private QuestDefinitions() {
    }

    public static Optional<QuestInfo> get(String questId) {
        return Optional.ofNullable(QUESTS.get(questId));
    }

    public static String questIdForScenario(int scenario) {
        return switch (scenario) {
            case 1 -> "quest1";
            default -> "quest1";
        };
    }
}
