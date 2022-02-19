import java.util.List;

/**
 * A class representing plants in the simulation
 * that inherits class Species
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.19
 */
public class Plant extends Species
{
    // keep track of the plant's current health
    private int health;

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
     * @param health the plant's current health
     */
    public Plant(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability, int health)
    {
        super(field, location, name, maximumTemperature, minimumTemperature, nutritionalValue, reproductionProbability);
        this.health = health;
    }

    public void act(List<Species> newSpecies)
    {}

    protected void reproduce(List<Species> newOfThisKind)
    {}

    public void isEaten()
    {}
}
