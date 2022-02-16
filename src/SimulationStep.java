/**
 * A simple class to keep track of and increment
 * the steps in the simulation.
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.16
 */
public class SimulationStep
{
    private int currentStep;

    /**
     * Construct a SimulationStep object starting with the given step.
     * @param initialStep the starting step number
     */
    public SimulationStep(int initialStep)
    {
        currentStep = initialStep;
    }

    /**
     * Construct a SimulationStep object starting with step 0.
     */
    public SimulationStep()
    {
        currentStep = 0;
    }

    /**
     * @return the current step
     */
    public int getCurrentStep()
    {
        return currentStep;
    }

    /**
     * Increment the steps by one
     */
    public void incStep()
    {
        currentStep++;
    }

    /**
     * Increment the steps by the given value
     * @param num the amount to increment
     */
    public void incStep(int num)
    {
        currentStep += num;
    }

    /**
     * Reset the current step to 0
     */
    public void reset()
    {
        currentStep = 0;
    }
}
