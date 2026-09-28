package dev.xperiment.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class XperimentClient implements ClientModInitializer {
    public static KeyBinding openGui;
    private static boolean lastLeft;
    private static boolean lastRight;

    @Override
    public void onInitializeClient() {
        openGui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.xperiment.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.xperiment"
        ));

        XperimentHud.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGui.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new XperimentScreen());
                }
            }

            trackClicks(client);
        });
    }

    private static void trackClicks(MinecraftClient client) {
        if (client.getWindow() == null) return;

        long handle = client.getWindow().getHandle();

        boolean left = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean right = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

        if (left && !lastLeft) {
            CpsCounter.recordLeftClick();
        }

        if (right && !lastRight) {
            CpsCounter.recordRightClick();
        }

        lastLeft = left;
        lastRight = right;
    }
}
