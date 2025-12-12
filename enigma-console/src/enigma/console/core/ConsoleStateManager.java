package enigma.console.core;

public class ConsoleStateManager {

    private EngineState currentState;

    public ConsoleStateManager() {
        this.currentState = EngineState.UNINITIALIZED;
    }

    public EngineState getCurrentState() {
        return currentState;
    }

    public void transitionToInitialized() {
        currentState = EngineState.INITIALIZED;
    }

    public void transitionToConfigured() {
        currentState = EngineState.CONFIGURED;
    }

    public void setState(EngineState state) {
        currentState = state;
    }
}

