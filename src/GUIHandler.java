import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.HashMap;

public class GUIHandler extends Application {

    private Scene menuScene;
    private Scene simulatorScene;
    private MenuScene menuSceneMaker;
    private SimulatorScene simulatorSceneMaker;
    private Stage primaryStage;
    private Initializer simulationInitializer;
    private Simulator simulatorOnDisplay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        simulationInitializer = new Initializer();
        menuSceneMaker = new MenuScene(this);

        menuScene = menuSceneMaker.createScene();

        this.primaryStage = primaryStage;

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public void switchToSimulatorView(String selectedHabitat,HashMap<String, Integer> selectedAnimals)
    {
        simulatorOnDisplay = simulationInitializer.initializeSimulation(selectedHabitat, selectedAnimals);
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