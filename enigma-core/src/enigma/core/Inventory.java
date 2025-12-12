package enigma.core;

import enigma.core.alphabet.Alphabet;
import enigma.core.reflector.Reflector;
import enigma.core.rotor.Rotor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public record Inventory(Alphabet alphabet, Map<Integer, Rotor> rotors, Map<Integer, Reflector> reflectors) implements Serializable {

    public Inventory(Alphabet alphabet,
                     Map<Integer, Rotor> rotors,
                     Map<Integer, Reflector> reflectors) {
        this.alphabet = alphabet;
        this.rotors = Map.copyOf(rotors);
        this.reflectors = Map.copyOf(reflectors);
    }

    public Rotor getRotor(int id) {
        Rotor rotor = rotors.get(id);
        if (rotor == null) {
            throw new IllegalArgumentException("Rotor ID " + id + " does not exist");
        }

        return rotor;
    }

    public Reflector getReflector(int id) {
        Reflector reflector = reflectors.get(id);
        if (reflector == null) {
            throw new IllegalArgumentException("Reflector ID " + id + " does not exist");
        }

        return reflector;
    }

    public int getRotorCount() {
        return rotors.size();
    }

    public int getReflectorCount() {
        return reflectors.size();
    }

    public Set<Integer> getRotorIds() {
        return rotors.keySet();
    }

    public Set<Integer> getReflectorIds() {
        return reflectors.keySet();
    }
}
