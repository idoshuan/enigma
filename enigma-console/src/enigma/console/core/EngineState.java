package enigma.console.core;

public enum EngineState {
    UNINITIALIZED,
    INITIALIZED,
    CONFIGURED;

    public boolean isAvailableIn(EngineState currentState) {
        return switch (this) {
            case UNINITIALIZED -> true;
            case INITIALIZED -> currentState == INITIALIZED || currentState == CONFIGURED;
            case CONFIGURED -> currentState == CONFIGURED;
        };
    }
}
