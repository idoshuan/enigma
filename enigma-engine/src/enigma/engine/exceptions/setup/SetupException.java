package enigma.engine.exceptions.setup;

import enigma.engine.exceptions.EngineException;

public abstract class SetupException extends EngineException {
    public SetupException(String message) {
        super(message);
    }
}

