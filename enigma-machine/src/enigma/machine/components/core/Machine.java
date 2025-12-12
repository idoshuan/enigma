package enigma.machine.components.core;

import enigma.machine.components.configuration.MachineConfig;

public interface Machine {
    
    char process(char input);
    void setConfig(MachineConfig machineConfig);
    MachineConfig getConfig();
}
