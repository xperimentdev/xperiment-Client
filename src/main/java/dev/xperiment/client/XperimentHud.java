package dev.xperiment.client;

import dev.xperiment.client.ModuleState.Module;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public final class XperimentHud implements HudRenderCallback {
    public static void register() { HudRenderCallback.EVENT.register(new XperimentHud()); }
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c.player == null || !ModuleState.enabled(Module.HUD)) return;
        int x=14,y=14,line=16;
        if (ModuleState.enabled(Module.FPS)) { draw(context,"FPS  "+c.getCurrentFps(),x,y); y+=line; }
        if (ModuleState.enabled(Module.COORDINATES)) { draw(context,String.format("XYZ  %.1f  %.1f  %.1f",c.player.getX(),c.player.getY(),c.player.getZ()),x,y); y+=line; }
        if (ModuleState.enabled(Module.KEYSTROKES)) { draw(context,"W A S D",x,y); y+=line; }
        if (ModuleState.enabled(Module.CPS)) draw(context,"CPS  0",x,y);
    }
    private static void draw(DrawContext ctx,String s,int x,int y) { ctx.drawTextWithShadow(MinecraftClient.getInstance().textRenderer,s,x,y,0xFFFFFFFF); }
}
