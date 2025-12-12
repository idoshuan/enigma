package enigma.engine.core;

import enigma.core.Inventory;
import enigma.core.alphabet.Alphabet;
import enigma.core.reflector.Reflector;
import enigma.core.rotor.Rotor;
import enigma.engine.dtos.CodeDetails;
import enigma.engine.dtos.CodeDetailsFactory;
import enigma.engine.dtos.EngineDetails;
import enigma.engine.dtos.MachineCode;
import enigma.engine.dtos.MachineState;
import enigma.engine.dtos.MessageRecord;
import enigma.engine.dtos.SessionRecord;
import enigma.engine.exceptions.EngineException;
import enigma.engine.exceptions.InvalidInputCharacterException;
import enigma.engine.exceptions.file.NoFileConfiguredException;
import enigma.engine.exceptions.machine.MachineNotConfiguredException;
import enigma.engine.code.random.RandomCodeGenerator;
import enigma.engine.code.random.RandomCodeGeneratorImpl;
import enigma.engine.statistics.StatisticsTracker;
import enigma.engine.code.validators.CodeValidator;
import enigma.engine.statistics.StatisticsTrackerImpl;
import enigma.loader.core.Loader;
import enigma.machine.components.configuration.MachineConfigImpl;
import enigma.machine.components.core.Machine;
import enigma.machine.components.core.MachineImpl;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class EngineImpl implements Engine {

    private final Loader loader;
    private Inventory inventory;
    private Machine machine;
    private Optional<MachineCode> initialCode;
    private CodeValidator codeValidator;
    private CodeDetailsFactory codeDetailsFactory;
    private StatisticsTracker statisticsTracker;

    public EngineImpl(Loader loader) {
        this.loader = loader;
    }

    @Override
    public void loadFromFile(String path) {
        inventory = loader.load(path);
        machine = new MachineImpl(inventory.alphabet());
        codeValidator = new CodeValidator(inventory);
        codeDetailsFactory = new CodeDetailsFactory(inventory);
        statisticsTracker = new StatisticsTrackerImpl();
        initialCode = Optional.empty();
    }

    @Override
    public EngineDetails getEngineDetails() {
        requireLoadedFile();

        return new EngineDetails(
                inventory.getRotorCount(),
                inventory.getReflectorCount(),
                statisticsTracker.getTotalMessageCount(),
                getInitialCodeDetails(),
                getCurrentCodeDetails()
        );
    }

    private Optional<CodeDetails> getInitialCodeDetails() {
        return initialCode.map(codeDetailsFactory::create);
    }

    private Optional<CodeDetails> getCurrentCodeDetails() {
        return initialCode
                .map(code -> code.withPositions(getCurrentPositionsAsChars()))
                .map(codeDetailsFactory::create);
    }

    private List<Character> getCurrentPositionsAsChars() {
        return machine.getConfig().getCurrentPositionsAsChars();
    }

    @Override
    public void configureMachineManually(MachineCode code) {
        requireLoadedFile();

        codeValidator.validate(code);
        configureMachine(code);
    }

    private void configureMachine(MachineCode code) {
        requireLoadedFile();

        List<Rotor> rotors = code.rotorIds().stream()
                .map(inventory::getRotor)
                .toList();

        Reflector reflector = inventory.getReflector(code.reflectorId());

        List<Integer> positionIndices = IntStream.range(0, rotors.size())
                .mapToObj(i -> rotors.get(i).charToPosition(code.positions().get(i)))
                .toList();

        initialCode = Optional.of(code);
        machine.setConfig(new MachineConfigImpl(rotors, positionIndices, reflector));
        statisticsTracker.addSession(codeDetailsFactory.create(code));
    }

    @Override
    public void configureMachineRandomly() {
        requireLoadedFile();

        RandomCodeGenerator randomCodeGenerator = new RandomCodeGeneratorImpl(inventory);
        MachineCode randomSetup = randomCodeGenerator.generate();

        configureMachine(randomSetup);
    }

    @Override
    public String process(String input) {
        requireConfiguredMachine();

        input = input.toUpperCase();

        if (!input.isEmpty()) {
            validateInputCharacters(input);
        }

        long startTime = System.nanoTime();
        StringBuilder result = new StringBuilder();

        for (char c : input.toCharArray()) {
            result.append(machine.process(c));
        }

        String output = result.toString();
        statisticsTracker.addMessage(new MessageRecord(input, output, System.nanoTime() - startTime));

        return output;
    }

    @Override
    public void resetConfiguration() {
        requireConfiguredMachine();
        configureMachine(initialCode.get());
    }

    @Override
    public List<SessionRecord> getStatistics() {
        requireLoadedFile();
        return statisticsTracker.getAllStatistics();
    }

    private void requireLoadedFile() {
        if (inventory == null) {
            throw new NoFileConfiguredException();
        }
    }

    private void requireConfiguredMachine() {
        requireLoadedFile();
        if (initialCode.isEmpty()) {
            throw new MachineNotConfiguredException();
        }
    }

    private void validateInputCharacters(String input) {
        Alphabet alphabet = inventory.alphabet();

        for (char c : input.toCharArray()) {
            if (!alphabet.contains(c)) {
                throw new InvalidInputCharacterException(c, alphabet.getCharacters());
            }
        }
    }

    @Override
    public Set<Integer> getAvailableRotorIds() {
        requireLoadedFile();
        return inventory.getRotorIds();
    }

    @Override
    public Set<Integer> getAvailableReflectorIds() {
        requireLoadedFile();
        return inventory.getReflectorIds();
    }

    @Override
    public void saveState(String path) {
        requireConfiguredMachine();

        MachineState state = new MachineState(
                inventory,
                initialCode.get(),
                getCurrentPositionsAsChars(),
                statisticsTracker.getAllStatistics()
        );

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + ".enigma"))) {
            out.writeObject(state);
        } catch (IOException e) {
            throw new EngineException("Failed to save machine state: " + e.getMessage());
        }
    }

    @Override
    public void loadState(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path + ".enigma"))) {
            MachineState state = (MachineState) in.readObject();

            inventory = state.inventory();
            machine = new MachineImpl(inventory.alphabet());
            codeValidator = new CodeValidator(inventory);
            codeDetailsFactory = new CodeDetailsFactory(inventory);
            statisticsTracker = new StatisticsTrackerImpl();
            statisticsTracker.restoreFromHistory(state.statistics());

            MachineCode code = state.initialCode().withPositions(state.currentPositions());
            initialCode = Optional.of(state.initialCode());
            configureMachineWithPositions(code);
        } catch (IOException | ClassNotFoundException e) {
            throw new EngineException("Failed to load machine state: " + e.getMessage());
        }
    }

    private void configureMachineWithPositions(MachineCode code) {
        List<Rotor> rotors = code.rotorIds().stream()
                .map(inventory::getRotor)
                .toList();

        Reflector reflector = inventory.getReflector(code.reflectorId());

        List<Integer> positionIndices = IntStream.range(0, rotors.size())
                .mapToObj(i -> rotors.get(i).charToPosition(code.positions().get(i)))
                .toList();

        machine.setConfig(new MachineConfigImpl(rotors, positionIndices, reflector));
    }
}

