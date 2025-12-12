package enigma.machine.components.configuration;

import enigma.core.reflector.Reflector;

import java.util.List;

public interface MachineConfig {
    
    List<MountedRotor> getRotors();
    Reflector getReflector();
    List<Integer> getCurrentPositions();
    List<Character> getCurrentPositionsAsChars();
    List<Integer> getNotchDistances();
}
