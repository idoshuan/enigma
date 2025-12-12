package enigma.loader.core.exceptions.reflector;

public class NoReflectorsException extends ReflectorException {
    public NoReflectorsException() {
        super("At least one reflector must be defined");
    }
}

