package enigma.loader.core.exceptions.alphabet;

public class OddAlphabetLengthException extends AlphabetException {
    public OddAlphabetLengthException(int length) {
        super("Alphabet length " + length + " is odd. Must have an even number of characters");
    }
}

