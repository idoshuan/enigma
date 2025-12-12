package enigma.loader.core.utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum RomanNumeral {
    I(1), II(2), III(3), IV(4), V(5);

    private final int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

    public static Optional<RomanNumeral> fromString(String s) {
        if (s == null) {
            return Optional.empty();
        }
        try {
            return Optional.of(RomanNumeral.valueOf(s));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static RomanNumeral fromInt(int value) {
        for (RomanNumeral rn : values()) {
            if (rn.value == value) {
                return rn;
            }
        }
        throw new IllegalArgumentException("No Roman numeral for value: " + value);
    }

    public static String validValues() {
        return Arrays.stream(values())
            .map(RomanNumeral::name)
            .collect(Collectors.joining(", "));
    }
}

