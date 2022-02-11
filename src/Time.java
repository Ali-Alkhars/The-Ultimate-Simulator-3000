/**
 * Keep track of the time in the simulation
 * by specifying if it is day or night
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.11
 */
public class Time
{
    // true if it is night
    private boolean isNight;
    // keep track of the simulation steps.
    private SimulationStep simStep;
    // the number of steps before the type of day changes
    private static final int CHANGE_STEPS = 2;

    /**
     * Construct a Time object with the given parameters
     * @param simStep the object of SimulationStep to use for keeping track of the steps
     * @param startNight true if first iteration is night, false if day
     */
    public Time(SimulationStep simStep, boolean startNight)
    {
        this.simStep = simStep;
        isNight = startNight;
    }

    /**
     * @return true if night, false otherwise
     */
    public boolean getIsNight()
    {
        return isNight;
    }

    /**
     * Create a String indicating night or day
     * @return "Night" if isNight is true, "Day" otherwise
     */
    public String timeString()
    {
        if(isNight) {
            return "Night";
        }
        return "Day";
    }

    /**
     * change the day status if 'CHANGE_STEPS' steps have passed
     */
    public void checkTime()
    {
        int step = simStep.getCurrentStep();

        if(step != 0 && step % CHANGE_STEPS == 0)
        {
            toggleIsNight();
        }
    }

    /**
     * Change the current status of the time
     */
    private void toggleIsNight()
    {
        isNight = ! isNight;
    }
}
