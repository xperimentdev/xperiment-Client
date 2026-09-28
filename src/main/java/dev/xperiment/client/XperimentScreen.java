package dev.xperiment.client;

import dev.xperiment.client.ModuleState.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class XperimentScreen extends Screen {
    private static final int PANEL_W = 700;
    private static final int PANEL_H = 440;

    public XperimentScreen() {
        super(Text.literal("Xperiment Client"));
    }

    @Override
    protected void init() {
        int left = (width - PANEL_W) / 2;
        int top = (height - PANEL_H) / 2;

        Module[] modules = Module.values();

        for (int i = 0; i < modules.length; i++) {
            Module module = modules[i];
            int column = i % 2;
            int row = i / 2;

            addDrawableChild(ButtonWidget.builder(
                    Text.literal(label(module)),
                    button -> {
                        ModuleState.toggle(module);
                        button.setMessage(Text.literal(label(module)));
                    })
                    .dimensions(left + 205 + column * 225, top + 100 + row * 60, 205, 42)
                    .build());
        }

        addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Position +"),
                button -> HudConfig.x += 5
        ).dimensions(left + 205, top + 290, 98, 32).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("HUD Position -"),
                button -> HudConfig.x -= 5
        ).dimensions(left + 312, top + 290, 98, 32).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Scale +"),
                button -> HudConfig.scale = Math.min(2.0f, HudConfig.scale + 0.1f)
        ).dimensions(left + 419, top + 290, 98, 32).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Scale -"),
                button -> HudConfig.scale = Math.max(0.5f, HudConfig.scale - 0.1f)
        ).dimensions(left + 526, top + 290, 98, 32).build());
    }

    private String label(Module module) {
        return pretty(module) + "   " + (ModuleState.enabled(module) ? "ON" : "OFF");
    }

    private String pretty(Module module) {
        return switch (module) {
            case FPS -> "FPS";
            case COORDINATES -> "Coordinates";
            case KEYSTROKES -> "Keystrokes";
            case CPS -> "CPS Display";
            case HUD -> "HUD";
            case PERFORMANCE -> "Performance";
        };
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, 0xFFF2F2F7);

        int left = (width - PANEL_W) / 2;
        int top = (height - PANEL_H) / 2;

        context.fill(left, top, left + PANEL_W, top + PANEL_H, 0xFFFFFFFF);
        context.fill(left, top, left + 5, top + PANEL_H, 0xFF007AFF);
        context.fill(left + 5, top, left + 170, top + PANEL_H, 0xFFF7F7F9);

        context.drawText(textRenderer, Text.literal("XPERIMENT"),
                left + 24, top + 27, 0xFF111111, true);
        context.drawText(textRenderer, Text.literal("Client"),
                left + 24, top + 47, 0xFF77777C, false);

        context.fill(left + 18, top + 88, left + 152, top + 126, 0xFFE7F0FF);
        context.drawText(textRenderer, Text.literal("Modules"),
                left + 42, top + 101, 0xFF007AFF, true);

        context.drawText(textRenderer, Text.literal("Xperiment Dashboard"),
                left + 205, top + 27, 0xFF111111, true);
        context.drawText(textRenderer, Text.literal("Apple-inspired client controls"),
                left + 205, top + 49, 0xFF77777C, false);

        context.drawText(textRenderer, Text.literal("HUD"),
                left + 205, top + 270, 0xFF111111, true);
        context.drawText(textRenderer, Text.literal("Adjust position and scale"),
                left + 205, top + 282, 0xFF77777C, false);

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.literal("Right Shift  •  Close / Open"),
                width / 2,
                top + PANEL_H - 25,
                0xFF8E8E93
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
