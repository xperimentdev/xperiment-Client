package dev.xperiment.client;

import java.util.EnumMap;
import java.util.Map;

public final class ModuleState {
    public enum Module { FPS, COORDINATES, KEYSTROKES, CPS, HUD, PERFORMANCE }
    private static final Map<Module, Boolean> states = new EnumMap<>(Module.class);
    static { for (Module m : Module.values()) states.put(m, true); }
    public static boolean enabled(Module m) { return states.get(m); }
    public static void toggle(Module m) { states.put(m, !enabled(m)); }
}
