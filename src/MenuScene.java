import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Builds and handles the scene of the GUI in which user can choose the habitat, animals, and climate change scenario to be implemented in
 * his/her simulation.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */

public class MenuScene{

    // The GUIHandler to communicate with.
    private GUIHandler handler;
    // The list of habitats to choose from.
    private ArrayList<String> habitatList;
    // The list of animals to choose from.
    private ArrayList<String> animalList;
    // The list of climate change scenarios to choose from.
    private ArrayList<String> climateChangeScenarioList;
    // The list of created TextFields for user to input the number of each animal.
    private ArrayList<TextField> animalNumberReceivers;
    // The created HashMap linking each animal name with the chosen number of these animals.
    private HashMap<String, Integer> selectedAnimals;

    /**
     * Builds a MenuScene object to communicate with the application's GUIHandler. The constructor initializes all the fields including
     * the lists of choices.
     * @param handler (GUIHandler) The GUIHandler governing the GUI.
     */
    public MenuScene (GUIHandler handler, ArrayList<String> animalChoices, ArrayList<String> habitatChoices, ArrayList<String> scenarioChoices)
    {
        habitatList = habitatChoices;
        animalList = animalChoices;
        climateChangeScenarioList = scenarioChoices;
        this.handler = handler;

        animalNumberReceivers = new ArrayList<>();
        selectedAnimals = new HashMap<>();
    }

    /**
     * Create a menu scene and sets it as the current scene of the GUIHandler's stage.
     * @param primaryStage (Stage) The GUI Handler's stage.
     */
    public void showView(Stage primaryStage)
    {
        primaryStage.setScene(this.createScene());
    }

    /**
     * Create the GUI Scene, setting its appearance and allowing its functionalities.
     * @return (Scene) the created scene.
     */
    private Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        Label welcomeLabel = new Label("Welcome to Ali and Anton's [Project Name]");
        welcomeLabel.setFont(new Font(24));
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(Pos.CENTER);

        // The habitat choice section.
        VBox habitatChoiceComponent = new VBox(5);
        ComboBox habitatChoiceDisplay = new ComboBox();
        habitatChoiceComponent.setPadding(new Insets(20,0,0,0));
        Label habitatChoicePrompt = new Label("Please choose a habitat for your simulation:");
        habitatChoiceComponent.getChildren().add(habitatChoicePrompt);

        for (String habitatName : habitatList) {
            habitatChoiceDisplay.getItems().add(habitatName);
        }

        habitatChoiceComponent.getChildren().add(habitatChoiceDisplay);

        // The animal choice section.
        VBox animalChoiceComponent = new VBox(5);
        animalChoiceComponent.setPadding(new Insets(20,0,0,0));

        Label animalChoicePrompt = new Label("Please choose the animals you want to see evolve in this habitat:");

        FlowPane animalListDisplay = new FlowPane();
        animalListDisplay.setHgap(10);
        animalListDisplay.setVgap(10);
        animalListDisplay.setPadding(new Insets(5,0,0,20));
        for (String animalName: animalList) {
            HBox animalComponent = new HBox(5);
            Label animalNameDisplay = new Label(animalName);
            TextField animalNumber = new TextField();
            animalNumber.setText("0");
            animalNumberReceivers.add(animalNumber);
            animalComponent.getChildren().addAll(animalNameDisplay,animalNumber);
            animalListDisplay.getChildren().add(animalComponent);
        }
        animalChoiceComponent.getChildren().addAll(animalChoicePrompt, animalListDisplay);

        // The scenario choice section.
        VBox climateChangeScenarioChoiceComponent = new VBox(5);
        ComboBox climateChangeScenarioChoiceDisplay = new ComboBox();
        climateChangeScenarioChoiceComponent.setPadding(new Insets(20,0,0,0));
        Label climateChangeScenarioChoicePrompt = new Label("Please choose a climate change scenario for your simulation:");
        climateChangeScenarioChoiceComponent.getChildren().add(climateChangeScenarioChoicePrompt);

        for (String scenarioName : climateChangeScenarioList) {
            climateChangeScenarioChoiceDisplay.getItems().add(scenarioName);
        }

        climateChangeScenarioChoiceComponent.getChildren().add(climateChangeScenarioChoiceDisplay);

