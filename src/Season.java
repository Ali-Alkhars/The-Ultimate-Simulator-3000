/**
 * A generic class that represents a season
 * of the year
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.16
 */
public class  Season
{
    private final String name;
    private final int aveTemperature;
    private final int tempChange;
    // store the current temperature of this season
    private Thermometer currentTemp;
    // the maximum temperature of this season
    private int upperLimitTemp;
    // the minimum temperature of this season
    private int lowerLimitTemp;

    /**
     * Initialise the Season object
     *
     * @param name the name of the season
     * @param aveTemperature the average temperature of this season
     * @param tempChange the amount that the season's temperature can go up or down from during the season
     */
    public Season(String name, int aveTemperature, int tempChange)
    {
        this.name = name;
        this.aveTemperature = aveTemperature;
        this.tempChange = tempChange;

        currentTemp = new Thermometer(aveTemperature);
        upperLimitTemp = aveTemperature + tempChange;
        lowerLimitTemp = aveTemperature - tempChange;
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
     * @return tempChange
     */
    public int getTempChange()
    {
        return tempChange;
    }

    /**
     * @return upperLimitTemp
     */
    public int getUpperLimitTemp()
    {
        return upperLimitTemp;
    }

    /**
     * @return lowerLimitTemp
     */
    public int getLowerLimitTemp()
    {
        return lowerLimitTemp;
    }

    /**
     * @return a reference to the Thermometer object of this season
     */
    public Thermometer getCurrentTemp()
    {
        return currentTemp;
    }

}
