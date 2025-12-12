package enigma.loader.core.exceptions.rotor;

import enigma.loader.core.utils.MappingSide;

public class InvalidMappingCharacterException extends RotorException {
    public InvalidMappingCharacterException(int rotorId, MappingSide side, char invalidChar) {
        super("Rotor ID " + rotorId + " - " + side + " contains character not in ABC: '" + invalidChar + "'");
    }
}

