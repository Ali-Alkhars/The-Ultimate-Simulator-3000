<<<<<<< Updated upstream
import java.util.Arrays;
// to be fully changed
public class PlantCSVReader extends CSVReader {

    double breedingProbability, int initialHealth, Habitat habitat
    private String name;
    private int maximumTemperature;
    private int minimumTemperature;
    private boolean isFemale;
    private int maximumAge;
    private int breedingAge;
    private double breedingProbability;
    private int maxLitterSize;
    private int nutritionalValue;
    private int strength;

    private static final String FILE_NAME = "animals.csv";
    private static final String TRUE_SYMBOL = "true";

    public AnimalCSVReader() {
        isPredator = false;
        name = null;
        maximumTemperature = 0;
        minimumTemperature = 0;
        isFemale = false;
        maximumAge = 0;
        breedingAge = 0;
        breedingProbability = 0;
        maxLitterSize = 0;
        nutritionalValue = 0;
        strength = 0;

    }

    protected void populateFields(String[] extractedData)
    {
        if (!(extractedData.length == 11)) {
            System.out.println("Habitat issue, please restart.");
        }

        if (extractedData[0] == TRUE_SYMBOL) {
            isPredator = true;
        }
        name = extractedData[1];
        maximumTemperature = Integer.valueOf(extractedData[2]);
        minimumTemperature = Integer.valueOf(extractedData[3]);
        if (extractedData[4] == TRUE_SYMBOL) {
            isFemale = true;
        }
        maximumAge = Integer.valueOf(extractedData[5]);
        breedingAge = Integer.valueOf(extractedData[6]);
        breedingProbability = Double.valueOf(extractedData[7]);
        maxLitterSize = Integer.valueOf(extractedData[8]);
        nutritionalValue = Integer.valueOf(extractedData[9]);
        strength = Integer.valueOf(extractedData[10]);
    }

    protected void resetParameters()
    {
        isPredator = false;
        name = null;
        maximumTemperature = 0;
        minimumTemperature = 0;
        isFemale = false;
        maximumAge = 0;
        breedingAge = 0;
        breedingProbability = 0;
        maxLitterSize = 0;
        nutritionalValue = 0;
        strength = 0;

    }

=======
/**
 * Setting and allowing other classes to retrieve data related to plants.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public class PlantCSVReader extends CSVReader
{
    // Name of the file containing plant data.
    private static final String FILE_NAME = "plants.csv";
    // The plant's name.
    private String name;
    // The maximum temperature plant can survive to.
    private int maximumTemperature;
    // The minimum temperature plant can survive to.
    private int minimumTemperature;
    // The nutritional value brought when plant is eaten.
    private int nutritionalValue;
    // Probability to see plant reproduce.
    private double reproductionProbability;
    // The plant's initial health.
    private int initialHealth;

    public PlantCSVReader ()
    {
        name = null;
        maximumTemperature = 0;
        minimumTemperature = 0;
        nutritionalValue = 0;
        reproductionProbability = 0;
        initialHealth = 0;
    }

    /**
     * Populated fields with the data read from the files. This method overrides a method of the CSVReader parent class
     * and is therefore called after reading the data.
     * @param extractedData (String[]) The data read.
     */
    protected void populateFields(String[] extractedData)
    {
        if (extractedData.length != 6) {
            System.out.println("Habitat csv issue, please restart.");
        }
        name = extractedData[0];
        maximumTemperature = Integer.valueOf(extractedData[1]);
        minimumTemperature = Integer.valueOf(extractedData[2]);
        nutritionalValue = Integer.valueOf(extractedData[3]);
        reproductionProbability = Double.valueOf(extractedData[4]);
        initialHealth = Integer.valueOf(extractedData[5]);
    }

    /**
     * Set all parameters back to initial values before reading data for another plant.
     */
    protected void resetParameters()
    {
        name = null;
        maximumTemperature = 0;
        minimumTemperature = 0;
        nutritionalValue = 0;
        reproductionProbability = 0;
        initialHealth =0;
    }

    /**
     * @return (String) The name of the file containing plant data.
     */
>>>>>>> Stashed changes
    protected String getFileName() {
        return FILE_NAME;
    }

<<<<<<< Updated upstream
    public String getName() {
        return name;
    }

    public double getBreedingProbability() {
        return breedingProbability;
    }

    public int getMaximumAge() {
        return maximumAge;
    }

    public int getMaximumTemperature() {
        return maximumTemperature;
    }

    public int getBreedingAge() {
        return breedingAge;
    }

=======
    /**
     * @return (int) The plant's nutritional value.
     */
    public int getNutritionalValue() {
        return nutritionalValue;
    }

    /**
     * @return (int) The minimum temperature a plant can survive to.
     */
>>>>>>> Stashed changes
    public int getMinimumTemperature() {
        return minimumTemperature;
    }

<<<<<<< Updated upstream
    public int getMaxLitterSize() {
        return maxLitterSize;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isFemale() {
        return isFemale;
    }

    public boolean isPredator() {
        return isPredator;
=======
    /**
     * @return (int) The maximum temperature a plant can survive to.
     */
    public int getMaximumTemperature() {
        return maximumTemperature;
    }

    /**
     * @return (String) The plant's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return (double) Probability that the plant reproduces.
     */
    public double getReproductionProbability() {
        return reproductionProbability;
    }

    /**
     * @return (int) The plant's initial health.
     */
    public int getInitialHealth() {
        return initialHealth;
>>>>>>> Stashed changes
    }
}