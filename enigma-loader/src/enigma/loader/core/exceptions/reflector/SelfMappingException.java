package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.RomanNumeral;

public class SelfMappingException extends ReflectorException {
    public SelfMappingException(int reflectorId, int position) {
        super("Reflector ID " + RomanNumeral.fromInt(reflectorId) + " - Position " + position + 
               " maps to itself (self-mapping not allowed)");
    }
}

