package enigma.loader.core.validators;

import enigma.loader.core.exceptions.reflector.DuplicatePositionException;
import enigma.loader.core.exceptions.reflector.InvalidIDException;
import enigma.loader.core.exceptions.reflector.InvalidMappingCountException;
import enigma.loader.core.exceptions.reflector.InvalidPositionException;
import enigma.loader.core.exceptions.reflector.NoReflectorsException;
import enigma.loader.core.exceptions.reflector.NonContinuousIDException;
import enigma.loader.core.exceptions.reflector.SelfMappingException;
import enigma.loader.core.utils.PositionType;
import enigma.loader.core.utils.RomanNumeral;
import enigma.loader.xml.generated.BTEReflect;
import enigma.loader.xml.generated.BTEReflector;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectorValidator implements Validator {
    private final List<BTEReflector> reflectors;
    private final int alphabetLength;

    public ReflectorValidator(List<BTEReflector> reflectors, int alphabetLength) {
        this.reflectors = reflectors;
        this.alphabetLength = alphabetLength;
    }

    @Override
    public void validate() {
        ensureMinimumReflectors();
        ensureValidIds();
        ensureContinuousIds();
        ensureValidMappingCount();
        ensureNoSelfMappings();
        ensurePositionsInRange();
        ensureNoDuplicateInputPositions();
        ensureNoDuplicateOutputPositions();
    }

    private void ensureMinimumReflectors() {
        if (reflectors.isEmpty()) {
            throw new NoReflectorsException();
        }
    }

    private void ensureValidIds() {
        int maxValidId = RomanNumeral.values().length;

        for (BTEReflector reflector : reflectors) {
            int id = parseReflectorId(reflector);
            if (id < 1 || id > maxValidId) {
                throw new InvalidIDException(reflector.getId());
            }
        }
    }

    private void ensureContinuousIds() {
        Set<Integer> outOfRange = new HashSet<>();
        int count = reflectors.size();

        for (BTEReflector reflector : reflectors) {
            int id = parseReflectorId(reflector);
            if (id < 1 || id > count) {
                outOfRange.add(id);
            }
        }

        if (!outOfRange.isEmpty()) {
            throw new NonContinuousIDException(outOfRange, count);
        }
    }

    private int parseReflectorId(BTEReflector reflector) {
        String rawId = reflector.getId();
        return RomanNumeral.fromString(rawId)
                .map(RomanNumeral::toInt)
                .orElseThrow(() -> new InvalidIDException(rawId));
    }

    private void ensureValidMappingCount() {
        int expected = alphabetLength / 2;

        for (BTEReflector reflector : reflectors) {
            int actual = reflector.getBTEReflect().size();
            if (actual != expected) {
                int id = parseReflectorId(reflector);
                throw new InvalidMappingCountException(id, expected, actual);
            }
        }
    }

    private void ensureNoSelfMappings() {
        for (BTEReflector reflector : reflectors) {
            for (BTEReflect mapping : reflector.getBTEReflect()) {
                if (mapping.getInput() == mapping.getOutput()) {
                    int id = parseReflectorId(reflector);
                    throw new SelfMappingException(id, mapping.getInput());
                }
            }
        }
    }

    private void ensurePositionsInRange() {
        for (BTEReflector reflector : reflectors) {
            int id = parseReflectorId(reflector);
            for (BTEReflect mapping : reflector.getBTEReflect()) {
                ensurePositionInRange(id, PositionType.INPUT, mapping.getInput());
                ensurePositionInRange(id, PositionType.OUTPUT, mapping.getOutput());
            }
        }
    }

    private void ensurePositionInRange(int id, PositionType type, int position) {
        if (position < 1 || position > alphabetLength) {
            throw new InvalidPositionException(id, type, position, alphabetLength);
        }
    }

    private void ensureNoDuplicateInputPositions() {
        for (BTEReflector reflector : reflectors) {
            ensureNoDuplicatePositions(reflector, PositionType.INPUT);
        }
    }

    private void ensureNoDuplicateOutputPositions() {
        for (BTEReflector reflector : reflectors) {
            ensureNoDuplicatePositions(reflector, PositionType.OUTPUT);
        }
    }

    private void ensureNoDuplicatePositions(BTEReflector reflector, PositionType type) {
        Set<Integer> seen = new HashSet<>();
        for (BTEReflect mapping : reflector.getBTEReflect()) {
            int position = (type == PositionType.INPUT) ? mapping.getInput() : mapping.getOutput();
            if (!seen.add(position)) {
                int id = parseReflectorId(reflector);
                throw new DuplicatePositionException(id, type, position);
            }
        }
    }
}
