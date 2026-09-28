package dev.xperiment.client;

import dev.xperiment.client.ModuleState.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

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

        int x = 14;
        int y = 14;
        int line = 16;

        if (ModuleState.enabled(Module.FPS)) {
            draw(context, "FPS  " + client.getCurrentFps(), x, y);
            y += line;
        }

        if (ModuleState.enabled(Module.COORDINATES)) {
            draw(context,
                    String.format("XYZ  %.1f  %.1f  %.1f",
                            client.player.getX(),
                            client.player.getY(),
                            client.player.getZ()),
                    x, y);
            y += line;
        }

        if (ModuleState.enabled(Module.KEYSTROKES)) {
            draw(context, "W A S D", x, y);
            y += line;
        }

        if (ModuleState.enabled(Module.CPS)) {
            draw(context,
                    "CPS  " + CpsCounter.getLeftCps() + " / " + CpsCounter.getRightCps(),
                    x, y);
        }
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
