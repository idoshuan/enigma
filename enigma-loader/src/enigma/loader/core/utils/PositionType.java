package enigma.loader.core.utils;

public enum PositionType {
    INPUT("input"),
    OUTPUT("output");

    private final String displayName;

    PositionType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

