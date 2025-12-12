package enigma.machine.components.configuration;

import enigma.core.rotor.Direction;
import enigma.core.rotor.Rotor;

public class MountedRotor {

    private final Rotor rotor;
    private int position;

    public MountedRotor(Rotor rotor, int initialPosition) {
        this.rotor = rotor;
        this.position = initialPosition;
    }

    public int process(int input, Direction direction) {
        int shiftedInput = Math.floorMod(input + position, rotor.getInputRange());
        int wiringOutput = rotor.process(shiftedInput, direction);
        return Math.floorMod(wiringOutput - position, rotor.getInputRange());
    }

    public void rotate() {
        position = (position + 1) % rotor.getInputRange();
    }

    public boolean isAtNotch() {
        return position == rotor.getNotchIndex();
    }

    public int getPosition() {
        return position;
    }

    public char getPositionAsChar() {
        return rotor.positionToChar(position);
    }

    public int positionFromNotch() {
        return rotor.distanceFromNotch(position);
    }
}
