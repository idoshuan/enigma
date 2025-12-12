package enigma.engine.exceptions.setup;

public class PositionCountMismatchException extends SetupException {
    public PositionCountMismatchException(int rotorCount, int positionCount) {
        super("Number of positions (" + positionCount + ") must match number of rotors (" + rotorCount + ")");
    }
}

