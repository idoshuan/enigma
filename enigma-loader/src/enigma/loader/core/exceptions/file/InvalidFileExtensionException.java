package enigma.loader.core.exceptions.file;

public class InvalidFileExtensionException extends FileException {
    public InvalidFileExtensionException(String fileName, String expectedExtension) {
        super("Invalid extension '" + getCurrentExtension(fileName) + 
               "' in '" + fileName + "'. Expected: " + expectedExtension);
    }

    private static String getCurrentExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex != -1) ? fileName.substring(lastDotIndex) : "(none)";
    }
}

