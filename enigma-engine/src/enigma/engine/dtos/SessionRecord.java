package enigma.engine.dtos;

import java.io.Serializable;
import java.util.List;

public record SessionRecord(CodeDetails code, List<MessageRecord> messages) implements Serializable {
    public SessionRecord {
        messages = List.copyOf(messages);
    }
}
