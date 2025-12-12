package enigma.engine.statistics;

import enigma.engine.dtos.CodeDetails;
import enigma.engine.dtos.MessageRecord;
import enigma.engine.dtos.SessionRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticsTrackerImpl implements StatisticsTracker {

    private final List<SessionData> pastSessions = new ArrayList<>();
    private SessionData currentSession;

    private static class SessionData {
        final CodeDetails setup;
        final List<MessageRecord> messages = new ArrayList<>();

        SessionData(CodeDetails setup) {
            this.setup = setup;
        }

        SessionRecord toRecord() {
            return new SessionRecord(setup, List.copyOf(messages));
        }
    }

    @Override
    public void addSession(CodeDetails code) {
        if (currentSession != null) {
            pastSessions.add(currentSession);
        }
        currentSession = new SessionData(code);
    }

    @Override
    public void addMessage(MessageRecord message) {
        currentSession.messages.add(message);
    }

    @Override
    public List<SessionRecord> getAllStatistics() {
        List<SessionRecord> result = pastSessions.stream()
                .map(SessionData::toRecord)
                .collect(Collectors.toCollection(ArrayList::new));
        if (currentSession != null) {
            result.add(currentSession.toRecord());
        }

        return List.copyOf(result);
    }

    @Override
    public int getTotalMessageCount() {
        int totalCount = pastSessions.stream()
                .mapToInt(session -> session.messages.size())
                .sum();
        
        if (currentSession != null) {
            totalCount += currentSession.messages.size();
        }
        
        return totalCount;
    }

    @Override
    public void restoreFromHistory(List<SessionRecord> history) {
        pastSessions.clear();
        currentSession = null;

        for (SessionRecord record : history) {
            SessionData session = new SessionData(record.code());
            session.messages.addAll(record.messages());
            pastSessions.add(session);
        }
    }
}
