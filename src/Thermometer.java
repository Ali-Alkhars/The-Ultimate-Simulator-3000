/**
 * A simple class to keep track of and
 * manipulate the temperature in the simulation
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.11
 */
public class Thermometer
{
    // the current temperature
    private int currentTemp;

    /**
     * Construct a Thermometer object with the given temperature.
     * @param initialTemp the initial temperature
     */
    public Thermometer(int initialTemp)
    {
        currentTemp = initialTemp;
    }

    /**
     * @return the current temperature
     */
    public int getTemperature()
    {
        return currentTemp;
    }

    /**
     * set the temperature to the given parameter
     * @param temp the new temperature value
     */
    public void setTemperature(int temp)
    {
        currentTemp = temp;
    }

    /**
     * increment the current temperature by the given parameter
     * @param inc the amount of the increment
     */
    public void incTemperature(int inc)
    {
        currentTemp += inc;
    }

    /**
     * decrement the current temperature by the given parameter
     * @param dec the amount of the decrement
     */
    public void decTemperature(int dec)
    {
        currentTemp -= dec;
    }
}
