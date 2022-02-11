//02.11
import java.util.List;

public abstract class Species {

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

    /**
     * Create a new animal at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Species(Field field, Location location, String name, int maximumTemperature, int minimumTemperature)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        this.name = name;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
    }

    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newSpecies A list to receive newly born animals.
     */
    abstract public void act(List<Species> newSpecies);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
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
        if(!(field.getObjectAt(newLocation) instanceof Grass ||  location != null)) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }

    protected boolean survivesTemperature(int temperature)
    {
        if (temperature <= maximumTemperature && temperature >= minimumTemperature) {
            return true;
        }
        return false;
    }

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
}
