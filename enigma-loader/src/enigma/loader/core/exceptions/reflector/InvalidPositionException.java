package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.PositionType;
import enigma.loader.core.utils.RomanNumeral;

public class InvalidPositionException extends ReflectorException {
    public InvalidPositionException(int reflectorId, PositionType positionType, 
                                    int position, int maxPosition) {
        super("Reflector ID " + RomanNumeral.fromInt(reflectorId) + " - " + positionType + " position " + 
               position + " is out of range [1, " + maxPosition + "]");
    }
}

