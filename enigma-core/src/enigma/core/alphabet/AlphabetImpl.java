package enigma.core.alphabet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class AlphabetImpl implements Alphabet, Serializable {

    private final String characters;
    private final Map<Character, Integer> charToIndex;

    public AlphabetImpl(String alphabet) {
        this.characters = alphabet;
        this.charToIndex = new HashMap<>();
        for (int i = 0; i < alphabet.length(); i++) {
            charToIndex.put(alphabet.charAt(i), i);
        }
    }

    @Override
    public int toIndex(char character) {
        return charToIndex.getOrDefault(character, -1);
    }

    @Override
    public char toChar(int index) {
        if (index < 0 || index >= characters.length()) {
            throw new IndexOutOfBoundsException(
                    "Index " + index + " is out of bounds for alphabet of size " + characters.length());
        }
        return characters.charAt(index);
    }

    @Override
    public boolean contains(char character) {
        return charToIndex.containsKey(character);
    }

    @Override
    public int size() {
        return characters.length();
    }

    @Override
    public String getCharacters() {
        return characters;
    }
}
