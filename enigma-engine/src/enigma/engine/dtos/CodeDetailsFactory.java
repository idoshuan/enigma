package enigma.engine.dtos;

import enigma.core.Inventory;
import enigma.core.rotor.Rotor;

import java.util.List;
import java.util.stream.IntStream;

public class CodeDetailsFactory {

    private final Inventory inventory;

    public CodeDetailsFactory(Inventory inventory) {
        this.inventory = inventory;
    }

    public CodeDetails create(MachineCode code) {
        return new CodeDetails(
                code.rotorIds(),
                code.positions(),
                code.reflectorId(),
                calculateNotchDistances(code)
        );
    }

    private List<Integer> calculateNotchDistances(MachineCode code) {
        return IntStream.range(0, code.rotorIds().size())
                .mapToObj(i -> {
                    Rotor rotor = inventory.getRotor(code.rotorIds().get(i));
                    int position = rotor.charToPosition(code.positions().get(i));
                    return rotor.distanceFromNotch(position);
                })
                .toList();
    }
}

