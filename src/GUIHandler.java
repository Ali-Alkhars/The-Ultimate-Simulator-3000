import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 * The GUIHandler handles the GUI by switching between scenes and coordinating their interactions with the Simulator.
 *
 * @author Anton Sirgue (K21018741) and Ali Alkhars (K20055566)
 * @version 2022.02.22
 */
public class GUIHandler {

    private Initializer simulationInitializer;
    private Simulator simulatorOnDisplay;
    private JFrame currentFrame;
    private MenuView menuViewMaker;

    /**
     * Build a GUIHandler with appropriate lists of choices for animals, habitats, and climate change scenarios.
     * @param animalChoices (ArrayList<String>) List of animal choices.
     * @param habitatChoices (ArrayList<String>) List of habitat choices.
     * @param scenarioChoices (ArrayList<String>) List of climate change scenario choices.
     */
    public GUIHandler (Initializer initializer, ArrayList<String> animalChoices, ArrayList<String> habitatChoices, ArrayList<String> scenarioChoices)
    {
        simulationInitializer = initializer;
        menuViewMaker = new MenuView(this, animalChoices, habitatChoices, scenarioChoices);
        showMenuView();
    }


    private void showMenuView() {
        JFrame menuFrame = menuViewMaker.createAndShow();
        menuFrame.pack();
        menuFrame.setVisible(true);
        currentFrame = menuFrame;
    }

    public void switchToSimulatorView(String chosenHabitat,HashMap<String, Integer> selectedAnimals,String chosenScenario)
    {
        System.out.println("le truc choisi et recu: "+chosenHabitat);
        for (String i : selectedAnimals.keySet()) {System.out.println(i + ": "+selectedAnimals.get(i));}
        System.out.println("le truc choisi et recu: "+chosenScenario);
        new Thread(() -> {
            simulatorOnDisplay = simulationInitializer.initializeSimulation(chosenHabitat, selectedAnimals, chosenScenario);
        }).start();
        SimulatorView viewForSim = simulationInitializer.getSimulatorView();
        currentFrame.setVisible(false);
        showSimulatorView(viewForSim);
    }

    public void switchToMenuView()
    {
        simulatorOnDisplay.endSimulation();
        currentFrame.setVisible(false);
        showMenuView();
    }

    private void showSimulatorView(SimulatorView viewForSim)
    {
        JFrame simulatorFrame = viewForSim.createAndShowGUI();
        simulatorFrame.pack();
        simulatorFrame.setVisible(true);
        currentFrame = simulatorFrame;
        simulatorOnDisplay.runLongSimulation();
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

    /**
     * Shows a MenuScene. A new SimulatorScene is built every step because user can launch multiple different simulations
     * in a same session and therefore go back to Menu multiple times.
     */

}