package dev.xperiment.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class XperimentClient implements ClientModInitializer {
    public static KeyBinding openGui;
    public void onInitializeClient() {
        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.xperiment.open_gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.xperiment"));
        XperimentHud.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> { while (openGui.wasPressed()) if (client.currentScreen == null) client.setScreen(new XperimentScreen()); });
    }
}
