import java.util.List;

/**
 * A class representing plants in the simulation
 * that inherits class Species
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.21
 */
public class Plant extends Species
{
    // the plant's initial health
    private final int initialHealth;
    // keep track of the plant's health
    private int currentHealth;
    // true if the current season is Spring
    private boolean isSpring;
    // true if the plant can regrow,
    // needs at least one season till it is true again
    private boolean canRegrow;
    // the probability that the plant's health grows
    private static final double GROWING_PROBABILITY = 0.1;

    /**
     * Create an instance of Plant
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param name the name of the plant
     * @param maximumTemperature the maximum temperature that the plant can withstand
     * @param minimumTemperature the minimum temperature that the plant can withstand
     * @param nutritionalValue the nutritional value given to the specie that eats this plant
     * @param reproductionProbability the probability that this plant will reproduce
     * @param initialHealth the plant's initial health
     */
    public Plant(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability, int initialHealth)
    {
        super(field, location, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability);
        this.initialHealth = initialHealth;
        currentHealth = initialHealth;
        isSpring = true; // true or not??
        canRegrow = true;
    }

    /**
     * Imitate a plant's step by doing the following:
     * 1) if the plant can't survive the temperature, then it dies
     * 2) else if the time is day:
     *      i) if the plant is dead, and it's spring, and the temperature is suitable,
     *         then grow back
     *      ii) else if the plant is alive, then reproduce and grow
     *
     * @param newPlants A list to return the new plant
     * @param isNight true if it is night in the simulation
     * @param temperature the current temperature of the simulation
     */
    public void act(List<Species> newPlants, boolean isNight, int temperature)
    {
        // 1)
        if (isAlive() && ! survivesTemperature(temperature))
        {
            setDead();
        }
        // 2)
        else if (! isNight)
        {
            // i)
            if (! isAlive() && survivesTemperature(temperature) && isSpring) {
                regrow();
            }
            // ii)
            else if (isAlive()) {
                reproduce(newPlants);
                grow();
            }
        }

    }

    /**
     * Add a new plant in a free neighbouring location if the two
     * following conditions are met:
     * 1- The production probability meets the random number
     * 2- There is a free adjacent location.
     *
     * @param newPlants A list to return the new plant
     */
    protected void reproduce(List<Species> newPlants)
    {
        if (rand.nextDouble() <= getReproductionProbability())
        {
            Field field = getField();
            List<Location> free = field.getFreeAdjacentLocations(getLocation());

            if (free.size() > 0) {
                Location loc = free.remove(0);
                Plant newPlant = new Plant(field, loc, getName(), getMaximumTemperature(), getMinimumTemperature(), getNutritionalValue(), getReproductionProbability(), initialHealth);
                newPlants.add(newPlant);
            }
        }
    }

    /**
     * the plant dies because of the temperature or
     * because it was eaten.
     *
     * its location in the field is cleared, but it
     * still remembers its field and location
     */
    protected void setDead()
    {
        if(getLocation() != null) {
            toggleIsAlive();
            canRegrow = false; // set to false because if left as true, it could regrow the next step
            getField().clear(getLocation());
        }
    }

    /**
     * the dead plant is placed back in the field if
     * its previous location is empty and can regrow, otherwise do nothing.
     * If it grows, then it grows back to full health.
     */
    private void regrow()
    {
        if(getField().getObjectAt(getLocation()) == null && canRegrow)   {
            toggleIsAlive();
            getField().place(this, getLocation());
            currentHealth = initialHealth;
        }
    }

    /**
     * Increase the plant's health by one if the random
     * number meets the growing probability
     */
    private void grow()
    {
        if (currentHealth < initialHealth && rand.nextDouble() <= GROWING_PROBABILITY) {
            currentHealth++;
        }
    }

    /**
     * The effect of getting eaten by an animal.
     *
     * Decrement currentHealth by one, and set dead if
     * the current health is less than 1.
     */
    public void isEaten()
    {
        currentHealth--;

        if (currentHealth <= 0)    {
            setDead();
        }
    }

    /**
     * if isSpring is true, then change it to false, vice versa.
     *
     * Also, set canRegrow as true because at least a season has passed
     * since the plant died.
     */
    public void toggleIsSpring()
    {
        isSpring = ! isSpring;
        canRegrow = true;
    }
}
