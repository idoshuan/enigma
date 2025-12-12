package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.RomanNumeral;

public class InvalidMappingCountException extends ReflectorException {
    public InvalidMappingCountException(int reflectorId, int expected, int actual) {
        super("Reflector ID " + RomanNumeral.fromInt(reflectorId) + 
               " - Expected " + expected + " mappings, found " + actual);
    }
}

