package enigma.engine.code.random;

import enigma.engine.dtos.MachineCode;

@FunctionalInterface
public interface RandomCodeGenerator {
    MachineCode generate();
}
