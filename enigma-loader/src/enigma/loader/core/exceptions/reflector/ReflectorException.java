package enigma.loader.core.exceptions.reflector;

import enigma.loader.core.exceptions.LoaderException;

public abstract class ReflectorException extends LoaderException {
    public ReflectorException(String message) {
        super(message);
    }
}

