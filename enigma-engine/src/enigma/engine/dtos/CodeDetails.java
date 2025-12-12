package enigma.engine.dtos;

import java.io.Serializable;
import java.util.List;

public record CodeDetails(
        List<Integer> rotorIds,
        List<Character> positions,
        int reflectorId,
        List<Integer> notchDistances
) implements Serializable {
    public CodeDetails {
        rotorIds = List.copyOf(rotorIds);
        positions = List.copyOf(positions);
        notchDistances = List.copyOf(notchDistances);
    }
}