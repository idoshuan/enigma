package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.RomanNumeral;

import java.util.Set;
import java.util.TreeSet;

public class NonContinuousIDException extends ReflectorException {
    public NonContinuousIDException(Set<Integer> foundIds, int expectedCount) {
        super("Reflector IDs must be I-" + RomanNumeral.fromInt(expectedCount) + " (continuous). Out of range: " + new TreeSet<>(foundIds));
    }
}

