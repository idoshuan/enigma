package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.RomanNumeral;

public class DuplicateReflectorIDException extends ReflectorException {
    public DuplicateReflectorIDException(int duplicateId) {
        super("Duplicate reflector ID '" + RomanNumeral.fromInt(duplicateId) + "'");
    }
}

