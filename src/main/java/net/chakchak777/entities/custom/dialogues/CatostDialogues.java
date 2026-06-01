package net.chakchak777.entities.custom.dialogues;

import net.chakchak777.entities.dialogue.DialogueLine;
import net.chakchak777.entities.dialogue.DialogueScenario;

import java.util.List;

public class CatostDialogues {
    private CatostDialogues() {
    }

    public static final List<DialogueScenario> SCENARIOS = List.of(
            new DialogueScenario(true, List.of(
                    new DialogueLine("Привет, путник! Я не сплю — можешь поговорить.", 20, "catost"),
                    new DialogueLine("Это конец первого сценария.", 40, "catost")
            )),
            new DialogueScenario(false, List.of(
                    new DialogueLine("Мррр... кто там? Я только проснулся...", 40, "catost"),
                    new DialogueLine("Это я сучка", 40, "player"),
                    new DialogueLine("Ну ка поясни ка за базар хуйло плешивое", 40, "catost"),
                    new DialogueLine("Ладно ладно, успокойся мабой", 40, "player"),
                    new DialogueLine("Вот и всё блять, я дальше спать", 40, "catost")

            )),
            new DialogueScenario(true, List.of(
                    new DialogueLine("Рад снова тебя видеть! У меня есть кое-что для тебя...", 40, "catost"),
                    new DialogueLine("Нука", 40, "player"),
                    new DialogueLine("Держи водоочку", 40, "catost")

            ))
    );
}

