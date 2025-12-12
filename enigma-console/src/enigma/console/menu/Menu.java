package enigma.console.menu;

import enigma.console.core.EngineState;

import java.util.List;

public interface Menu {
    String getTitle();
    void addItem(MenuItem item);
    List<MenuItem> getAvailableItems(EngineState engineState);
}
