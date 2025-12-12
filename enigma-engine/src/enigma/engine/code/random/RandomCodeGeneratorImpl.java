package enigma.engine.code.random;

import enigma.core.Constants;
import enigma.core.Inventory;
import enigma.engine.dtos.MachineCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCodeGeneratorImpl implements RandomCodeGenerator {

    private final Random random;
    private final Inventory inventory;

    public RandomCodeGeneratorImpl(Inventory inventory) {
        this(inventory, new Random());
    }

    public RandomCodeGeneratorImpl(Inventory inventory, Random random) {
        this.inventory = inventory;
        this.random = random;
    }

    @Override
    public MachineCode generate() {
        int numOfRotorsToUse = random.nextInt(Constants.MINIMUM_ROTORS, inventory.getRotorCount() + 1);

        List<Integer> rotorIds = getRandomRotorIds(numOfRotorsToUse);
        List<Character> positions = getRandomPositions(numOfRotorsToUse);
        int reflectorId = getRandomReflectorId();

        return new MachineCode(rotorIds, positions, reflectorId);
    }

    private List<Integer> getRandomRotorIds(int count) {
        List<Integer> availableIds = new ArrayList<>(inventory.getRotorIds());
        Collections.shuffle(availableIds, random);
        return availableIds.subList(0, count);
    }

    private List<Character> getRandomPositions(int count) {
        return random.ints(count, 0, inventory.alphabet().size())
                .mapToObj(inventory.alphabet()::toChar)
                .toList();
    }

    private int getRandomReflectorId() {
        List<Integer> availableIds = new ArrayList<>(inventory.getReflectorIds());
        return availableIds.get(random.nextInt(availableIds.size()));
    }
}
