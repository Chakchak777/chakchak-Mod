package net.chakchak777.client;

public class DialogueClientState {
    private static String currentText = "";
    private static String currentIcon = "";

    private static boolean isVisible = false;

    public static void updateDialogue(String text, String icon) {
        currentText = text;
        currentIcon = icon;
        isVisible = !text.isEmpty();
    }

    public static String getCurrentText() {
        return currentText;
    }

    public static String getCurrentIcon() {
        return currentIcon;
    }

    public static boolean isVisible() {
        return isVisible;
    }
}
