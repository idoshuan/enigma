package enigma.machine.components.configuration;

import enigma.core.reflector.Reflector;
import enigma.core.rotor.Rotor;

import java.util.ArrayList;
import java.util.List;

public class MachineConfigImpl implements MachineConfig {

    private final List<MountedRotor> rotors;
    private final Reflector reflector;

    public MachineConfigImpl(List<Rotor> selectedRotors,
                             List<Integer> positions,
                             Reflector reflector
    ) {
        this.rotors = new ArrayList<>();
        this.reflector = reflector;

        for (int i = 0; i < selectedRotors.size(); i++) {
            rotors.add(new MountedRotor(selectedRotors.get(i), positions.get(i)));
        }
    }

    @Override
    public List<MountedRotor> getRotors() {
        return rotors;
    }

    @Override
    public Reflector getReflector() {
        return reflector;
    }

    @Override
    public List<Integer> getCurrentPositions() {
        return rotors.stream()
                .map(MountedRotor::getPosition)
                .toList();
    }

    @Override
    public List<Character> getCurrentPositionsAsChars() {
        return rotors.stream()
                .map(MountedRotor::getPositionAsChar)
                .toList();
    }

    @Override
    public List<Integer> getNotchDistances() {
        return rotors.stream()
                .map(MountedRotor::positionFromNotch)
                .toList();
    }
}
