//02.25
import java.util.List;
import java.util.Random;

public abstract class Species
{
    // Whether the species is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The species' position in the field.
    private Location location;
    // The species' name
    private final String name;
    // The maximum temperature at which the species can survive
    private final int maximumTemperature;
    // The minimum temperature at which the species can survive
    private final int minimumTemperature;
    // The food value animal provides when eaten
    private final int nutritionalValue;
    // The likelihood of a specie to reproduce.
    private final double reproductionProbability;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new specie at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param name the name of the specie
     * @param maximumTemperature the maximum temperature that the specie can withstand
     * @param minimumTemperature the minimum temperature that the specie can withstand
     * @param nutritionalValue the nutritional value given to the specie that eats this specie
     * @param reproductionProbability the probability that this specie will reproduce
     */
    public Species(Field field, Location location, String name, int maximumTemperature, int minimumTemperature, int nutritionalValue, double reproductionProbability)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.name = name;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
        this.nutritionalValue = nutritionalValue;
        this.reproductionProbability = reproductionProbability;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newSpecies A list to receive newly born animals.
     */
    abstract public void act(List<Species> newSpecies, boolean isNight, int temperature);

    abstract void reproduce(List<Species> newOfThisKind);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * if alive is true change to false, vice versa.
     */
    protected void toggleIsAlive()
    {
        alive = ! alive;
    }


    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(field.getObjectAt(newLocation)  != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    protected boolean survivesTemperature(int temperature)
    {
        return (temperature <= maximumTemperature && temperature >= minimumTemperature);
    }

    /**
     * @return The specie's name
     */
    protected String getName()
    {
        return name;
    }

    protected int getMaximumTemperature()
    {
        return maximumTemperature;
    }
    protected int getMinimumTemperature()
    {
        return minimumTemperature;
    }

    protected int getNutritionalValue()
    {
        return nutritionalValue;
    }

    /**
     * @return reproductionProbability
     */
    protected double getReproductionProbability()
    {
        return reproductionProbability;
    }

}
