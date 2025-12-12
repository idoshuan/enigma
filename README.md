![Front Page](front-page.png)

---

# Enigma Machine

🔗 **GitHub Repository**: https://github.com/idoshuan/enigma

---

### Layer Descriptions

**enigma-core (Foundation Layer)**  
Contains the fundamental domain models that all other modules depend on. Defines the core components of an Enigma machine: `Alphabet`, `Rotor`, `Reflector`, and `Inventory`. These are pure domain objects with no external dependencies, making them stable and reusable.

**enigma-loader (Infrastructure Layer)**  
Responsible for loading machine configuration from external sources. Currently implements XML loading via JAXB, but the `Loader` interface allows easy addition of other formats (JSON, YAML, database). Includes comprehensive validation to ensure loaded configurations are valid before use.

**enigma-machine (Simulation Layer)**  
Contains the actual Enigma machine simulation logic. The `MachineImpl` class handles the core encryption algorithm: rotating rotors, passing signals forward through rotors, reflecting, and passing signals backward. This layer knows nothing about how the machine is configured - it only processes characters.

**enigma-engine (Business Logic Layer)**  
The central orchestrator that ties everything together. Manages the complete lifecycle: loading configurations via the loader, setting up the machine, processing messages, tracking statistics, and handling state persistence. Exposes a clean `Engine` interface that hides all internal complexity.

**enigma-console (Presentation Layer)**  
The user interface layer. Implements a menu-driven console application with state-aware navigation. Uses `ConsoleStateManager` to track the current state and show only relevant menu options. Completely decoupled from the business logic - communicates only through the `Engine` interface.
---

## Main Classes Documentation

### enigma-core

- **`Alphabet`** - Interface for alphabet management - char/index conversion
- **`AlphabetImpl`** - Implementation supporting any alphabet defined in XML
- **`Rotor`** - Interface for rotor behavior - forward/backward signal processing
- **`RotorImpl`** - Rotor implementation with bidirectional mappings and notch support
- **`Reflector`** - Interface for signal reflection
- **`ReflectorImpl`** - Reflector with pair-based character mapping
- **`Inventory`** - Record containing all available machine components (alphabet, rotors, reflectors)

### enigma-loader

- **`Loader`** - Interface for configuration loading
- **`XMLLoader`** - XML file loader using JAXB unmarshalling
- **`XMLParser`** - Converts JAXB objects to domain models
- **`FileValidator`** - Validates file existence and extension
- **`DefinitionValidator`** - Orchestrates all validation checks
- **`AlphabetValidator`** - Ensures alphabet validity (even length, uppercase)
- **`RotorValidator`** - Validates rotor mappings and notch positions
- **`ReflectorValidator`** - Validates reflector pair mappings

### enigma-machine

- **`Machine`** - Interface for character processing
- **`MachineImpl`** - Core simulation - routes signal through rotors, reflector, and back
- **`MachineConfig`** - Current machine configuration (mounted rotors + reflector)
- **`MountedRotor`** - Rotor instance with current position and rotation logic

### enigma-engine

- **`Engine`** - Main interface for all machine operations
- **`EngineImpl`** - Orchestrates loading, configuration, processing, and state persistence
- **`CodeValidator`** - Validates user-provided machine codes
- **`RandomCodeGenerator`** - Generates valid random configurations
- **`StatisticsTracker`** - Tracks processing statistics and message history
- **`MachineCode`** - DTO for machine configuration (rotors, positions, reflector)
- **`MachineState`** - Serializable state object for save/load functionality

### enigma-console

- **`Main`** - Application entry point
- **`ConsoleApplication`** - Dependency injection and component wiring
- **`ConsoleImpl`** - Main console loop with menu-driven interaction
- **`ConsoleStateManager`** - Manages UI state transitions (UNINITIALIZED → INITIALIZED → CONFIGURED)
- **`Menu` / `MenuItem`** - Dynamic menu system - shows options based on current state
- **`ConsoleDisplay`** - Output formatting and display
- **`ConsoleInputHandler`** - User input handling
- **`ConfigurationInputParser`** - Parses rotor IDs, positions, and reflector input

---

## Design Choices

### 1. Modular Architecture
Divided into 5 independent modules for separation of concerns, testability, and extensibility. Each module has a single responsibility and can be developed/tested in isolation.

### 2. Interface-Based Design
All major components are defined by interfaces (`Engine`, `Machine`, `Loader`, `Rotor`, etc.). This allows easy mocking for testing, flexibility to swap implementations, and clear contracts between modules.

### 3. State Machine for Console
The console uses a state machine pattern (`EngineState`: UNINITIALIZED → INITIALIZED → CONFIGURED) to control menu availability and prevent invalid operations (e.g., processing before configuration).

### 4. Immutable DTOs with Java Records
Data transfer objects (`MachineCode`, `CodeDetails`, `Inventory`, `MachineState`) are implemented as Java Records, ensuring immutability, thread safety, and clean API.

### 5. Comprehensive Validation with Specific Exceptions
The loader module includes extensive validation with a dedicated exception class for each error condition (e.g., `DuplicateRotorMappingException`, `InvalidNotchException`). This provides clear error messages and easy debugging.

### 6. JAXB for XML Parsing
Used JAXB (Jakarta XML Binding) for XML configuration because it's a standard Java technology with automatic XML-to-object mapping.

### 7. Dependency Injection in ConsoleApplication
All dependencies are created and wired in `ConsoleApplication`, making the system modular and testable. Components receive their dependencies through constructors.

---

## Bonus Feature: Save & Load State

Implemented full state persistence using Java Object Serialization.

**What is saved:**
- Complete machine inventory (alphabet, rotors, reflectors)
- Initial configuration code
- Current rotor positions  
- Full statistics and message history

**Implementation:** `MachineState` record is serialized to a `.enigma` file via `ObjectOutputStream`. On load, the state is deserialized and the machine is fully restored including all history.

**Location:** `EngineImpl.saveState()` and `EngineImpl.loadState()`
