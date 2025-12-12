package enigma.console.menu;

import enigma.console.core.EngineState;

public record MenuItem(String label, EngineState requiredEngineState, MenuAction action) {}