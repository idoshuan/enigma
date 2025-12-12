package enigma.console.io;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationInputParser {

    public List<Integer> parseRotorIds(String input) {
        return Arrays.stream(input.trim().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public List<Character> parsePositions(String input) {
        return input.trim().toUpperCase().chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
    }

    public int parseReflectorId(String input) {
        return Integer.parseInt(input.trim());
    }
}

