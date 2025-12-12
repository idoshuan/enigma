package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.utils.PositionType;
import enigma.loader.core.utils.RomanNumeral;

public class DuplicatePositionException extends ReflectorException {
    public DuplicatePositionException(int reflectorId, PositionType positionType, int position) {
        super("Reflector ID " + RomanNumeral.fromInt(reflectorId) + 
               " - Duplicate " + positionType + " position " + position);
    }
}

