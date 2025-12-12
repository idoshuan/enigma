package enigma.console.io;

import enigma.console.menu.MenuItem;
import enigma.engine.dtos.CodeDetails;
import enigma.engine.dtos.EngineDetails;
import enigma.engine.dtos.MessageRecord;
import enigma.engine.dtos.SessionRecord;
import enigma.loader.core.utils.RomanNumeral;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConsoleOutputFormatter {

    public String formatCodeDetails(CodeDetails code) {
        List<Integer> reversedRotorIds = code.rotorIds().reversed();
        List<Character> reversedPositions = code.positions().reversed();
        List<Integer> reversedNotchDistances = code.notchDistances().reversed();

        String rotorIdsStr = reversedRotorIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String positionsStr = IntStream.range(0, reversedPositions.size())
                .mapToObj(i -> reversedPositions.get(i) + "(" + reversedNotchDistances.get(i) + ")")
                .collect(Collectors.joining(","));

        String reflectorStr = RomanNumeral.fromInt(code.reflectorId()).name();

        return "<" + rotorIdsStr + "><" + positionsStr + "><" + reflectorStr + ">";
    }

    public String formatEngineDetails(EngineDetails details) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Number of rotors: ").append(details.rotorCount()).append("\n");
        sb.append("Number of reflectors: ").append(details.reflectorCount()).append("\n");
        sb.append("Total number of messages since file was loaded: ")
                .append(details.totalMessagesProcessed()).append("\n");
        
        details.initialCode().ifPresent(code ->
                sb.append("Initial code: ").append(formatCodeDetails(code)).append("\n")
        );
        
        details.currentCode().ifPresent(code ->
                sb.append("Current code: ").append(formatCodeDetails(code)).append("\n")
        );
        
        return sb.toString();
    }

    public String formatProcessResult(String input, String output) {
        return String.format("Output: %s", output);
    }

    public String formatMessage(MessageRecord message, int messageNumber) {
        return String.format("%d. <%s> --> <%s> (%d nano-seconds)",
                messageNumber,
                message.input(),
                message.output(),
                message.durationNanos()
        );
    }

    public String formatSession(SessionRecord session, int sessionNumber) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Code Configuration #").append(sessionNumber).append(":\n");
        sb.append(formatCodeDetails(session.code())).append("\n\n");
        
        List<MessageRecord> messages = session.messages();
        if (messages.isEmpty()) {
            sb.append("No messages processed with this configuration.\n\n");
        } else {
            for (int msgNum = 0; msgNum < messages.size(); msgNum++) {
                sb.append(formatMessage(messages.get(msgNum), msgNum + 1)).append("\n");
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }

    public String formatStatistics(List<SessionRecord> statistics) {
        if (statistics.isEmpty()) {
            return "\nNo statistics available yet.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Machine History and Statistics ===\n\n");

        for (int sessionNum = 0; sessionNum < statistics.size(); sessionNum++) {
            sb.append(formatSession(statistics.get(sessionNum), sessionNum + 1));
        }

        return sb.toString();
    }

    public String formatMenu(String title, List<MenuItem> items) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append(title).append("\n");
        sb.append("=".repeat(title.length())).append("\n\n");
        
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("%d. %s\n", i + 1, items.get(i).label()));
        }

        return sb.toString();
    }

    public String formatError(String message) {
        return "Error: " + message;
    }

    public String formatSuccess(String message) {
        return "Success: " + message;
    }
}

