package enigma.engine.exceptions.setup;

public class DuplicateRotorException extends SetupException {
    public DuplicateRotorException(int duplicateId) {
        super("Rotor ID " + duplicateId + " is used more than once");
    }
}

