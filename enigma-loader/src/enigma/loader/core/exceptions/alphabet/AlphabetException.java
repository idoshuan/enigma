package enigma.loader.core.exceptions.alphabet;

import enigma.loader.core.exceptions.LoaderException;

public abstract class AlphabetException extends LoaderException {
    public AlphabetException(String message) {
        super(message);
    }
}

