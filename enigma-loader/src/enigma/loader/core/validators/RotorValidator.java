package enigma.loader.core.validators;

import enigma.core.Constants;
import enigma.loader.core.exceptions.rotor.DuplicateMappingException;
import enigma.loader.core.exceptions.rotor.InvalidMappingCharacterException;
import enigma.loader.core.exceptions.rotor.InvalidMappingCountException;
import enigma.loader.core.exceptions.rotor.InvalidNotchException;
import enigma.loader.core.exceptions.rotor.MinimumRotorsException;
import enigma.loader.core.exceptions.rotor.NonContinuousIDException;
import enigma.loader.core.utils.MappingSide;
import enigma.loader.xml.generated.BTEPositioning;
import enigma.loader.xml.generated.BTERotor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RotorValidator implements Validator {
    private final List<BTERotor> rotors;
    private final Set<Character> alphabetCharacters;
    private final int alphabetLength;

    public RotorValidator(List<BTERotor> rotors, Set<Character> alphabetCharacters) {
        this.rotors = rotors;
        this.alphabetCharacters = alphabetCharacters;
        this.alphabetLength = alphabetCharacters.size();
    }

    @Override
    public void validate() {
        ensureMinimumRotors();
        ensureContinuousIds();
        ensureValidNotchPositions();
        ensureValidMappingCount();
        ensureNoDuplicateMappings();
        ensureValidMappingCharacters();
    }

    private void ensureMinimumRotors() {
        if (rotors.size() < Constants.MINIMUM_ROTORS) {
            throw new MinimumRotorsException(Constants.MINIMUM_ROTORS, rotors.size());
        }
    }

    private void ensureContinuousIds() {
        Set<Integer> outOfRangeIDs = findNonContinuousIDs();

        if (!outOfRangeIDs.isEmpty()) {
            throw new NonContinuousIDException(outOfRangeIDs, rotors.size());
        }
    }

    private Set<Integer> findNonContinuousIDs() {
        Set<Integer> outOfRangeIDs = new HashSet<>();

        for (BTERotor rotor : rotors) {
            int id = rotor.getId();

            if (id < 1 || id > rotors.size()) {
                outOfRangeIDs.add(id);
            }
        }

        return outOfRangeIDs;
    }

    private void ensureValidNotchPositions() {
        for (BTERotor rotor : rotors) {
            int notch = rotor.getNotch();
            if (notch < 1 || notch > alphabetLength) {
                throw new InvalidNotchException(rotor.getId(), notch, alphabetLength);
            }
        }
    }

    private void ensureValidMappingCount() {
        for (BTERotor rotor : rotors) {
            int mappingCount = rotor.getBTEPositioning().size();

            if (mappingCount != alphabetLength) {
                throw new InvalidMappingCountException(rotor.getId(), alphabetLength, mappingCount);
            }
        }
    }

    private void ensureValidMappingCharacters() {
        for (BTERotor rotor : rotors) {
            for (BTEPositioning mapping : rotor.getBTEPositioning()) {
                char right = mapping.getRight().charAt(0);
                char left = mapping.getLeft().charAt(0);
                ensureCharacterInAlphabet(rotor.getId(), MappingSide.RIGHT, right);
                ensureCharacterInAlphabet(rotor.getId(), MappingSide.LEFT, left);
            }
        }
    }

    private void ensureCharacterInAlphabet(int rotorId, MappingSide side, char value) {
        if (!alphabetCharacters.contains(value)) {
            throw new InvalidMappingCharacterException(rotorId, side, value);
        }
    }

    private void ensureNoDuplicateMappings() {
        for (BTERotor rotor : rotors) {
            ensureNoDuplicatesOnSide(rotor, MappingSide.RIGHT);
            ensureNoDuplicatesOnSide(rotor, MappingSide.LEFT);
        }
    }

    private void ensureNoDuplicatesOnSide(BTERotor rotor, MappingSide side) {
        Set<Character> seen = new HashSet<>();

        for (BTEPositioning mapping : rotor.getBTEPositioning()) {
            char value = (side == MappingSide.RIGHT) 
                    ? mapping.getRight().charAt(0) 
                    : mapping.getLeft().charAt(0);

            if (!seen.add(value)) {
                throw new DuplicateMappingException(rotor.getId(), side, value);
            }
        }
    }
}
