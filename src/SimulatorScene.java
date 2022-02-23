import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.embed.swing.SwingNode;
import javafx.stage.Stage;

/**
 * Builds and handles the scene of the GUI in which user can see the simulation happening. This class makes use of a SwingNode
 * (https://docs.oracle.com/javase/8/javafx/api/javafx/embed/swing/SwingNode.html) to simply render the Swing code of the SimulatorView class
 * (patrly rewritten but heavily dependent on the class provided in the handout) in our JavaFX application.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */
public class SimulatorScene {
    // The GUIHandler to communicate with.
    private GUIHandler handler;
    // The SimulatorView object for the currently running simulation to be displayed to the user.
    private SimulatorView simulatorViewSwing;

    /**
     * Builds an SimulatorScene object and initializes its field.
     * @param handler (GUIHandler) The GUIHandler object that governs the GUI and which with this object should communicate.
     * @param simulatorView (SimulatorView) The SimulatorView to render in our application.
     */
    public SimulatorScene (GUIHandler handler, SimulatorView simulatorView) {
        this.handler = handler;
        this.simulatorViewSwing = simulatorView;
    }

    /**
     * Create a simulator scene and sets it as the current scene of the GUIHandler's stage.
     * @param primaryStage (Stage) The GUI Handler's stage.
     */
    public void showView(Stage primaryStage)
    {
        primaryStage.setScene(this.createScene());
    }

    /**
     * Creates a scene incorporating the SimulatorView in a SwingNode and adding a button to end the simulation and go back to menu view.
     * @return (Scene) The created scene.
     */
    private Scene createScene() {
        BorderPane root = new BorderPane();
        final SwingNode simulatorView = new SwingNode();
        simulatorView.setContent(simulatorViewSwing.createAndShowGUI());

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
        Scene menuScene = new Scene(root, 1000, 500); // define size
        return menuScene;

    }
}