package enigma.engine.exceptions;

public class InvalidInputCharacterException extends EngineException {
    public InvalidInputCharacterException(char invalidChar, String validAlphabet) {
        super("Invalid character: '" + invalidChar + "'. Valid characters are: " + validAlphabet);
    }
}

