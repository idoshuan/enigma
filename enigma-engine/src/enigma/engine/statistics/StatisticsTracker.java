package enigma.engine.statistics;

import enigma.engine.dtos.CodeDetails;
import enigma.engine.dtos.MessageRecord;
import enigma.engine.dtos.SessionRecord;

import java.util.List;

public interface StatisticsTracker {
    List<SessionRecord> getAllStatistics();
    void addSession(CodeDetails code);
    void addMessage(MessageRecord message);
    int getTotalMessageCount();
    void restoreFromHistory(List<SessionRecord> history);
}
