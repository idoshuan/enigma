package enigma.engine.exceptions.setup;

public class InvalidPositionException extends SetupException {
    public InvalidPositionException(char position, String validPositions) {
        super("Position '" + position + "' is not valid. Valid positions are: " + validPositions);
    }
}

