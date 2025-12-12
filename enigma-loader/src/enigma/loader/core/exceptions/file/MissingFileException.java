package enigma.loader.core.exceptions.file;

public class MissingFileException extends FileException {
    public MissingFileException(String fileName) {
        super("File not found: '" + fileName + "'");
    }
}

