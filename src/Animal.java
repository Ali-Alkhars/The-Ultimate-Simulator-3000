//02.19
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author Anton Sirgue and Ali Alkhars
 * @version 2022.02.11 (3)
 */

public class Animal extends Species
{
    // Constants that remain true for all animals
    // The grass's food value
    private static final int GRASS_FOOD_VALUE = 9;

    // Static fields, defining a special kind of animal, they can not be changed after initialization

    // The age at which a rabbit can start to breed.
    private final int breedingAge;
    // The age to which a rabbit can live.
    private final int maxAge;
    // The maximum number of births.
    private final int maxLitterSize;
    // A shared random number generator to control breeding.
    private final Random rand = Randomizer.getRandom();
    // The animal's sex
    private final boolean isFemale;

    // Fields prone to change during the animal's live

    // The animal's food level
    protected int foodLevel;
    // The rabbit's age.
    private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */

    public Animal(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability,  boolean isFemale, int maxAge, int breedingAge, int maxLitterSize, boolean randomAge)
    {
        super(field, location, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability);

        this.isFemale = isFemale;
        this.maxAge = maxAge;
        this.breedingAge = breedingAge;
        this.maxLitterSize = maxLitterSize;

        if (randomAge) {
            age = rand.nextInt(maxAge);
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        } else {
            age = 0;
            foodLevel = GRASS_FOOD_VALUE;
        }
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newSpecies A list to return newly born foxes.
     */
    public void act(List<Species> newSpecies)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            ArrayList<Animal> neighboringAnimalsList = getNeighboringAnimalsList(field, it);
            if (canReproduce(neighboringAnimalsList)) {
                reproduce(newSpecies);
            }
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    protected ArrayList<Animal> getNeighboringAnimalsList(Field field, Iterator<Location> it)
    {
        ArrayList<Animal> neighboringAnimals = new ArrayList<>();
        while (it.hasNext()) {
            Location where = it.next();
            Object species = field.getObjectAt(where);
            if (species instanceof Animal) {
                Animal neighboringAnimal = (Animal) species;
                if (neighboringAnimal.isAlive()) {
                    neighboringAnimals.add(neighboringAnimal);
                }
            }
        }
        return neighboringAnimals;
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object species = field.getObjectAt(where); // change in Field
            if(species instanceof Plant) {
                Plant plantSquare = (Plant) species;
                if(plantSquare.isAlive()) {
                    plantSquare.isEaten();
                    foodLevel += GRASS_FOOD_VALUE;
                    return where;
                }
            }
        }
        // No food was found.
        return null;
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     */
    protected boolean canReproduce(ArrayList<Animal> neighboringAnimalsList)
    {
        // task to reproduce is handed to women only so that the same reproduction does not happen twice
        if (this.isFemale) {
            for (Animal neighbor : neighboringAnimalsList) {
                if (!(neighbor.isFemale) && neighbor.getName() == this.getName()) {
                    // The neighbor is a male of the same species
                    setLocation(neighbor.getLocation());
                    return true;
                }
            }
        }
        return false;
    }

    protected void reproduce(List<Species> newOfThisKind)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = new Animal(field, loc, getName(), getMaximumTemperature(), getMinimumTemperature(), getNutritionalValue(), getReproductionProbability(), isFemale, maxAge, breedingAge, maxLitterSize,false);
            newOfThisKind.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getReproductionProbability()) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    protected boolean canBreed()
    {
        return age >= breedingAge;
    }

    protected boolean getIsFemale ()
    {
        return isFemale;
    }

    protected int getMaxAge ()
    {
        return maxAge;
    }
    protected int getBreedingAge ()
    {
        return breedingAge;
    }
    protected int getMaxLitterSize ()
    {
        return maxLitterSize;
    }

}
