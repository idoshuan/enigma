package enigma.loader.core.exceptions.rotor;

public class MinimumRotorsException extends RotorException {
    public MinimumRotorsException(Integer minimumRotors, Integer insertedRotors) {
        super("Minimum " + minimumRotors + " rotors required, but " + insertedRotors + " found");
    }
}

