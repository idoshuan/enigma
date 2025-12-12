package enigma.console.menu;

import enigma.console.core.EngineState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuImpl implements Menu {

    private final String title;
    private final List<MenuItem> items;

    public MenuImpl(String title) {
        this.title = title;
        this.items = new ArrayList<>();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public List<MenuItem> getAvailableItems(EngineState engineState) {
        return items.stream()
                .filter(item -> item.requiredEngineState().isAvailableIn(engineState))
                .collect(Collectors.toList());
    }
}
