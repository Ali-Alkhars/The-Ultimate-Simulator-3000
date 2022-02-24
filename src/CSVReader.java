import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * A class to govern behavior of the various CSV Readers (one for plant, animals, scenarios, and habitats). This class implements the actual "CSV reading"
 * behaviour.
 * Note: constructor is not declared as no behaviors need to be handled by a constructor and no field has to be initialized.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public abstract class CSVReader {

    /**
     * Method to be called by children, it centralizes the CSV Readers operations by orchestrating the process of reading data from the csv files
     * and populating the appropraite fields with the read data.
     *
     * @param nameOfElementToLookFor (String) the name of the element which data must be extracted
     */
    public void extractDataFor(String nameOfElementToLookFor)
    {
        resetParameters();
        // Read data from appropriate CSV file
        String[] extractedData = getDataFor(nameOfElementToLookFor);
        if (extractedData != null) {
            // Populate appropriate field wit the data read
            populateFields(extractedData);
        } else {
            System.out.println("ERROR: no data could be read for " + nameOfElementToLookFor);
        }
    }

    /**
     * Reads the data relative to a given element in the appropraite CSV file (the path to this file depends on the children
     * class from which this method is called)
     *
     * @param nameOfElementToLookFor (String) the name of the element which data must be extracted
     * @return (String[]) the extracted data
     */
    private String[] getDataFor(String nameOfElementToLookFor)
    {
        Path pathToFile = Paths.get(this.getFileName());
        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            // Skip first line as they are headers.
            String line = br.readLine();
            while ((line=br.readLine()) != null) {
                String[] attributes = line.split(",");
                if (attributes[0].equals(nameOfElementToLookFor)) {
                    return attributes;
                }
            }
        } catch (Exception e) {
            System.out.println("Issue when parsing CSV");
        }
        return null;
    }

    /**
     * Abstract method that must be overriden with the approriate behaviours needed to populate the fields of the child class.
     *
     * @param extractedData (String[]) The data extracted from the CSV file.
     */
    abstract void populateFields(String[] extractedData);

    /**
     * Abstract method used to reset all the fields of a CSV Reader object when the reading is done and another element ca be expected to be read.
     */
    abstract void resetParameters();

    /**
     * Abstract method to return the name of the file containing the data to be read.
     *
     * @return (String) the file name.
     */
    abstract String getFileName();

    /**
     * Returns a list of choices (of animals, habitats, and scenarios depending on the child class it is called from) available to the user.
     *
     * @return (ArrayList<String>) The list of available choices.
     */
    public ArrayList<String> getChoicesList()
    {
        ArrayList<String> choicesList = new ArrayList<>();
        Path pathToFile = Paths.get(this.getFileName());
        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            // Skip first line as they are headers.
            String line = br.readLine();
            while ((line=br.readLine()) != null) {
                String[] attributes = line.split(",");
                choicesList.add(attributes[0]);
            }
        } catch (Exception e) {
            System.out.println("Issue when parsing CSV");
            return null;
        }
        return choicesList;
    }
}
