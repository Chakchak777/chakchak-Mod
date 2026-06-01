package net.chakchak777.entities.dialogue;

import java.util.List;

public record DialogueScenario(boolean startsWithoutAnim, List<DialogueLine> lines) {
}
