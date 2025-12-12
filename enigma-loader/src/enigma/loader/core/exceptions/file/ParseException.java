package enigma.loader.core.exceptions.file;

public class ParseException extends FileException {
    public ParseException(String fileName, String format, Throwable cause) {
        super("Failed to parse " + format + " file '" + fileName + "': " + cause.getMessage());
    }
}

