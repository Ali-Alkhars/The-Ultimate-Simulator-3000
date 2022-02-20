import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public abstract class CSVReader {
    public void extractDataFor(String habitatName)
    {
        resetParameters();
        String[] extractedData = getDataFor(habitatName);
        if (extractedData != null) {
            populateFields(extractedData);
        } // how do we handle errors
    }

    private String[] getDataFor(String habitatName)
    {
        Path pathToFile = Paths.get(this.getFileName());
        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                if (attributes[0] == habitatName) {
                    return attributes;
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Issue when parsing CSV");
        }
        return null;
    }

    abstract void populateFields(String[] extractedData);
    abstract void resetParameters();
    abstract String getFileName();

    public ArrayList<String> getChoicesList()
    {
        ArrayList<String> choicesList = new ArrayList<>();
        Path pathToFile = Paths.get(this.getFileName());
        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                choicesList.add(attributes[0]);
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Issue when parsing CSV");
        }
        return null;
    }
}
