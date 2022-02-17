/**
 * A generic class that represents a season
 * of the year
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.16
 */
public class Season
{
    private final String name;
    private int aveTemperature;
    private final int tempChange;
    // store the current temperature of this season
    private Thermometer currentTemp;

    /**
     * Initialise the Season object
     *
     * @param name the name of the season
     * @param aveTemperature the average temperature of this season
     * @param tempChange the amount that the season's temperature can go up or down from the average temperature
     */
    public Season(String name, int aveTemperature, int tempChange)
    {
        this.name = name;
        this.aveTemperature = aveTemperature;
        this.tempChange = tempChange;

        currentTemp = new Thermometer(aveTemperature);
    }

    /**
     * @return the name of the season
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return aveTemperature
     */
    public int getAveTemperature()
    {
        return aveTemperature;
    }

    /**
     * Increment the season's average temperature by the given parameter
     * @param inc the increment
     */
    public void incAveTemperature(int inc)
    {
        aveTemperature += inc;
    }

    /**
     * @return tempChange
     */
    public int getTempChange()
    {
        return tempChange;
    }

    /**
     * @return the upper limit temperature that the season could reach
     */
    public int getUpperLimitTemp()
    {
        return aveTemperature + tempChange;
    }

    /**
     * @return the lower limit temperature that the season could drop to
     */
    public int getLowerLimitTemp()
    {
        return aveTemperature - tempChange;
    }

    /**
     * @return a reference to the Thermometer object of this season
     */
    public Thermometer getCurrentTemp()
    {
        return currentTemp;
    }

}
