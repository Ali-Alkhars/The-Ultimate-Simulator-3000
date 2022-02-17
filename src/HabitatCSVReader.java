import java.util.Arrays;

public class HabitatCSVReader extends CSVReader {
    private int[] winterTemperatures;
    private int[] springTemperatures;
    private int[] summerTemperatures;
    private int[] autumnTemperatures;
    private double plantConcentration;

    private static final String FILE_NAME = "habitats.csv";

    public HabitatCSVReader() {
        winterTemperatures = new int[3];
        autumnTemperatures = new int[3];
        springTemperatures = new int[3];
        summerTemperatures = new int[3];
        plantConcentration = 0;
    }

    protected void populateFields(String[] extractedData)
    {
        extractedData = removeHabitatName(extractedData);
        if (!(extractedData.length == 9)) {
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

    protected void resetParameters()
    {
        winterTemperatures = null;
        springTemperatures = null;
        summerTemperatures = null;
        autumnTemperatures = null;
        plantConcentration = 0;

    }

    private String[] removeHabitatName(String[] attributes)
    {
        String[] attributesWithoutHabitatName = Arrays.copyOfRange(attributes,1,attributes.length);
        return attributesWithoutHabitatName;
    }

    protected String getFileName() {
        return FILE_NAME;
    }

    public int[] getWinterTemperatures()
    {
        return winterTemperatures;
    }

    public int[] getAutumnTemperatures() {
        return autumnTemperatures;
    }

    public int[] getSpringTemperatures() {
        return springTemperatures;
    }

    public int[] getSummerTemperatures() {
        return summerTemperatures;
    }

    public double getPlantConcentration() {
        return plantConcentration;
    }
}