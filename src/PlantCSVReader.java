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

    protected String getFileName() {
        return FILE_NAME;
    }

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

    public int getMinimumTemperature() {
        return minimumTemperature;
    }

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
    }
}