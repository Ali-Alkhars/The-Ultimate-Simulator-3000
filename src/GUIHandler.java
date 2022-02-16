import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIHandler extends Application {

    private Scene menuScene;
    private Scene simulatorScene;
    private MenuScene menuSceneMaker;
    private SimulatorScene simulatorSceneMaker;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        menuSceneMaker = new MenuScene(this);
        simulatorSceneMaker = new SimulatorScene(this);
        menuScene = menuSceneMaker.createScene();
        simulatorScene = simulatorSceneMaker.createScene();

        this.primaryStage = primaryStage;

        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public void switchToSimulatorView()
    {
        primaryStage.setScene(simulatorScene);
        // start simulation
    }

    public void switchToMenuView()
    {
        //end simulation
        primaryStage.setScene(menuScene);
    }

}