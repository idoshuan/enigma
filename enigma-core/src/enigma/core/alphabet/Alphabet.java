package enigma.core.alphabet;

public interface Alphabet {

    int toIndex(char character);
    char toChar(int index);
    boolean contains(char character);
    int size();
    String getCharacters();
}
