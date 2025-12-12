package enigma.loader.xml;

import enigma.core.Inventory;
import enigma.core.alphabet.Alphabet;
import enigma.core.alphabet.AlphabetImpl;
import enigma.core.reflector.Reflector;
import enigma.core.reflector.ReflectorImpl;
import enigma.core.rotor.Rotor;
import enigma.core.rotor.RotorImpl;
import enigma.loader.core.Parser;
import enigma.loader.core.exceptions.reflector.DuplicateReflectorIDException;
import enigma.loader.core.exceptions.reflector.InvalidIDException;
import enigma.loader.core.exceptions.rotor.DuplicateRotorIDException;
import enigma.loader.core.utils.RomanNumeral;
import enigma.loader.xml.generated.BTEEnigma;
import enigma.loader.xml.generated.BTEPositioning;
import enigma.loader.xml.generated.BTEReflect;
import enigma.loader.xml.generated.BTEReflector;
import enigma.loader.xml.generated.BTERotor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParser implements Parser {

    @Override
    public Inventory parse(BTEEnigma enigma) {
        return createInventory(enigma);
    }

    private Inventory createInventory(BTEEnigma enigma) {
        String alphabetString = enigma.getABC().trim().toUpperCase();
        Alphabet alphabet = new AlphabetImpl(alphabetString);
        
        Map<Integer, Rotor> rotors = convertRotors(enigma.getBTERotors().getBTERotor(), alphabet);
        Map<Integer, Reflector> reflectors = convertReflectors(enigma.getBTEReflectors().getBTEReflector());

        return new Inventory(alphabet, rotors, reflectors);
    }

    private Map<Integer, Rotor> convertRotors(List<BTERotor> bteRotors, Alphabet alphabet) {
        Map<Integer, Rotor> rotors = new HashMap<>();
        
        for (BTERotor bteRotor : bteRotors) {
            int id = bteRotor.getId();
            
            if (rotors.containsKey(id)) {
                throw new DuplicateRotorIDException(id);
            }
            
            Rotor rotor = convertRotor(bteRotor, alphabet);
            rotors.put(id, rotor);
        }
        
        return rotors;
    }

    private Rotor convertRotor(BTERotor bteRotor, Alphabet alphabet) {
        Map<Integer, Integer> wiring = new HashMap<>();

        StringBuilder leftColumn = new StringBuilder();
        StringBuilder rightColumn = new StringBuilder();

        for (BTEPositioning pos : bteRotor.getBTEPositioning()) {
            leftColumn.append(Character.toUpperCase(pos.getLeft().charAt(0)));
            rightColumn.append(Character.toUpperCase(pos.getRight().charAt(0)));
        }

        String leftStr = leftColumn.toString();
        String rightStr = rightColumn.toString();

        // Build wiring based on alphabet positions in each column
        for (int i = 0; i < alphabet.size(); i++) {
            char letter = alphabet.toChar(i);

            int rightIndex = rightStr.indexOf(letter);
            int leftIndex = leftStr.indexOf(letter);

            // Connect the wire: rightIndex -> leftIndex
            wiring.put(rightIndex, leftIndex);
        }

        int notchIndex = bteRotor.getNotch() - 1;

        return new RotorImpl(wiring, notchIndex, rightStr);
    }

    private Map<Integer, Reflector> convertReflectors(List<BTEReflector> bteReflectors) {
        Map<Integer, Reflector> reflectors = new HashMap<>();
        
        for (BTEReflector bteReflector : bteReflectors) {
            String rawId = bteReflector.getId();
            int id = RomanNumeral.fromString(rawId)
                    .map(RomanNumeral::toInt)
                    .orElseThrow(() -> new InvalidIDException(rawId));
            
            if (reflectors.containsKey(id)) {
                throw new DuplicateReflectorIDException(id);
            }
            
            Reflector reflector = convertReflector(bteReflector);
            reflectors.put(id, reflector);
        }
        
        return reflectors;
    }

    private Reflector convertReflector(BTEReflector bteReflector) {
        Map<Integer, Integer> wiring = new HashMap<>();
        
        for (BTEReflect reflect : bteReflector.getBTEReflect()) {
            int input = reflect.getInput() - 1;
            int output = reflect.getOutput() - 1;
            
            wiring.put(input, output);
            wiring.put(output, input);
        }
        
        return new ReflectorImpl(wiring);
    }
}
