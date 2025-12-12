package enigma.engine.dtos;

import enigma.core.Inventory;

import java.io.Serializable;
import java.util.List;

public record MachineState(
        Inventory inventory,
        MachineCode initialCode,
        List<Character> currentPositions,
        List<SessionRecord> statistics
) implements Serializable {}

