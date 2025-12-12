package enigma.console.core;

import enigma.console.exceptions.ConsoleException;
import enigma.console.io.ConfigurationInputParser;
import enigma.console.io.ConsoleDisplay;
import enigma.console.io.ConsoleInputHandler;
import enigma.console.menu.Menu;
import enigma.console.menu.MenuImpl;
import enigma.console.menu.MenuItem;
import enigma.engine.core.Engine;
import enigma.engine.dtos.EngineDetails;
import enigma.engine.dtos.MachineCode;
import enigma.engine.dtos.SessionRecord;
import enigma.engine.exceptions.EngineException;
import enigma.loader.core.exceptions.LoaderException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ConsoleImpl implements Console {

    private boolean isRunning;
    private final Engine engine;
    private final ConsoleStateManager stateManager;
    private final ConsoleInputHandler inputHandler;
    private final ConsoleDisplay display;
    private final ConfigurationInputParser parser;
    private final Menu menu;

    public ConsoleImpl(Engine engine, 
                      ConsoleStateManager stateManager,
                      ConsoleInputHandler inputHandler,
                      ConsoleDisplay display,
                      ConfigurationInputParser parser) {
        this.engine = engine;
        this.stateManager = stateManager;
        this.inputHandler = inputHandler;
        this.display = display;
        this.parser = parser;
        this.menu = configureMenu();
        this.isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                List<MenuItem> availableItems = menu.getAvailableItems(stateManager.getCurrentState());
                display.showMenu(menu.getTitle(), availableItems);
                display.showPrompt("\nEnter your choice: ");
                int choice = inputHandler.readChoice(1, availableItems.size());
                availableItems.get(choice - 1).action().execute();
            } catch (ConsoleException e) {
                display.showError(e.getMessage());
            }
        }
    }

    private void loadConfiguration() {
        try {
            String filePath = inputHandler.readLine("Enter path to configuration file: ").trim();
            engine.loadFromFile(filePath);
            stateManager.transitionToInitialized();
            display.showSuccess("Configuration loaded successfully!");
        } catch (LoaderException e) {
            display.showError(e.getMessage());
        }
    }

    private void displaySpecifications() {
        EngineDetails engineDetails = engine.getEngineDetails();
        display.showEngineDetails(engineDetails);
    }

    private void configureManually() {
        try {
            displayAvailableComponents();
            
            List<Integer> rotorIds = collectRotorIds();
            List<Character> positions = collectPositions();
            int reflectorId = collectReflectorId();

            Collections.reverse(rotorIds);
            Collections.reverse(positions);
            
            MachineCode machineCode = new MachineCode(rotorIds, positions, reflectorId);
            engine.configureMachineManually(machineCode);
            
            stateManager.transitionToConfigured();
            display.showSuccess("Machine configured manually!");


            display.showInfo("Configuration: Rotors (L→R)=" + rotorIds + ", Positions (L→R)=" + positions + ", Reflector=" + reflectorId);
        } catch (EngineException e) {
            display.showError(e.getMessage());
        } catch (NumberFormatException e) {
            display.showError("Invalid input format. Please enter numeric values.");
        } catch (Exception e) {
            display.showError("Configuration failed: " + e.getMessage());
        }
    }
    
    private void displayAvailableComponents() {
        Set<Integer> availableRotorIds = engine.getAvailableRotorIds();
        Set<Integer> availableReflectorIds = engine.getAvailableReflectorIds();
        
        display.showInfo("=== Manual Machine Configuration ===");
        display.showInfo("Available Rotors: " + availableRotorIds);
        display.showInfo("Available Reflectors: " + availableReflectorIds);
        display.showInfo("Note: You must select at least 3 rotors (no duplicates)\n");
    }
    
    private List<Integer> collectRotorIds() {
        display.showInfo("Enter rotor IDs separated by commas (e.g 1,2,3):");
        String input = inputHandler.readLine("Rotors: ");
        return parser.parseRotorIds(input);
    }
    
    private List<Character> collectPositions() {
        display.showInfo("\nEnter initial positions as a contiguous string (e.g., 4D8A):");
        display.showInfo("Note: Rightmost character = position of rightmost rotor");
        String input = inputHandler.readLine("Positions: ");
        return parser.parsePositions(input);
    }
    
    private int collectReflectorId() {
        display.showInfo("\nEnter reflector ID:");
        String input = inputHandler.readLine("Reflector: ");
        return parser.parseReflectorId(input);
    }

    private void configureRandomly() {
        engine.configureMachineRandomly();
        stateManager.transitionToConfigured();
        display.showSuccess("Machine configured randomly!");
    }

    private void processInput() {
        try {
            String input = inputHandler.readLine("Enter text to process: ");
            String output = engine.process(input);
            display.showProcessResult(input, output);
        } catch (EngineException e) {
            display.showError(e.getMessage());
        }
    }

    private void resetMachine() {
        engine.resetConfiguration();
        display.showSuccess("Machine reset to initial configuration!");
    }

    private void displayStatistics() {
        List<SessionRecord> statistics = engine.getStatistics();
        display.showStatistics(statistics);
    }

    private void saveMachineState() {
        try {
            String filePath = inputHandler.readLine("Enter path to save machine state (without extension): ").trim();
            engine.saveState(filePath);
            display.showSuccess("Machine state saved successfully to " + filePath + ".enigma");
        } catch (EngineException e) {
            display.showError(e.getMessage());
        }
    }

    private void loadMachineState() {
        try {
            String filePath = inputHandler.readLine("Enter path to load machine state (without extension): ").trim();
            engine.loadState(filePath);
            stateManager.setState(EngineState.CONFIGURED);
            display.showSuccess("Machine state loaded successfully from " + filePath + ".enigma");
        } catch (EngineException e) {
            display.showError(e.getMessage());
        }
    }

    private void quit() {
        isRunning = false;
        display.showInfo("Goodbye!");
    }

    private Menu configureMenu() {
        Menu menu = new MenuImpl("Enigma Machine");

        menu.addItem(new MenuItem("Load machine configuration from file", EngineState.UNINITIALIZED, this::loadConfiguration));
        menu.addItem(new MenuItem("Load saved machine state from file", EngineState.UNINITIALIZED, this::loadMachineState));
        menu.addItem(new MenuItem("Display machine specifications", EngineState.INITIALIZED, this::displaySpecifications));
        menu.addItem(new MenuItem("Configure machine manually", EngineState.INITIALIZED, this::configureManually));
        menu.addItem(new MenuItem("Configure machine randomly",  EngineState.INITIALIZED, this::configureRandomly));
        menu.addItem(new MenuItem("Process input text", EngineState.CONFIGURED, this::processInput));
        menu.addItem(new MenuItem("Reset machine to initial configuration", EngineState.CONFIGURED, this::resetMachine));
        menu.addItem(new MenuItem("Save machine state to file", EngineState.CONFIGURED, this::saveMachineState));
        menu.addItem(new MenuItem("Display statistics and history", EngineState.INITIALIZED, this::displayStatistics));
        menu.addItem(new MenuItem("Exit", EngineState.UNINITIALIZED, this::quit));

        return menu;
    }
}
