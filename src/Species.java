import java.util.List;
import java.util.Random;

public abstract class Species
{
    // Whether the species is alive or not.
    private boolean alive;
    // The species' field.
    private Field field;
    // The species' position in the field.
    private Location location;
    // The species' name
    private final String name;
    // The maximum temperature at which the species can survive
    private final int maximumTemperature;
    // The minimum temperature at which the species can survive
    private final int minimumTemperature;
    // The food value species provides when eaten
    private final int nutritionalValue;
    // The likelihood of a species to reproduce.
    private final double reproductionProbability;
    // A shared random number generator to control breeding.
    protected static final Random rand = Randomizer.getRandom();

    /**
     * Create a new specie at location in field.
     *
     * @param field (Field) The field currently occupied.
     * @param location (Location) The location within the field.
     * @param name (String) the name of the specie
     * @param maximumTemperature (int) the maximum temperature that the specie can withstand
     * @param minimumTemperature (int) the minimum temperature that the specie can withstand
     * @param nutritionalValue (int) the nutritional value given to the specie that eats this specie
     * @param reproductionProbability (double) the probability that this specie will reproduce
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
     * Abstract method to be overriden by children with the behaviour they want to have at each step.
     *
     * @param newSpecies (List<Species>) A list to receive newly born species.
     */
    abstract public void act(List<Species> newSpecies, boolean isNight, int temperature);

    abstract void reproduce(List<Species> newOfThisKind);

    /**
     * Check whether the species is alive or not.
     *
     * @return (boolean) true if the species is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * If alive is true change to false, vice versa.
     */
    protected void toggleIsAlive()
    {
        alive = ! alive;
    }


    /**
     * Indicate that the species is no longer alive.
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
     * Return the species' location.
     *
     * @return (Location) The species' location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the species at the new location in the given field.
     *
     * @param newLocation (Location) The species' new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(!(field.getObjectAt(newLocation) instanceof Plant ||  location != null)) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * @return The species' field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * Checks if species can survive to a given temperature.
     *
     * @param temperature (int) the current temperature.
     * @return (boolean) If species survives the given temperature.
     */
    protected boolean survivesTemperature(int temperature)
    {
        return (temperature <= maximumTemperature && temperature >= minimumTemperature);
    }

    /**
     * @return (String) The specie's name
     */
    protected String getName()
    {
        return name;
    }

    /**
     * @return (int) The maximum temperature the species can survive to.
     */
    protected int getMaximumTemperature()
    {
        return maximumTemperature;
    }

    /**
     * @return (int) The minimum temperature the species can survive to.
     */
    protected int getMinimumTemperature()
    {
        return minimumTemperature;
    }

    /**
     * @return (int) The species' nutritional value.
     */
    protected int getNutritionalValue()
    {
        return nutritionalValue;
    }

    /**
     * @return (double) the probability that a species reproduce at each step.
     */
    protected double getReproductionProbability()
    {
        return reproductionProbability;
    }

}