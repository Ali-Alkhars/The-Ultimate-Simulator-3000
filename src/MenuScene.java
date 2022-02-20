import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import java.util.HashMap;

public class MenuScene{

    private GUIHandler handler;
    private ArrayList<String> habitatList;
    private ArrayList<String> animalList;
    private ArrayList<String> climateChangeScenarioList;
    private ArrayList<TextField> animalNumberReceivers;
    private HashMap<String, Integer> selectedAnimals;


    public MenuScene (GUIHandler handler)
    {
        habitatList = new ArrayList<>();
        animalList = new ArrayList<>();
        climateChangeScenarioList = new ArrayList<>();
        habitatList.add("hr");
        habitatList.add("hr");
        habitatList.add("hr");
        habitatList.add("hr");
        animalList.add("lion");
        animalList.add("elep");
        animalList.add("lioffn");
        animalList.add("lisdweon");
        animalList.add("fefe");
        climateChangeScenarioList.add("ff");
        climateChangeScenarioList.add("ff");
        climateChangeScenarioList.add("ff");
        climateChangeScenarioList.add("ff");
        this.handler = handler;

        animalNumberReceivers = new ArrayList<>();
        selectedAnimals = new HashMap<>();
    }

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(5, 5, 5, 5));
        Label welcomeLabel = new Label("Welcome to Ali and Anton's [Project Name]");
        welcomeLabel.setFont(new Font(24));
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);
        welcomeLabel.setAlignment(Pos.CENTER);

        // HABITAT CHOICE
        VBox habitatChoiceComponent = new VBox(5);
        ComboBox habitatChoiceDisplay = new ComboBox();
        habitatChoiceComponent.setPadding(new Insets(20,0,0,0));
        Label habitatChoicePrompt = new Label("Please choose a habitat for your simulation:");
        habitatChoiceComponent.getChildren().add(habitatChoicePrompt);

        for (String habitatName : habitatList) {
            habitatChoiceDisplay.getItems().add(habitatName);
        }

        habitatChoiceComponent.getChildren().add(habitatChoiceDisplay);

        // ANIMAL CHOICE
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

        // SCENARIO CHOICE
        VBox climateChangeScenarioChoiceComponent = new VBox(5);
        ComboBox climateChangeScenarioChoiceDisplay = new ComboBox();
        climateChangeScenarioChoiceComponent.setPadding(new Insets(20,0,0,0));
        Label climateChangeScenarioChoicePrompt = new Label("Please choose a climate change scenario for your simulation:");
        climateChangeScenarioChoiceComponent.getChildren().add(climateChangeScenarioChoicePrompt);

        for (String scenarioName : climateChangeScenarioList) {
            climateChangeScenarioChoiceDisplay.getItems().add(scenarioName);
        }

        climateChangeScenarioChoiceComponent.getChildren().add(climateChangeScenarioChoiceDisplay);

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

    private void throwErrorMessage(String message) {
        System.out.println(message);
    }

    private boolean textIsANumber(String textToTest) {
        if (textToTest.matches("[0-9]*")) {
            return true;
        }
        return false;
    }

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

    private String getHabitatInput(ComboBox habitatChoiceDisplay)
    {
        String chosenHabitat = (String) habitatChoiceDisplay.getValue();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a habitat.");
            return null;
        }
        return chosenHabitat;
    }

    private String getScenarioInput(ComboBox scenarioCoiceDisplay)
    {
        String chosenHabitat = (String) scenarioCoiceDisplay.getValue();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a climate change scenario.");
            return null;
        }
        return chosenHabitat;
    }

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

    private void launchSimulation(String chosenHabitat, String chosenScenario)
    {
        handler.switchToSimulatorView(chosenHabitat,selectedAnimals,chosenScenario);
    }
}