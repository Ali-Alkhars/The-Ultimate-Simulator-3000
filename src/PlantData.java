/**
 * Setting and allowing other classes to retrieve data related to plants.
 * Its implementation greatly differs from the processes to read data for animals or habitats because plants are, for now,
 * of one type only and therefore does not require a .txt file.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public class PlantData {
    // The plant's name.
    private static final String name = "plant";
    // The maximum temperature plant can survive to.
    private static final int maximumTemperature = 40;
    // The minimum temperature plant can survive to.
    private static final int minimumTemperature = -5;
    // The nutritional value brought when plant is eaten.
    private static final int nutritionalValue = 9;
    // Probability to see plant reproduce.
    private static final double reproductionProbability = 0.01;
    // The plant's initial health.
    private static final int initialHealth = 5;

    /**
     * @return (int) The plant's nutritional value.
     */
    public static int getNutritionalValue() {
        return nutritionalValue;
    }

    /**
     * @return (int) The minimum temperature a plant can survive to.
     */
    public static int getMinimumTemperature() {
        return minimumTemperature;
    }

    /**
     * @return (int) The maximum temperature a plant can survive to.
     */
    public static int getMaximumTemperature() {
        return maximumTemperature;
    }

    /**
     * @return (String) The plant's name.
     */
    public static String getName() {
        return name;
    }

    /**
     * @return (double) Probability that the plant reproduces.
     */
    public static double getReproductionProbability() {
        return reproductionProbability;
    }

    /**
     * @return (int) The plant's initial health.
     */
    public static int getInitialHealth() {
        return initialHealth;
    }
}