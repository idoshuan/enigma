package enigma.core.rotor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class RotorImpl implements Rotor, Serializable {

    private final Map<Integer, Integer> forwardWiring;
    private final Map<Integer, Integer> backwardWiring;
    private final int notchIndex;
    private final String rightColumn;

    public RotorImpl(Map<Integer, Integer> forwardWiring, int notchIndex, String rightColumn) {
        this.forwardWiring = Map.copyOf(forwardWiring);
        this.backwardWiring = invertMapping(forwardWiring);
        this.notchIndex = notchIndex;
        this.rightColumn = rightColumn;
    }

    @Override
    public int process(int input, Direction direction) {
        return (direction == Direction.FORWARD)
                ? forwardWiring.get(input)
                : backwardWiring.get(input);
    }

    @Override
    public int getNotchIndex() {
        return notchIndex;
    }

    @Override
    public int getInputRange() {
        return forwardWiring.size();
    }

    @Override
    public int charToPosition(char c) {
        return rightColumn.indexOf(Character.toUpperCase(c));
    }

    @Override
    public char positionToChar(int position) {
        return rightColumn.charAt(position);
    }

    @Override
    public int distanceFromNotch(int position) {
        return Math.floorMod(getNotchIndex() - position, getInputRange());
    }

    private static Map<Integer, Integer> invertMapping(Map<Integer, Integer> mapping) {
        Map<Integer, Integer> inverted = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : mapping.entrySet()) {
            inverted.put(entry.getValue(), entry.getKey());
        }

        return Map.copyOf(inverted);
    }
}
