package enigma.loader.core.exceptions.rotor;

public class InvalidNotchException extends RotorException {
    public InvalidNotchException(int rotorId, int notchPosition, int maxPosition) {
        super("Rotor ID " + rotorId + " - Notch position " + notchPosition + 
               " is out of range [1, " + maxPosition + "]");
    }
}

