import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

public class GUIHandler extends Application {

    private Scene menuScene;
    private Scene simulatorScene;
    private MenuScene menuSceneMaker;
    private SimulatorScene simulatorSceneMaker;
    private Stage primaryStage;
    private Initializer simulationInitializer;
    private Simulator simulatorOnDisplay;

    static Stage classStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {

        classStage = primaryStage;
        simulationInitializer = new Initializer();
        menuSceneMaker = new MenuScene(this);

        menuScene = menuSceneMaker.createScene();

        this.primaryStage = primaryStage;

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public void switchToSimulatorView(String selectedHabitat,HashMap<String, Integer> selectedAnimals, String selectedScenario)
    {
        simulatorOnDisplay = simulationInitializer.initializeSimulation(selectedHabitat, selectedAnimals, selectedScenario);
        simulatorSceneMaker = new SimulatorScene(this, simulationInitializer.getSimulatorView());
        simulatorScene = simulatorSceneMaker.createScene();
        primaryStage.setScene(simulatorScene);
        // start simulation
    }

    public void switchToMenuView()
    {
        simulatorOnDisplay.endSimulation();
        primaryStage.setScene(menuScene);
    }

}