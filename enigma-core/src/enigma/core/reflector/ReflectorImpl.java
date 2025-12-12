package enigma.core.reflector;

import java.io.Serializable;
import java.util.Map;

public final class ReflectorImpl implements Reflector, Serializable {

    private final Map<Integer, Integer> wiring;

    public ReflectorImpl(Map<Integer, Integer> wiring) {
        this.wiring = Map.copyOf(wiring);
    }

    @Override
    public int reflect(int input) {
        return wiring.get(input);
    }
}
