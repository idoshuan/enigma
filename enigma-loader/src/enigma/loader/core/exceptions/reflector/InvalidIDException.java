package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.RomanNumeral;

public class InvalidIDException extends ReflectorException {
    public InvalidIDException(String invalidId) {
        super("Invalid reflector ID '" + invalidId + 
               "'. Must be a Roman numeral (" + RomanNumeral.validValues() + ")");
    }
}

