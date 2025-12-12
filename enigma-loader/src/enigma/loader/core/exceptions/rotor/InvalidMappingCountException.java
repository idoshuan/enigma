package enigma.loader.core.exceptions.rotor;

public class InvalidMappingCountException extends RotorException {
    public InvalidMappingCountException(int rotorId, int expected, int actual) {
        super("Rotor ID " + rotorId + " - Expected " + expected + " mappings, found " + actual);
    }
}

