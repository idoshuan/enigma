package enigma.console.io;

import enigma.console.menu.MenuItem;
import enigma.engine.dtos.EngineDetails;
import enigma.engine.dtos.SessionRecord;

import java.util.List;

public class ConsoleDisplay {

    private final ConsoleOutputFormatter formatter;

    public ConsoleDisplay(ConsoleOutputFormatter formatter) {
        this.formatter = formatter;
    }

    public void showMenu(String title, List<MenuItem> items) {
        System.out.print(formatter.formatMenu(title, items));
    }

    public void showEngineDetails(EngineDetails details) {
        System.out.print(formatter.formatEngineDetails(details));
    }

    public void showProcessResult(String input, String output) {
        System.out.println(formatter.formatProcessResult(input, output));
    }

    public void showStatistics(List<SessionRecord> statistics) {
        System.out.println(formatter.formatStatistics(statistics));
    }

    public void showError(String message) {
        System.out.println(formatter.formatError(message));
    }

    public void showSuccess(String message) {
        System.out.println(formatter.formatSuccess(message));
    }

    public void showInfo(String message) {
        System.out.println(message);
    }

    public void showPrompt(String message) {
        System.out.print(message);
    }
}

