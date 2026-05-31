package net.chakchak777.client;


import com.mojang.blaze3d.platform.InputConstants;
import net.chakchak777.ChakchakMod;
import net.chakchak777.gui.QuestMenuScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = ChakchakMod.MODID, value = Dist.CLIENT)
public class ModKeyBinds {
    public static final KeyMapping OPEN_QUEST_MENU = new KeyMapping(
            "Открытие меню квестов",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "category.chakchakmod"
    );

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event){
        event.register(OPEN_QUEST_MENU);
    }
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) return;

        if (OPEN_QUEST_MENU.consumeClick() && mc.screen == null) {
            mc.setScreen(new QuestMenuScreen());
        }
    }
}
