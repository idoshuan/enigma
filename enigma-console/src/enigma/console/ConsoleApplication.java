package enigma.console;

import enigma.console.core.Console;
import enigma.console.core.ConsoleImpl;
import enigma.console.core.ConsoleStateManager;
import enigma.console.io.ConfigurationInputParser;
import enigma.console.io.ConsoleDisplay;
import enigma.console.io.ConsoleInputHandler;
import enigma.console.io.ConsoleOutputFormatter;
import enigma.engine.core.Engine;
import enigma.engine.core.EngineImpl;
import enigma.loader.core.Loader;
import enigma.loader.xml.XMLLoader;

import java.util.Scanner;

public class ConsoleApplication {

    private final Console console;

    public ConsoleApplication() {
        Scanner scanner = new Scanner(System.in);
        ConsoleInputHandler inputHandler = new ConsoleInputHandler(scanner);

        ConsoleOutputFormatter formatter = new ConsoleOutputFormatter();
        ConsoleDisplay display = new ConsoleDisplay(formatter);
        ConfigurationInputParser parser = new ConfigurationInputParser();

        ConsoleStateManager stateManager = new ConsoleStateManager();

        Loader loader = new XMLLoader();
        Engine engine = new EngineImpl(loader);

        this.console = new ConsoleImpl(engine, stateManager, inputHandler, display, parser);
    }

    public void run() {
        console.run();
    }
}

