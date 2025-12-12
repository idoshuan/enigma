package enigma.engine.core;

import enigma.engine.dtos.EngineDetails;
import enigma.engine.dtos.MachineCode;
import enigma.engine.dtos.SessionRecord;

import java.util.List;
import java.util.Set;

public interface Engine {
    
    void loadFromFile(String path);
    EngineDetails getEngineDetails();
    void configureMachineManually(MachineCode setup);
    void configureMachineRandomly();
    String process(String input);
    void resetConfiguration();
    List<SessionRecord> getStatistics();
    Set<Integer> getAvailableRotorIds();
    Set<Integer> getAvailableReflectorIds();
    void saveState(String path);
    void loadState(String path);
}
