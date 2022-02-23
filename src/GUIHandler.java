import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The GUIHandler handles the GUI by switching between scenes and coordinating their interactions with the Simulator.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */
public class GUIHandler extends Application {

    private Initializer simulationInitializer;
    private Simulator simulatorOnDisplay;

    static Stage classStage = new Stage();

    private ArrayList<String> animalChoices;
    private ArrayList<String> habitatChoices;
    private ArrayList<String> scenarioChoices;

    /**
     * Build a GUIHandler with appropriate lists of choices for animals, habitats, and climate change scenarios.
     * @param animalChoices (ArrayList<String>) List of animal choices.
     * @param habitatChoices (ArrayList<String>) List of habitat choices.
     * @param scenarioChoices (ArrayList<String>) List of climate change scenario choices.
     */
    public GUIHandler (ArrayList<String> animalChoices, ArrayList<String> habitatChoices, ArrayList<String> scenarioChoices)
    {
        this.animalChoices = animalChoices;
        this.habitatChoices = habitatChoices;
        this.scenarioChoices = scenarioChoices;
    }

    /**
     * Start the GUI Application, the first screen is the MenuScene for users to make his/her choices.
     * @param primaryStage (Stage) The primary stage to be used by our application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        classStage = primaryStage;
        simulationInitializer = new Initializer();
        showMenuScene();
        primaryStage.show();
    }

    /**
     * Switch the screen on view to SimulatorScene. This method first initializes the simulation, then sets the new screen
     * on view, and finally launches the Simulator.
     * Note: A new SimulatorScene is built every time because user can launch multiple different simulations in a same session.
     * Note 2: Asynchronous behaviour was implemented thanks to https://stackoverflow.com/questions/1842734/how-to-asynchronously-call-a-method-in-java
     * @param selectedHabitat (String) The name of the selected habitat.
     * @param selectedAnimals (HashMap<String, Integer>) Animal chosen and their numbers.
     * @param selectedScenario (String) The name of the chosen scenario.
     */
    public void switchToSimulatorView(String selectedHabitat,HashMap<String, Integer> selectedAnimals, String selectedScenario)
    {
        new Thread(() -> {
            simulatorOnDisplay = simulationInitializer.initializeSimulation(selectedHabitat, selectedAnimals, selectedScenario);
        }).start();
        SimulatorScene simulatorSceneMaker = new SimulatorScene(this, simulationInitializer.getSimulatorView());
        simulatorSceneMaker.showView(classStage);
    }

    /**
     * Switch the screen on view to SimulatorScene. This method first ends the simulation before showing the MenuScene.
     */
    public void switchToMenuView()
    {
        simulatorOnDisplay.endSimulation();
        showMenuScene();
    }

    /**
     * Shows a MenuScene. A new SimulatorScene is built every step because user can launch multiple different simulations
     * in a same session and therefore go back to Menu multiple times.
     */
    private void showMenuScene()
    {
        MenuScene menuSceneMaker = new MenuScene(this, animalChoices, habitatChoices, scenarioChoices);
        menuSceneMaker.showView(classStage);
    }
}