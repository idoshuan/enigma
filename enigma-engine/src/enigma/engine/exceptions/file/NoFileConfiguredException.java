package enigma.engine.exceptions.file;

import enigma.engine.exceptions.EngineException;

public class NoFileConfiguredException extends EngineException {
    public NoFileConfiguredException() {
        super("No file configured");
    }
}

