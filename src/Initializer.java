import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Initializer {

    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    private static final boolean RANDOM_ANIMAL_AGE = true;

    private Habitat simulationHabitat;
    // List of species in the field.
    private List<Species> speciesToEvolveInSimulation;

    private Field field;
    // A graphical view of the simulation.
    private SimulatorView view;

    private HabitatCSVReader habitatReader;
    private AnimalCSVReader animalReader;

    private int fieldDepth;
    private int fieldWidth;

    private double habitatPlantConcentration;

    private Random rand;

    private Simulator createdSimulator;

    private SimulationStep simulatorStepCounter;


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
        field = new Field(depth, width);
        fieldDepth = depth;
        fieldWidth = width;

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);

        habitatReader = new HabitatCSVReader();

        // define colors attribution
    }

    public Simulator initializeSimulation(String chosenHabitat, HashMap<String, Integer> animalsToCreate)
    {
        simulationHabitat = createHabitat(chosenHabitat);
        populateWithAnimals(animalsToCreate);
        populateWithPlants();
        simulatorStepCounter = new SimulationStep();
        createdSimulator = new Simulator(speciesToEvolveInSimulation, field, simulatorStepCounter, view);
        return createdSimulator;
    }

    public SimulatorView getSimulatorView()
    {
        return view;
    }

    private Habitat createHabitat (String habitatName)
    {
        if (habitatName != null) {
            habitatReader.extractDataFor(habitatName);
            Habitat chosenHabitat = new Habitat(habitatReader.getSpringTemperatures(), habitatReader.getSummerTemperatures(), habitatReader.getAutumnTemperatures(), habitatReader.getWinterTemperatures());
            habitatPlantConcentration = habitatReader.getPlantConcentration();
            return chosenHabitat;
        } else {
            System.out.println("Habitat name was not specified successfully when attempting to create habitat object.");
            return null;
        }
    }

    private void populateWithAnimals(HashMap<String, Integer> animalsToCreate) {
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
                    freeLocationToPlaceAnimal = findAvailableLocation();
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
                    freeLocationToPlaceAnimal = findAvailableLocation();
                    Animal newAnimal = new Animal(field, freeLocationToPlaceAnimal, name, maximumTemperature, minimumTemperature,  isFemale, maxAge,breedingAge, breedingProbability, maxLitterSize, nutritionalValue, RANDOM_ANIMAL_AGE);
                    speciesToEvolveInSimulation.add(newAnimal);
                }
            }
        }
    }

    private Location findAvailableLocation()
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
