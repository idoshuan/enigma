package enigma.loader.core.exceptions.rotor;

import java.util.Set;
import java.util.TreeSet;

public class NonContinuousIDException extends RotorException {
    public NonContinuousIDException(Set<Integer> outOfRangeIDs, int expectedCount) {
        super("Rotor IDs must be 1-" + expectedCount + " (continuous). Out of range: " + new TreeSet<>(outOfRangeIDs));
    }
}

