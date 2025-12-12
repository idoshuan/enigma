package enigma.core.rotor;

public interface Rotor {

    int process(int input, Direction direction);
    int getNotchIndex();
    int getInputRange();
    int charToPosition(char c);
    char positionToChar(int position);
    int distanceFromNotch(int position);
}
