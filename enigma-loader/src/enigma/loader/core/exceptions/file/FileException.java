package enigma.loader.core.exceptions.file;

import enigma.loader.core.exceptions.LoaderException;

public abstract class FileException extends LoaderException {
    public FileException(String message) {
        super(message);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}

