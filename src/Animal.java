import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * A class representing shared characteristics of animals,
 * could be initialised for prey animals.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.21
 */

public class Animal extends Species
{
    // Fields defining a special kind of animal, they can not be changed after initialization

    // The age at which an animal can start to breed.
    private final int breedingAge;
    // The age to which an animal can live.
    private final int maxAge;
    // The maximum number of births at once.
    private final int maxLitterSize;
    // true if the animal's sex if female
    private final boolean isFemale;
    // true if the animal hibernates during cold temperatures
    private final boolean hibernates;
    // true if the animal is active at night
    private final boolean isNocturnal;
    // the number of steps that should pass until an animal in hibernation moves
    private static final int STAY_STEPS = 25;

    // Fields prone to change during the animal's life

    // The animal's food level
    protected int foodLevel;
    // number of steps where the animal is in hibernation
    private int hiberSteps;
    // true if the animal is currently hibernating
    private boolean inHibernation;
    // The animal's age.
    private int age;

    /**
     * Create a new animal. An animal may be created with age
     * zero (a newborn) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability,  boolean isFemale, int maxAge, int breedingAge, int maxLitterSize, boolean randomAge, boolean hibernates, boolean isNocturnal)
    {
        super(field, location, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability);

        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.maxLitterSize = maxLitterSize;
        this.isFemale = isFemale;
        this.hibernates = hibernates;
        this.isNocturnal = isNocturnal;
        inHibernation = false;
        hiberSteps = 0;

        // the initial food level is its nutritional value
        if (randomAge) {
            age = rand.nextInt(maxAge);
            foodLevel = rand.nextInt(nutritionalValue);
        } else {
            age = 0;
            foodLevel = nutritionalValue;
        }
    }

    /**
     * Imitate an animal's step by doing the following:
     * 1) increment the animal's age.
     * 2) if the animal is alive, then:
     *      i) check if the animal should hibernate.
     *      ii) if the animal can't withstand the current temperature, then it dies.
     *      iii) if the animal is in hibernation, then:
     *          a) if it passed STAY_STEPS steps without moving, then move and increment hunger.
     *          b) increment hiberSteps.
     *      iv) if the animal is not in hibernation, then:
     *          a) if it is day, or it is night and the animal is nocturnal, then move.
     *          b) increment hunger.
     *
     * @param newSpecies A list to receive newly born animals.
     * @param isNight true if it is night in the simulation
     * @param temperature the current temperature of the simulation
     */
    public void act(List<Species> newSpecies, boolean isNight, int temperature)
    {
        // 1)
        incrementAge();

        // 2)
        if(isAlive())
        {
            // i)
            checkHibernation(temperature);

            // ii)
            if (! survivesTemperature(temperature))
            {
                setDead();
            }
            // iii)
            else if (inHibernation)
            {
                // a)
                if (hiberSteps % STAY_STEPS == 0)   {
                    makeMove(newSpecies);
                    incrementHunger();
                }
                // b)
                incrementHiberSteps();
            }
            // iv)
            else
            {
                // a)
                if (!isNight || isNocturnal) {
                    makeMove(newSpecies);
                }
                // b)
                incrementHunger();
            }
        }
    }

    /**
     * An animal movement. It tries to reproduce, then looks for a plant to eat,
     * or dies of overcrowding.
     *
     * @param newSpecies A list to receive newly born animals.
     */
    protected void makeMove(List<Species> newSpecies)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
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
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > maxAge) {
            setDead();
        }
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Increment hiberSteps by 1
     */
    protected void incrementHiberSteps()
    {
        hiberSteps++;
    }

    /**
     * Look for plants adjacent to the current location.
     * Only the first plant is eaten.
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
                    foodLevel += plantSquare.getNutritionalValue();
                    return where;
                }
            }
        }
        // No food was found.
        return null;
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     */
    // Ali: in the second if statement, why == instead of .equals()?
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
        int births = numberOfBirths();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = new Animal(field, loc, getName(), getMaximumTemperature(), getMinimumTemperature(), getNutritionalValue(), getReproductionProbability(), randomSex(), maxAge, breedingAge, maxLitterSize,false, hibernates, isNocturnal);
            newOfThisKind.add(young);
        }
    }

    /**
     * this method returns a boolean randomly, which indicates that the
     * sex is male or female.
     *
     * @return true (female) if the number is 1, false (male) if it is 0
     */
    protected boolean randomSex()
    {
        return rand.nextInt(2) == 1;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int numberOfBirths()
    {
        int births = 0;
        if(canGiveBirth() && rand.nextDouble() <= getReproductionProbability()) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * An animal can give birth if it has reached the breeding age.
     */
    protected boolean canGiveBirth()
    {
        return age >= breedingAge;
    }

    /**
     * Check if the animal should be in hibernation.
     *
     * Change inHibernation to true if the animal can hibernate
     * and the current temperature is less than or equal the
     * minimum temperature of the animal + 5
     *
     * @param currentTemperature the current temperature of the simulation
     */
    protected void checkHibernation(int currentTemperature)
    {
        if (hibernates && currentTemperature <= getMinimumTemperature() + 5)  {
            inHibernation = true;
        }
        else {
            inHibernation = false;
            hiberSteps = 0;
        }
    }

    /**
     * @return The maximum age to which an animal can live
     */
    protected int getMaxAge ()
    {
        return maxAge;
    }

    /**
     * @return The age at which an animal can start to breed
     */
    protected int getBreedingAge ()
    {
        return breedingAge;
    }

    /**
     * @return The maximum number of births at once
     */
    protected int getMaxLitterSize ()
    {
        return maxLitterSize;
    }

    /**
     * @return true if the animal hibernates
     */
    protected boolean getHibernates()
    {
        return hibernates;
    }

    /**
     * @return true if the animal is active at night
     */
    protected boolean getIsNocturnal()
    {
        return isNocturnal;
    }

    /**
     * @return the number of steps that have passed since the animal was in hibernation
     */
    protected int getHiberSteps()
    {
        return hiberSteps;
    }

    /**
     * @return true if the animal is currently hibernating
     */
    protected boolean getInHibernation()
    {
        return inHibernation;
    }

}
