package enigma.engine.dtos;

import java.util.Optional;

public record EngineDetails(
        int rotorCount,
        int reflectorCount,
        int totalMessagesProcessed,
        Optional<CodeDetails> initialCode,
        Optional<CodeDetails> currentCode
        ) {}
