import java.util.Iterator;
import java.util.List;

/**
 * A class representing plants in the simulation
 * that inherits class Species
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.20
 */
public class Plant extends Species
{
    // the plant's initial health
    private final int initialHealth;
    // keep track of the plant's health
    private int currentHealth;
    // reference to the habitat
    private Habitat habitat;
    // the number of steps that passed since the plant died
    private int stepSinceDeath;

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
     * @param habitat a reference to the plant's habitat
     */
    public Plant(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability, int initialHealth, Habitat habitat)
    {
        super(field, location, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability);
        this.initialHealth = initialHealth;
        currentHealth = initialHealth;
        this.habitat = habitat;
        stepSinceDeath = 0;
    }

    public void act(List<Species> newPlants)
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        int currentTemperature = habitat.getCurrentTemperature();

        if (isAlive() && ! survivesTemperature(currentTemperature))
        {
            setDead();
        }
        else if (! isAlive() && survivesTemperature(currentTemperature) && stepSinceDeath >= habitat.SEASON_CHANGE && habitat.isSpring())
        {
            regrow();
        }
        else if (isAlive())
        {

        }
        else
        {
            stepSinceDeath++;
        }

    }

    protected void reproduce(List<Species> newOfThisKind)
    {}

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
            getField().clear(getLocation());
        }
    }

    /**
     * the dead plant is placed back in the field if
     * its previous location is empty, otherwise do nothing.
     * If it grows, then it grows back to full health.
     */
    private void regrow()
    {
        if(getField().getObjectAt(getLocation()) == null)   {
            stepSinceDeath = 0;
            toggleIsAlive();
            getField().place(this, getLocation());
            currentHealth = initialHealth;
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
}
