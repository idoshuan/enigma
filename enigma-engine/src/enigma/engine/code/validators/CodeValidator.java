package enigma.engine.code.validators;

import enigma.core.Constants;
import enigma.core.Inventory;
import enigma.engine.dtos.MachineCode;
import enigma.engine.exceptions.setup.*;

import java.util.HashSet;
import java.util.Set;

public class CodeValidator {
    private final Inventory inventory;

    public CodeValidator(Inventory inventory) {
        this.inventory = inventory;
    }

    public void validate(MachineCode setup) {
        validateRotorCount(setup);
        validateNoDuplicateRotors(setup);
        validateRotorIdsExist(setup);
        validatePositionCount(setup);
        validatePositionRange(setup);
        validateReflectorIdExists(setup);
    }

    private void validateRotorCount(MachineCode setup) {
        if (setup.rotorIds().size() < Constants.MINIMUM_ROTORS) {
            throw new InvalidRotorCountException(Constants.MINIMUM_ROTORS, setup.rotorIds().size());
        }
    }

    private void validateNoDuplicateRotors(MachineCode setup) {
        Set<Integer> seen = new HashSet<>();
        for (int rotorId : setup.rotorIds()) {
            if (!seen.add(rotorId)) {
                throw new DuplicateRotorException(rotorId);
            }
        }
    }

    private void validateRotorIdsExist(MachineCode setup) {
        for (int rotorId : setup.rotorIds()) {
            if (!inventory.getRotorIds().contains(rotorId)) {
                throw new RotorNotFoundException(rotorId);
            }
        }
    }

    private void validatePositionCount(MachineCode setup) {
        if (setup.positions().size() != setup.rotorIds().size()) {
            throw new PositionCountMismatchException(setup.rotorIds().size(), setup.positions().size());
        }
    }

    private void validatePositionRange(MachineCode setup) {
        for (char position : setup.positions()) {
            if (!inventory.alphabet().contains(position)) {
                throw new InvalidPositionException(position, inventory.alphabet().toString());
            }
        }
    }

    private void validateReflectorIdExists(MachineCode setup) {
        if (!inventory.getReflectorIds().contains(setup.reflectorId())) {
            throw new ReflectorNotFoundException(setup.reflectorId());
        }
    }

}
