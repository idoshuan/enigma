package enigma.engine.exceptions.setup;

public class ReflectorNotFoundException extends SetupException {
    public ReflectorNotFoundException(int reflectorId) {
        super("Reflector ID " + reflectorId + " does not exist in inventory");
    }
}

