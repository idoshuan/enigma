package enigma.loader.core.exceptions;

public abstract class LoaderException extends RuntimeException {
    public LoaderException(String message) {
        super(message);
    }

    public LoaderException(Throwable cause) {
        super(cause);
    }
}