        // The button to validate choices and switch to SimulatorView.
        Button actionButton = new Button();
        actionButton.setText("Simulate");
        actionButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                getInputsAndLaunchSimulation(habitatChoiceDisplay, animalNumberReceivers, climateChangeScenarioChoiceDisplay);
                ArrayList<Integer> numberInputed = getNumericValuesOfUserInputs(animalNumberReceivers);
                generateAnimalDictionary(numberInputed);
            }
        });

        VBox choiceComponents = new VBox();
        choiceComponents.getChildren().addAll(habitatChoiceComponent, animalChoiceComponent, climateChangeScenarioChoiceComponent);

        root.setTop(welcomeLabel);
        root.setBottom(actionButton);
        root.setCenter(choiceComponents);
        Scene menuScene = new Scene(root, 1000, 500);
        return menuScene;
    }

    /**
     * Return a list of Integers from the Strings inputted by the user in the various TextFields.
     * @param inputsList (ArrayList<TextField>) The list of TextFields object in which the user inputted data.
     * @return (ArrayList<Integer>) The list of integers inputted by the user.
     */
    private ArrayList<Integer> getNumericValuesOfUserInputs (ArrayList<TextField> inputsList)
    {
        ArrayList<Integer> inputedNumbers = new ArrayList<>();
        for (TextField inputReceiver : inputsList) {
            String inputValue = inputReceiver.getText();
            if (textIsANumber(inputValue)) {
                inputedNumbers.add(Integer.valueOf(inputValue));
            } else {
                throwErrorMessage("One of the values inputed is not a number.");
                return null;
            }
        }
        System.out.println(inputedNumbers.size());
        return inputedNumbers;
    }

    /**
     * Helped methods for other classes to print error messages if needed.
     * @param message (String) The error message.
     */
    private void throwErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * Checks if a given String represents an int.
     * @param textToTest (String) The String object to test.
     */
    private boolean textIsANumber(String textToTest) {
        if (textToTest.matches("[0-9]*")) {
            return true;
        }
        return false;
    }

    /**
     * Generates a HashMap associating the name of each animal with the number of this animal that must be created.
     * This method heavily relies on the fact that animal names are stored in the animalList in the same order that they were on screen.
     * @param numberOfEachAnimals (ArrayList<Integer>) The String object to test.
     */
    private boolean generateAnimalDictionary(ArrayList<Integer> numberOfEachAnimals)
    {
        if (numberOfEachAnimals.size() == animalList.size()) {
            for (int i=0; i<numberOfEachAnimals.size(); i++) {
                System.out.println("la boucle est à" + i);
                selectedAnimals.put(animalList.get(i), numberOfEachAnimals.get(i));
            }
        } else {
            throwErrorMessage("Please check that all animal numbers were inputed correctly and try again.");
            // empty map
            return false;
        }
        return true;
    }

    /**
     * Reads the name of the chosen Habitat.
     * @param habitatChoiceDisplay (ComboBox) The ComboBox used by the user to select a habitat.
     */
    private String getHabitatInput(ComboBox habitatChoiceDisplay)
    {
        String chosenHabitat = (String) habitatChoiceDisplay.getValue();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a habitat.");
            return null;
        }
        return chosenHabitat;
    }

    /**
     * Reads the name of the chosen climate change scenario.
     * @param scenarioCoiceDisplay (ComboBox) The ComboBox used by the user to select a climate change scenario.
     */
    private String getScenarioInput(ComboBox scenarioCoiceDisplay)
    {
        String chosenHabitat = (String) scenarioCoiceDisplay.getValue();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a climate change scenario.");
            return null;
        }
        return chosenHabitat;
    }

    /**
     * Retrieves all inputs in the correct form using helped methods defined in this class and launches the simulation.
     * @param habitatChoiceDisplay (ComboBox) The ComboBox used by the user to select a habitat.
     * @param animalNumberReceivers (ArrayList<TextField>) The list of TextFields used by the user to input numbers of each animal to create.
     * @param climateChangeScenarioChoiceDisplay (ComboBox) The ComboBox used by the user to select a climate change scenario.
     */
    private void getInputsAndLaunchSimulation(ComboBox habitatChoiceDisplay, ArrayList<TextField> animalNumberReceivers, ComboBox climateChangeScenarioChoiceDisplay) {
        String chosenHabitat = getHabitatInput(habitatChoiceDisplay);
        if (chosenHabitat != null) {
            ArrayList<Integer> numbersInputed = getNumericValuesOfUserInputs(animalNumberReceivers);
            System.out.println("Ici" + numbersInputed.size());
            if (numbersInputed != null) {
                boolean generationSuccessful = generateAnimalDictionary(numbersInputed);
                if (generationSuccessful) {
                    String chosenSimulation = getScenarioInput(climateChangeScenarioChoiceDisplay);
                    launchSimulation(chosenHabitat, chosenSimulation);
                }
            }
        }

    }

    /**
     * Calls the GUIHandler to launch the simulation and switch screen.
     * @param chosenHabitat (String) The name of the habitat chosen by the user.
     * @param chosenScenario (String) The name of the climate change scenario chosen by the user.
     */
    private void launchSimulation(String chosenHabitat, String chosenScenario)
    {
        handler.switchToSimulatorView(chosenHabitat,selectedAnimals,chosenScenario);
    }
}