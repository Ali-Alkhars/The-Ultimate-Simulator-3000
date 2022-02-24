import java.util.Arrays;

/**
 * Handling the reading and retrieving of data relating to habitats.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public class HabitatCSVReader extends CSVReader {
    // List of minimum, average, and maximum temperatures for the winter.
    private int[] winterTemperatures;
    // List of minimum, average, and maximum temperatures for the spring.
    private int[] springTemperatures;
    // List of minimum, average, and maximum temperatures for the summer.
    private int[] summerTemperatures;
    // List of minimum, average, and maximum temperatures for the autumn.
    private int[] autumnTemperatures;
    // The concentration of plants in a given habitat.
    private double plantConcentration;

    // Name of the CSV files containing data on fields.
    private static final String FILE_NAME = "habitats.csv";

    /**
     * Build a HabitatCSVReader and initialize its fields.
     */
    public HabitatCSVReader() {
        winterTemperatures = new int[3];
        autumnTemperatures = new int[3];
        springTemperatures = new int[3];
        summerTemperatures = new int[3];
        plantConcentration = 0;
    }

    /**
     * Populate the fields with the data exctracted from the CSV file. Fields are used to store data of the right type to be easily fetched
     * by the Initializer when trying to get information about a specific habitat.
     * Habitat name from the data is removed to facilitate reading, the first 12 elements are in groups of 3 relating to each season
     * (min, avg, and max temperatures), the 13th element is the plant concentration in that habitat.
     * @param extractedData (String[]) the data from the CSV file relative to a specific habitat.
     */
    protected void populateFields(String[] extractedData)
    {
        extractedData = removeHabitatName(extractedData);
        if (extractedData.length != 9) {
            System.out.println("Habitat issue, please restart.");
        }
        for(int i =0; i < extractedData.length; i++) {
            if(i/2 == 0) {
                winterTemperatures[i%2] = Integer.valueOf(extractedData[i]);
            } else if (i/2 == 1) {
                springTemperatures[i%2] = Integer.valueOf(extractedData[i]);
            } else if (i/2 == 2) {
                summerTemperatures[i%2] = Integer.valueOf(extractedData[i]);
            } else if (i/2 == 3){
                autumnTemperatures[i%2] = Integer.valueOf(extractedData[i]);
            }
            plantConcentration = Double.valueOf(extractedData[extractedData.length-1]);
        }
    }

    /**
     * Set all parameters back to initial values before reading data for another habitat.
     */
    protected void resetParameters()
    {
        winterTemperatures = null;
        springTemperatures = null;
        summerTemperatures = null;
        autumnTemperatures = null;
        plantConcentration = 0;

    }

    /**
     * Returns the list of extracted data without the habitat's name (as this informaiton is known by the Initializer and will therefore not be asked).
     * The name is the first element of the attributes list.
     * @param attributes (String[]) the data from the CSV file relative to a specific habitat.
     */
    private String[] removeHabitatName(String[] attributes)
    {
        String[] attributesWithoutHabitatName = Arrays.copyOfRange(attributes,1,attributes.length);
        return attributesWithoutHabitatName;
    }

    /**
     * @return (String) The name of the file containing habitat data.
     */
    protected String getFileName() {
        return FILE_NAME;
    }

    /**
     * @return (int[]) Minimum, average, and maximum temperatures for the winter.
     */
    public int[] getWinterTemperatures()
    {
        return winterTemperatures;
    }

    /**
     * @return (int[]) Minimum, average, and maximum temperatures for the autumn.
     */
    public int[] getAutumnTemperatures() {
        return autumnTemperatures;
    }

    /**
     * @return (int[]) Minimum, average, and maximum temperatures for the spring.
     */
    public int[] getSpringTemperatures() {
        return springTemperatures;
    }

    /**
     * @return (int[]) Minimum, average, and maximum temperatures for the summer.
     */
    public int[] getSummerTemperatures() {
        return summerTemperatures;
    }

    /**
     * @return (double) The concentration of plants in the habitat.
     */
    public double getPlantConcentration() {
        return plantConcentration;
    }
}