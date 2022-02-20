import java.awt.*;
import java.util.*;

import javafx.application.Application;
import java.util.List;

public class Initializer {

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    private static final boolean RANDOM_ANIMAL_AGE = true;
    private static final ArrayList<String> CLIMATE_CHANGE_SCENARIO_NAMES = new ArrayList<>(Arrays.asList("none", "low", "medium", "high"));

    // List of species in the field.
    private List<Species> speciesToEvolveInSimulation;
    // A graphical view of the simulation.
    private HabitatCSVReader habitatReader;
    private AnimalCSVReader animalReader;

    private int fieldDepth;
    private int fieldWidth;

    private SimulatorView view;

    private double habitatPlantConcentration;

    private Random rand;

    private Simulator createdSimulator;



    /**
     * Construct a simulation field with default size.
     */
    public Initializer()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
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

        openGUI();

        // define colors attribution
    }

    private void openGUI()
    {
        ArrayList<String> animalChoices = animalReader.getChoicesList();
        ArrayList<String> habitatChoices = habitatReader.getChoicesList();
        // build GUI
    }

    public Simulator initializeSimulation(String chosenHabitat, HashMap<String, Integer> animalsToCreate, String scenarioName)
    {
        SimulationStep simulatorStepCounter = new SimulationStep();
        Field field = new Field(fieldDepth, fieldWidth);
        view = new SimulatorView(fieldDepth, fieldWidth);

        ClimateChange chosenClimateChangeScenario = createChosenClimateChangeScneario(scenarioName);
        Habitat simulationHabitat = createHabitat(chosenHabitat, simulatorStepCounter, chosenClimateChangeScenario);
        populateWithAnimals(animalsToCreate, field);
        populateWithPlants();
        createdSimulator = new Simulator(simulationHabitat, speciesToEvolveInSimulation, field, simulatorStepCounter, view);
        return createdSimulator;
    }

    public SimulatorView getSimulatorView()
    {
        return view;
    }

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

    private void populateWithAnimals(HashMap<String, Integer> animalsToCreate, Field field) {
        Location freeLocationToPlaceAnimal;

        for(String animalName : animalsToCreate.keySet()) {
            animalReader.extractDataFor(animalName);
            if (animalReader.isPredator()) {
                int strength = animalReader.getStrength();;
                String name = animalReader.getName();
                int maximumTemperature = animalReader.getMaximumTemperature();
                int minimumTemperature = animalReader.getMinimumTemperature();
                boolean isFemale = animalReader.isFemale();
                int maxAge = animalReader.getMaximumAge();
                int breedingAge = animalReader.getBreedingAge();
                double breedingProbability = animalReader.getBreedingProbability();
                int maxLitterSize = animalReader.getMaxLitterSize();
                int nutritionalValue = animalReader.getNutritionalValue();

                for (int i = 0; i< animalsToCreate.get(animalName); i++) {
                    freeLocationToPlaceAnimal = findAvailableLocation(field);
                    Predator newPredator = new Predator(strength, field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature, isFemale, maxAge, breedingAge, breedingProbability, maxLitterSize, nutritionalValue, RANDOM_ANIMAL_AGE);
                    speciesToEvolveInSimulation.add(newPredator);
                }
            } else {

                String name = animalReader.getName();
                int maximumTemperature = animalReader.getMaximumTemperature();
                int minimumTemperature = animalReader.getMinimumTemperature();
                boolean isFemale = animalReader.isFemale();
                int maxAge = animalReader.getMaximumAge();
                int breedingAge = animalReader.getBreedingAge();
                double breedingProbability = animalReader.getBreedingProbability();
                int maxLitterSize = animalReader.getMaxLitterSize();
                int nutritionalValue = animalReader.getNutritionalValue();

                for (int i = 0; i< animalsToCreate.get(animalName); i++) {
                    freeLocationToPlaceAnimal = findAvailableLocation(field);
                    Animal newAnimal = new Animal(field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature,  isFemale, maxAge,breedingAge, breedingProbability, maxLitterSize, nutritionalValue, RANDOM_ANIMAL_AGE);
                    speciesToEvolveInSimulation.add(newAnimal);
                }
            }
        }
    }

    private ClimateChange createChosenClimateChangeScneario(String scenarioName)
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
    }

    private int calculateFieldArea()
    {
        return fieldWidth*fieldDepth;
    }

    private void populateWithPlants()
    {
        int fieldArea = calculateFieldArea();
        int numberOfPlants = (int)(fieldArea * habitatPlantConcentration);
        for (int i = 0; i< numberOfPlants; i++) {
            //create plant and place it
        }
    }


}
