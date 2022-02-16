import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represent a habitat of the simulation.
 * Keep track of its seasons and temperature
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.16
 */
public class Habitat
{
    // holds the habitat's seasons
    private List<Season> seasons;
    // the number of steps before the season changes
    private static final int SEASON_CHANGE = 182;
    // hold the current season
    private Season currentSeason;
    // keep track of the simulation steps.
    private SimulationStep simStep;
    private Random random;

    /**
     * Initialise the Habitat fields and fill the
     * seasons Hash map
     *
     * @param simStep a SimulationStep object to keep track of the steps
     * @param spring a Season object representing spring
     * @param summer a Season object representing summer
     * @param autumn a Season object representing autumn
     * @param winter a Season object representing winter
     */
    public Habitat(SimulationStep simStep, Season spring, Season summer, Season autumn, Season winter)
    {
        this.simStep = simStep;
        random = new Random();
        currentSeason = spring;   // the simulation always starts with spring

        // Initialise seasons and fill it
        seasons = new ArrayList<>();
        seasons.add(spring);
        seasons.add(summer);
        seasons.add(autumn);
        seasons.add(winter);
    }

    /**
     * @return the current season as a String
     */
    public String getCurrentSeason()
    {
        return currentSeason.getName();
    }

    /**
     * @return the current temperature of the current season as an int
     */
    public int getCurrentTemperature()
    {
        return currentSeason.getCurrentTemp().getTemperature();
    }

    /**
     * change the season if 'SEASON_CHANGE' steps have passed
     * and change the temperature each step
     */
    public void checkSeason()
    {
        int step = simStep.getCurrentStep();

        if(step != 0 && (step+1) % SEASON_CHANGE == 0) // step+1 because step starts with 0
        {
            changeSeason();
        }

        randomizeTemperature();
    }

    /**
     * Change the current season according to the
     * predefined order of seasons
     */
    private void changeSeason()
    {
        int seasonIndx = seasons.indexOf(currentSeason);

        if(seasonIndx == seasons.size() -1) {
            currentSeason = seasons.get(0);
        }
        else {
            currentSeason = seasons.get(seasonIndx + 1);
        }
    }

    /**
     * change the current season's temperature randomly according
     * to the season's tempChange field
     */
    private void randomizeTemperature()
    {
        int randomize = random.nextInt(2);
        int change = random.nextInt(currentSeason.getTempChange() + 1);
        Thermometer currentTemp = currentSeason.getCurrentTemp();

        // make sure that the temperature doesn't go beyond the temperature upper limit
        if (randomize == 0 && (getCurrentTemperature() + change) <= currentSeason.getUpperLimitTemp()) {
            currentTemp.incTemperature(change);
        }
        // make sure that the temperature doesn't go below the temperature lower limit
        else if(randomize == 1 && (getCurrentTemperature() - change) >= currentSeason.getLowerLimitTemp()) {
            currentTemp.decTemperature(change);
        }
    }
}
