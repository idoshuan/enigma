package enigma.engine.dtos;

import java.io.Serializable;
import java.util.List;

public record MachineCode(
        List<Integer> rotorIds,
        List<Character> positions,
        int reflectorId
) implements Serializable {
    public MachineCode {
        rotorIds = List.copyOf(rotorIds);
        positions = List.copyOf(positions);
    }

    public MachineCode withPositions(List<Character> newPositions) {
        return new MachineCode(rotorIds, newPositions, reflectorId);
    }
}
