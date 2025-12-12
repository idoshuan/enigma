package enigma.loader.core.exceptions.rotor;

import enigma.loader.core.utils.MappingSide;

public class DuplicateMappingException extends RotorException {
    public DuplicateMappingException(int rotorId, MappingSide side, char duplicateValue) {
        super("Rotor ID " + rotorId + " - Duplicate " + side + " mapping '" + duplicateValue + "'");
    }
}

