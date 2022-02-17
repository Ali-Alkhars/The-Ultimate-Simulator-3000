import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes, Michael Kölling, Ali Alkhars (k20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.16
 */
public class Simulator
{

    // List of species in the field.
    private List<Species> species;
    // The current state of the field.
    private Field field;
    // keep track of the simulation steps.
    private SimulationStep simStep;
    // A graphical view of the simulation.
    private SimulatorView view;

    private boolean simulationIsOn;

    private static final int DEFAULT_DELAY = 0;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator(List<Species> speciesInSimulation, Field field, SimulationStep simulationStepCounter, SimulatorView simulatorView)
    {

        this.species = speciesInSimulation;
        this.field = field;
        this.simStep = simulationStepCounter;
        this.view = simulatorView;
        this.simulationIsOn = true;
    }

    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000); // Should we change this ?
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            delay(DEFAULT_DELAY);
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {
        if (simulationIsOn) {
            simStep.incStep();

            // Provide space for newborn animals.
            List<Species> newSpecies = new ArrayList<>();
            // Let all rabbits act.
            for(Iterator<Species> it = species.iterator(); it.hasNext(); ) {
                Species species = it.next();
                species.act(newSpecies);
                if(! species.isAlive()) {
                    it.remove();
                }
            }

            // Add the newly born foxes and rabbits to the main lists.
            species.addAll(newSpecies);

            view.showStatus(simStep.getCurrentStep(), field);
        }
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void endSimulation()
    {
        simStep.reset();
        species.clear();
        simulationIsOn = false;
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
