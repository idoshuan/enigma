package enigma.loader.core.validators;

import enigma.loader.xml.generated.BTEEnigma;
import enigma.loader.xml.generated.BTEReflector;
import enigma.loader.xml.generated.BTERotor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefinitionValidator implements Validator {
    private final BTEEnigma enigma;

    public DefinitionValidator(BTEEnigma enigma) {
        this.enigma = enigma;
    }

    @Override
    public void validate() {
        String alphabet = getAlphabet();
        new AlphabetValidator(alphabet).validate();

        Set<Character> alphabetCharacters = toCharacterSet(alphabet);
        List<BTERotor> rotors = enigma.getBTERotors().getBTERotor();
        List<BTEReflector> reflectors = enigma.getBTEReflectors().getBTEReflector();
        
        new RotorValidator(rotors, alphabetCharacters).validate();
        new ReflectorValidator(reflectors, alphabet.length()).validate();
    }

    private String getAlphabet() {
        String alphabet = enigma.getABC();
        return (alphabet != null) ? alphabet.trim().toUpperCase() : null;
    }

    private Set<Character> toCharacterSet(String str) {
        if (str == null) {
            return Set.of();
        }
        return str.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }
}
