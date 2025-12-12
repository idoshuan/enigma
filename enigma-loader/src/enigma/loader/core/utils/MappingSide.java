package enigma.loader.core.utils;

public enum MappingSide {
    LEFT("left"),
    RIGHT("right");

    private final String displayName;

    MappingSide(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

