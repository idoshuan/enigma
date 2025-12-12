package enigma.loader.core;

import enigma.core.Inventory;

public interface Loader {
    Inventory load(String path);
}
