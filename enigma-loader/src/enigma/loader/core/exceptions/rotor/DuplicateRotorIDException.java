package enigma.loader.core.exceptions.rotor;

public class DuplicateRotorIDException extends RotorException {
    public DuplicateRotorIDException(Integer duplicateID) {
        super("Duplicate rotor ID: " + duplicateID);
    }
}

