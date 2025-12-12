package enigma.loader.core;

import enigma.core.Inventory;
import enigma.loader.xml.generated.BTEEnigma;

public interface Parser {
    Inventory parse(BTEEnigma enigma);
}
