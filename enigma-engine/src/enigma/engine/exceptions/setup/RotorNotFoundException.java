package enigma.engine.exceptions.setup;

public class RotorNotFoundException extends SetupException {
    public RotorNotFoundException(int rotorId) {
        super("Rotor ID " + rotorId + " does not exist in inventory");
    }
}

