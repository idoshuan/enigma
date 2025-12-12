package enigma.engine.exceptions.machine;

import enigma.engine.exceptions.EngineException;

public class MachineNotConfiguredException extends EngineException {
    public MachineNotConfiguredException() {
        super("Machine must be configured before this operation");
    }
}