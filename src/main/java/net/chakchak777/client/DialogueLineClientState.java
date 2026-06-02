package net.chakchak777.client;

public class DialogueLineClientState {
    private static String text = "";
    private static String icon = "";
    private static int ticksRemaining = 0;


    public static void showLine(String txt, String ico, int ticks){
        text = txt;
        icon = ico;
        ticksRemaining = ticks;
    }
    public static boolean isVisible(){
        return ticksRemaining>0 &&!text.isEmpty();
    }
    public static void clientTick(){
        if (ticksRemaining>0){
            ticksRemaining--;
        }
    }
    public static String getText() { return text; }
    public static String getIcon() { return icon; }
}
