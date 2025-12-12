package enigma.engine.exceptions.setup;

public class InvalidRotorCountException extends SetupException {
    public InvalidRotorCountException(int minimumRequired, int provided) {
        super("Minimum " + minimumRequired + " rotors required, but " + provided + " provided");
    }
}

