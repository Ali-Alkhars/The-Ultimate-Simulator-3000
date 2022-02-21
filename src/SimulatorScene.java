import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.embed.swing.SwingNode;

public class SimulatorScene {

    private GUIHandler handler;

    public SimulatorScene (GUIHandler handler, SimulatorView simulatorView) {
        this.handler = handler;
    }
    public Scene createScene() {
        BorderPane root = new BorderPane();
        final SwingNode simulatorView = new SwingNode();
        SimulatorView simulatorViewHandler = new SimulatorView();
        simulatorView.setContent(simulatorViewHandler.createAndShowGUI());





        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                handler.switchToMenuView();
            }
        });

        root.setBottom(btn);
        root.setCenter(simulatorView);
        Scene menuScene = new Scene(root, 1000, 500);
        return menuScene;

    }
}