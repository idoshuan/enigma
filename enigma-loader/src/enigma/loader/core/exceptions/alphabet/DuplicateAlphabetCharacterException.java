package enigma.loader.core.exceptions.alphabet;

public class DuplicateAlphabetCharacterException extends AlphabetException {
    public DuplicateAlphabetCharacterException(Character duplicateChar) {
        super("Duplicate character in alphabet: '" + duplicateChar + "'");
    }
}

