package enigma.machine.components.core;

import enigma.core.alphabet.Alphabet;
import enigma.core.rotor.Direction;
import enigma.machine.components.configuration.MachineConfig;
import enigma.machine.components.configuration.MountedRotor;

import java.util.List;

public class MachineImpl implements Machine {

    private final Alphabet alphabet;
    private MachineConfig machineConfig;

    public MachineImpl(Alphabet alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public char process(char input) {
        int signal = alphabet.toIndex(input);

        rotateRotors();

        signal = passSignalForward(signal);
        signal = passSignalThroughReflector(signal);
        signal = passSignalBackward(signal);

        return alphabet.toChar(signal);
    }

    private int passSignalForward(int signal) {
        for (MountedRotor rotor : machineConfig.getRotors()) {
            signal = rotor.process(signal, Direction.FORWARD);
        }
        return signal;
    }

    private int passSignalThroughReflector(int signal) {
        return machineConfig.getReflector().reflect(signal);
    }

    private int passSignalBackward(int signal) {
        for (MountedRotor rotor : machineConfig.getRotors().reversed()) {
            signal = rotor.process(signal, Direction.BACKWARD);
        }
        return signal;
    }

    private void rotateRotors() {
        List<MountedRotor> rotors = machineConfig.getRotors();

        rotors.get(0).rotate();
        for (int i = 0; i < rotors.size() - 1; i++) {
            if (rotors.get(i).isAtNotch()) {
                rotors.get(i + 1).rotate();
            }
            else {
                break;
            }
        }
    }

    @Override
    public void setConfig(MachineConfig machineConfig) {
        this.machineConfig = machineConfig;
    }

    @Override
    public MachineConfig getConfig() {
        return machineConfig;
    }
}
