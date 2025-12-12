package enigma.loader.core.validators;

import enigma.loader.core.exceptions.alphabet.DuplicateAlphabetCharacterException;
import enigma.loader.core.exceptions.alphabet.EmptyAlphabetException;
import enigma.loader.core.exceptions.alphabet.OddAlphabetLengthException;

import java.util.HashSet;
import java.util.Set;

public class AlphabetValidator implements Validator {

    private final String alphabet;

    public AlphabetValidator(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public void validate() {
        ensureNotEmpty();
        ensureEvenLength();
        ensureNoDuplicates();
    }

    private void ensureNotEmpty() {
        if (alphabet == null || alphabet.isEmpty()) {
            throw new EmptyAlphabetException();
        }
    }

    private void ensureEvenLength() {
        if (alphabet.length() % 2 != 0) {
            throw new OddAlphabetLengthException(alphabet.length());
        }
    }

    private void ensureNoDuplicates() {
        Set<Character> seen = new HashSet<>();
        for (char c : alphabet.toCharArray()) {
            if (!seen.add(c)) {
                throw new DuplicateAlphabetCharacterException(c);
            }
        }
    }
}

