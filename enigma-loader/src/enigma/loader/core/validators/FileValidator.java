package enigma.loader.core.validators;

import enigma.loader.core.exceptions.file.MissingFileException;
import enigma.loader.core.exceptions.file.InvalidFileExtensionException;

import java.io.File;

public class FileValidator implements Validator {
    private final String fileName;
    private final String expectedExtension;

    public FileValidator(String fileName, String expectedExtension) {
        this.fileName = fileName;
        this.expectedExtension = expectedExtension;
    }

    @Override
    public void validate() {
        ensureValidExtension();
        ensureFileExists();
    }

    private void ensureValidExtension() {
        if (!fileName.endsWith(expectedExtension)) {
            throw new InvalidFileExtensionException(fileName, expectedExtension);
        }
    }

    private void ensureFileExists() {
        if (!new File(fileName).exists()) {
            throw new MissingFileException(fileName);
        }
    }
}
