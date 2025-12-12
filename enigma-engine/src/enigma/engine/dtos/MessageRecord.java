package enigma.engine.dtos;

import java.io.Serializable;

public record MessageRecord(String input, String output, long durationNanos) implements Serializable {}
