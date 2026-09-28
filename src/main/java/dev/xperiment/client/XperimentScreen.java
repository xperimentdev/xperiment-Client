package dev.xperiment.client;

import dev.xperiment.client.ModuleState.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class XperimentScreen extends Screen {
    public XperimentScreen(){ super(Text.literal("Xperiment Client")); }
    protected void init(){ int pw=650,ph=420,l=(width-pw)/2,t=(height-ph)/2; Module[] ms=Module.values(); for(int i=0;i<ms.length;i++){ Module m=ms[i]; int col=i%2,row=i/2; addDrawableChild(ButtonWidget.builder(Text.literal(label(m)),b->{ModuleState.toggle(m);b.setMessage(Text.literal(label(m)));}).dimensions(l+190+col*205,t+105+row*62,185,42).build()); } }
    private String label(Module m){ return pretty(m)+"   "+(ModuleState.enabled(m)?"ON":"OFF"); }
    private String pretty(Module m){ return switch(m){case FPS->"FPS";case COORDINATES->"Coordinates";case KEYSTROKES->"Keystrokes";case CPS->"CPS Display";case HUD->"HUD";case PERFORMANCE->"Performance";}; }
    public void render(DrawContext c,int mx,int my,float d){ c.fill(0,0,width,height,0xFFF2F2F7); int pw=650,ph=420,l=(width-pw)/2,t=(height-ph)/2; c.fill(l,t,l+pw,t+ph,0xFFFFFFFF); c.fill(l,t,l+5,t+ph,0xFF007AFF); c.fill(l+5,t,l+165,t+ph,0xFFF7F7F9); c.drawText(textRenderer,Text.literal("XPERIMENT"),l+24,t+27,0xFF111111,true); c.drawText(textRenderer,Text.literal("Client"),l+24,t+47,0xFF77777C,false); c.fill(l+18,t+88,l+147,t+126,0xFFE7F0FF); c.drawText(textRenderer,Text.literal("Modules"),l+39,t+101,0xFF007AFF,true); c.drawText(textRenderer,Text.literal("Xperiment Dashboard"),l+190,t+28,0xFF111111,true); c.drawText(textRenderer,Text.literal("Clean controls for your client"),l+190,t+49,0xFF77777C,false); super.render(c,mx,my,d); c.drawCenteredTextWithShadow(textRenderer,Text.literal("Right Shift  •  Close / Open"),width/2,t+ph-25,0xFF8E8E93); }
    public boolean shouldPause(){return false;}
}
