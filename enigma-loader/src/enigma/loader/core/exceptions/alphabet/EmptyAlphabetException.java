package enigma.loader.core.exceptions.alphabet;

public class EmptyAlphabetException extends AlphabetException {
    public EmptyAlphabetException() {
        super("Alphabet cannot be empty");
    }
}

