/**
 * An abstract class that specifies the actions
 * of the different climate change scenarios
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.17
 */
public abstract class ClimateChange
{
    private double concreteChange;
    private final double changePercentage;

    /**
     * Initialise ClimateChange and its fields
     *
     * @param concreteChange hold the actual temperature change value
     * @param changePercentage the change percentage that is added to the concreteChange each year
     */
    public ClimateChange(int concreteChange, double changePercentage)
    {
        this.concreteChange = concreteChange;
        this.changePercentage = changePercentage;
    }

    /**
     * @return the concreteChange as a rounded int
     */
    public int getClimateChangeEffect()
    {
        return (int) Math.round(concreteChange);
    }

    /**
     * increase the concreteChange by the changePercentage
     */
    public void doClimateChange()
    {
        concreteChange = concreteChange + (changePercentage * concreteChange);
    }

}
