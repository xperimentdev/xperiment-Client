package dev.xperiment.client;

import dev.xperiment.client.ModuleState.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

public final class XperimentHud implements HudRenderCallback {
    public static void register() {
        HudRenderCallback.EVENT.register(new XperimentHud());
    }

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null || !ModuleState.enabled(Module.HUD)) {
            return;
        }

        int x = HudConfig.x;
        int y = HudConfig.y;
        int line = 16;

        context.getMatrices().push();
        context.getMatrices().translate(x, y, 0);
        context.getMatrices().scale(HudConfig.scale, HudConfig.scale, 1.0f);

        int localY = 0;

        if (ModuleState.enabled(Module.FPS)) {
            draw(context, "FPS  " + client.getCurrentFps(), 0, localY);
            localY += line;
        }

        if (ModuleState.enabled(Module.COORDINATES)) {
            draw(context,
                    String.format("XYZ  %.1f  %.1f  %.1f",
                            client.player.getX(),
                            client.player.getY(),
                            client.player.getZ()),
                    0, localY);
            localY += line;
        }

        if (ModuleState.enabled(Module.KEYSTROKES)) {
            drawKeystrokes(context, client, 0, localY);
            localY += 50;
        }

        if (ModuleState.enabled(Module.CPS)) {
            draw(context,
                    "CPS  " + CpsCounter.getLeftCps() + " / " + CpsCounter.getRightCps(),
                    0, localY);
        }

        context.getMatrices().pop();
    }

    private static void drawKeystrokes(DrawContext context, MinecraftClient client, int x, int y) {
        drawKey(context, client, "W", GLFW.GLFW_KEY_W, x + 22, y);
        drawKey(context, client, "A", GLFW.GLFW_KEY_A, x, y + 22);
        drawKey(context, client, "S", GLFW.GLFW_KEY_S, x + 22, y + 22);
        drawKey(context, client, "D", GLFW.GLFW_KEY_D, x + 44, y + 22);
    }

    private static void drawKey(DrawContext context, MinecraftClient client,
                                String label, int key, int x, int y) {
        boolean pressed = GLFW.glfwGetKey(client.getWindow().getHandle(), key) == GLFW.GLFW_PRESS;

        int background = pressed ? 0xFF007AFF : 0xFFE5E5EA;
        int text = pressed ? 0xFFFFFFFF : 0xFF1C1C1E;

        context.fill(x, y, x + 20, y + 20, background);
        context.drawTextWithShadow(
                client.textRenderer,
                label,
                x + 7,
                y + 6,
                text
        );
    }

    private static void draw(DrawContext context, String text, int x, int y) {
        context.drawTextWithShadow(
                MinecraftClient.getInstance().textRenderer,
                text,
                x,
                y,
                0xFFFFFFFF
        );
    }
}
