import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Initializing the simulation. It first launches the UI, then builds the appropriate objects to run the simulation desired by the user.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public class Initializer {

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The default value of if animals' ages must be randomized when thy are created.
    private static final boolean RANDOM_ANIMAL_AGE = true;
    // The names of available climate change scenarios.
    private static final ArrayList<String> CLIMATE_CHANGE_SCENARIO_NAMES = new ArrayList<>(Arrays.asList("none", "low", "medium", "high"));

    // List of species to eveolve in the field.
    private List<Species> speciesToEvolveInSimulation;
    // To read habitat related data.
    private final HabitatCSVReader habitatReader;
    // To read animal related data.
    private final AnimalCSVReader animalReader;
    // To retrieve data related to plants.
    private final PlantData plantReader;
    // The depth of the field created.
    private final int fieldDepth;
    // The width of the field created.
    private final int fieldWidth;
    // A Random object to handle random behaviours throughout the class.
    private final Random rand;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The plant concentration in the habitat created by the user.
    private double habitatPlantConcentration;
    // The Simulator object that will take care of running the simulation.
    private Simulator createdSimulator;

    /**
     * Default constructor, calls the other with the default width and depth.
     */
    public Initializer()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Builds an Initializer object and initializes its field.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Initializer(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        rand = new Random();
        speciesToEvolveInSimulation = new ArrayList<>();
        fieldDepth = depth;
        fieldWidth = width;
        habitatReader = new HabitatCSVReader();
        animalReader = new AnimalCSVReader();
        plantReader = new PlantData();

        openGUI();

        // define colors attribution
    }

    /**
     * Opens the GUI with the list of available animals and habitats extracted from the files as well as the list of available climate change
     * scenarios.
     * Note: the method to launch the class was found on https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
     * on 02/23/2022 thanks to Dr. Raphael's help.
     */
    private void openGUI()
    {
        ArrayList<String> animalChoices = animalReader.getChoicesList();
        ArrayList<String> habitatChoices = habitatReader.getChoicesList();
        GUIHandler g = new GUIHandler(animalChoices, habitatChoices, CLIMATE_CHANGE_SCENARIO_NAMES); // give fields
        try {
            g.start(GUIHandler.classStage);
        } catch(Exception e) {
            System.out.println("Error while launching GUI.");
        }
    }

    /**
     * Create and return a simulator with the animals, habitat, and climate change scenario asked by the user as well as all
     * other necessary objects for the simulation to run.
     * @param chosenHabitat (String) The name of the habitat chosen by the user.
     * @param animalsToCreate (HashMap<String, Integer>) Keys are the animals' names and values are the number of each animal we need to create.
     * @param scenarioName (String) The name of the climate change scenario to implement in the simulation.
     */
    public Simulator initializeSimulation(String chosenHabitat, HashMap<String, Integer> animalsToCreate, String scenarioName)
    {
        SimulationStep simulatorStepCounter = new SimulationStep();
        Field field = new Field(fieldDepth, fieldWidth);
        view = new SimulatorView(fieldDepth, fieldWidth);

        ClimateChange chosenClimateChangeScenario = createChosenClimateChangeScenario(scenarioName);
        Habitat simulationHabitat = createHabitat(chosenHabitat, simulatorStepCounter, chosenClimateChangeScenario);
        if (getNumberOfPlants() + getNumberOfAnimals(animalsToCreate) > calculateFieldArea()) {
            // throw alert, too many animals
            return null;
        }
        populateWithAnimals(animalsToCreate, field);
        populateWithPlants(field);
        createdSimulator = new Simulator(simulationHabitat, speciesToEvolveInSimulation, field, simulatorStepCounter, view);
        return createdSimulator; // why return ?
    }

    /**
     * Return the created SimulatorView.
     * @return (SimulatorView) The created SimulatorView object.
     */
    public SimulatorView getSimulatorView()
    {
        return view;
    }

    /**
     * Read data for the chosen habitat and create an habitat object appropriately.
     * @param habitatName (String) The name of the chosen habitat.
     * @param simulatorStepCounter (SimulationStep) The created SimulationStep object for this simulation to be handed to the Habitat object.
     * @param climateChangeScenario (ClimateChange) The created ClimateChange object to be handed to the Habitat object.
     * @return (Habitat) the created Habitat object.
     */
    private Habitat createHabitat (String habitatName, SimulationStep simulatorStepCounter, ClimateChange climateChangeScenario)
    {
        if (habitatName != null) {
            habitatReader.extractDataFor(habitatName);
            Habitat chosenHabitat = new Habitat(simulatorStepCounter, climateChangeScenario ,habitatReader.getSpringTemperatures(), habitatReader.getSummerTemperatures(), habitatReader.getAutumnTemperatures(), habitatReader.getWinterTemperatures());
            habitatPlantConcentration = habitatReader.getPlantConcentration();
            return chosenHabitat;
        } else {
            System.out.println("Habitat name was not specified successfully when attempting to create habitat object.");
            return null;
        }
    }

    /**
     * Populate the simulation with the chosen animals. First reading the data relating to each animal and then creating and adding to the
     * list of species the right number of each animal.
     * @param animalsToCreate (HashMap<String, Integer>) The names and number of chosen animals.
     * @param field (Field) The field in which the animals will evolve.
     */
    private void populateWithAnimals(HashMap<String, Integer> animalsToCreate, Field field) {
        Location freeLocationToPlaceAnimal;

        for(String animalName : animalsToCreate.keySet()) {
            animalReader.extractDataFor(animalName);
            if (animalsToCreate.get(animalName) != 0) {
                if (animalReader.isPredator()) {
                    // Predator object should be created.
                    int strength = animalReader.getStrength();
                    String name = animalReader.getName();
                    int maximumTemperature = animalReader.getMaximumTemperature();
                    int minimumTemperature = animalReader.getMinimumTemperature();
                    int maxAge = animalReader.getMaximumAge();
                    int breedingAge = animalReader.getBreedingAge();
                    double breedingProbability = animalReader.getBreedingProbability();
                    int maxLitterSize = animalReader.getMaxLitterSize();
                    boolean isFemale = randomSex();
                    int nutritionalValue = animalReader.getNutritionalValue();
                    boolean hibernates = animalReader.canHibernate();
                    boolean isNocturnal = animalReader.isNocturnal();

                    for (int i = 0; i < animalsToCreate.get(animalName); i++) {
                        freeLocationToPlaceAnimal = findAvailableLocation(field);
                        Predator newPredator = new Predator(strength, field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature, nutritionalValue, breedingProbability, isFemale, maxAge, breedingAge, maxLitterSize, RANDOM_ANIMAL_AGE, hibernates, isNocturnal);
                        speciesToEvolveInSimulation.add(newPredator);
                    }
                } else {
                    // Animal object should be created.
                    String name = animalReader.getName();
                    int maximumTemperature = animalReader.getMaximumTemperature();
                    int minimumTemperature = animalReader.getMinimumTemperature();
                    boolean isFemale = randomSex();
                    int maxAge = animalReader.getMaximumAge();
                    int breedingAge = animalReader.getBreedingAge();
                    double breedingProbability = animalReader.getBreedingProbability();
                    int maxLitterSize = animalReader.getMaxLitterSize();
                    int nutritionalValue = animalReader.getNutritionalValue();
                    boolean hibernates = animalReader.canHibernate();
                    boolean isNocturnal = animalReader.isNocturnal();
                    for (int i = 0; i < animalsToCreate.get(animalName); i++) {
                        freeLocationToPlaceAnimal = findAvailableLocation(field);
                        Animal newAnimal = new Animal(field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature, nutritionalValue, breedingProbability,  isFemale, maxAge, breedingAge, maxLitterSize, RANDOM_ANIMAL_AGE, hibernates, isNocturnal);
                        speciesToEvolveInSimulation.add(newAnimal);
                    }
                }
            }
        }
    }

    /**
     * Create the chosen climate change scneario. Scenarios are pre-defined in their respective classes as their functions can be changed
     * to better approximate the real scnearios projected by the GIEC.
     * @param scenarioName (String) The name of the scenario chosen by the user.
     * @return (ClimateChange) The created ClimateChange scenario object.
     */
    private ClimateChange createChosenClimateChangeScenario(String scenarioName)
    {
        ClimateChange chosenScenario;
        // inversé comme ca si ya un pb le default c de ne pas en avoir
        if (scenarioName == CLIMATE_CHANGE_SCENARIO_NAMES.get(3)) {
            chosenScenario = new ChangeScenario4();
        } else if (scenarioName == CLIMATE_CHANGE_SCENARIO_NAMES.get(2)) {
            chosenScenario = new ChangeScenario3();
        } else if (scenarioName == CLIMATE_CHANGE_SCENARIO_NAMES.get(1)) {
            chosenScenario = new ChangeScenario2();
        } else{
            chosenScenario = new ChangeScenario1();
        }
        return chosenScenario;
    }

    /**
     * Find an available location for an object to be created in the simulation field. The simulation is chosen aat random and is changed if
     * the randomly selected cell already contains an object.
     * @param field (Field) The simulation's field.
     * @return (Location) the available location found.
     */
    private Location findAvailableLocation(Field field)
    {
        int randomWidth = rand.nextInt(fieldWidth);
        int randomDepth = rand.nextInt(fieldDepth);
        while (field.getObjectAt(randomDepth,randomWidth) != null) {
            randomWidth = rand.nextInt(fieldWidth);
            randomDepth = rand.nextInt(fieldDepth);
        }
        Location freeLocation = new Location(randomDepth, randomWidth);
        return freeLocation;

        // if time out, too many animals in the wrong habitat alert.
    }

    /**
     * Calculate the area of the field to know the number of plants that should be created.
     * @return (int) The field's area.
     */
    private int calculateFieldArea()
    {
        return fieldWidth*fieldDepth;
    }

    /**
     * Populate the simulation with the appropriate number of plants.
     * @param field (Field) The simulation's field.
     */
    private void populateWithPlants(Field field)
    {
        Location freeLocationToPlaceAnimal;
        String name = plantReader.getName();
        int maximumTemperature = plantReader.getMaximumTemperature();
        int minimumTemperature = plantReader.getMinimumTemperature();
        int nutritionalValue = plantReader.getNutritionalValue();
        double reproductionProbability = plantReader.getReproductionProbability();
        int initialHealth = plantReader.getInitialHealth();
        for (int i = 0; i< getNumberOfPlants(); i++) {
            freeLocationToPlaceAnimal = findAvailableLocation(field);
            Plant createdPlant = new Plant(field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability, initialHealth);
            speciesToEvolveInSimulation.add(createdPlant);
        }
    }

    /**
     * Calculate the total number of plants to be created.
     * @return (int) the number of plants.
     */
    private int getNumberOfPlants()
    {
        int fieldArea = calculateFieldArea();
        int numberOfPlants = (int)(fieldArea * habitatPlantConcentration);
        return numberOfPlants;
    }

    /**
     * Calculate the total number of animals to be created.
     * @return (int) the number of animals.
     */
    private int getNumberOfAnimals(HashMap<String, Integer> animalsToCreate)
    {
        int totalNumber = 0;
        for (String animalName : animalsToCreate.keySet()) {
            totalNumber += animalsToCreate.get(totalNumber);
        }
        return totalNumber;
    }

    /**
     * Returns a random boolean to randomize the sex of newborns.
     * @return (boolean) true (female) if the number is 1, false (male) if it is 0
     */
    private boolean randomSex()
    {
        return rand.nextInt(2) == 1;
    }
}
