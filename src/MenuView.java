import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Builds and handles the view of the GUI in which user can choose the habitat, animals, and climate change scenario to be implemented in
 * his/her simulation.
 *
 * @author Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.28
 */
public class MenuView
{
    private GUIHandler handler;
    private ArrayList<String> habitatList;
    private ArrayList<String> animalList;
    private ArrayList<String> climateChangeScenarioList;
    private ArrayList<JTextField> animalNumberReceivers;
    private HashMap<String, Integer> selectedAnimals;

    /**
     * Initializes all fields with the appropriate list of animal, habitat and climate change scneario choices as well as the GUIHandler
     * currently governing the GUI.
     *
     * @param handler (GUIHandler) the GUIHandler currenly handling the GUI.
     * @param animalChoices (ArrayList<String>) The list of animal choices.
     * @param habitatChoices (ArrayList<String>) The list of habitat choices.
     * @param scenarioChoices (ArrayList<String>) The list of climate change scenario choices.
     */
    public MenuView (GUIHandler handler, ArrayList<String> animalChoices, ArrayList<String> habitatChoices, ArrayList<String> scenarioChoices)
    {
        habitatList = habitatChoices;
        animalList = animalChoices;
        climateChangeScenarioList = scenarioChoices;
        
        this.handler = handler;
        
        animalNumberReceivers = new ArrayList<>();
        selectedAnimals = new HashMap<>();
    }

    /**
     * Creates the actual menu view UI.
     *
     * @return (JFrame) The created menu view UI.
     */
    public JFrame createAndShow()
    {
        // Create frame and set title and minimal size.
        JFrame frame = new JFrame("Ultimate Simulator 3000");
        frame.setMinimumSize(new Dimension(600,450));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // Welcome label with specific font.
        JLabel welcomeLabel = new JLabel("Welcome to the Ultimate Simulator 3000");
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 27));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // HABITAT CHOICE SECTION

        Box habitatChoiceComponent = Box.createHorizontalBox();
        JLabel habitatChoicePrompt = new JLabel("Choose a habitat for your simulation:");

        JComboBox habitatListDisplay = new JComboBox();
        for (String habitatName : habitatList) {
            habitatListDisplay.addItem(habitatName);
        }
        
        habitatChoiceComponent.add(habitatChoicePrompt);
        habitatChoiceComponent.add(habitatListDisplay);
        
        // CLIMATE CHANGE SCENARIO CHOICE SECTION

        Box scenarioChoiceComponent = Box.createHorizontalBox();
        
        JLabel scenarioChoicePrompt = new JLabel("Choose a climate change scenario:");
        JComboBox scenarioListDisplay = new JComboBox();
        for (String scenarioName : climateChangeScenarioList) {
            scenarioListDisplay.addItem(scenarioName);
        }
        
        scenarioChoiceComponent.add(scenarioChoicePrompt);
        scenarioChoiceComponent.add(scenarioListDisplay);
        
        // ANIMAL CHOICE COMPONENT SECTION

        JPanel animalChoiceComponent = new JPanel();
        animalChoiceComponent.setLayout(new BorderLayout());

        // Labels to guide animals choice.
        JLabel animalChoicePrompt = new JLabel("Please choose the animals you want to see evolve in this habitat:");
        JLabel animalChoiceExplanationPrompt = new JLabel("(Input the number of each of these animals you want to include, we recommend adding more than 300 of each animal you choose)");
        animalChoicePrompt.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        animalChoiceExplanationPrompt.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        // Technique to set the italic font: https://java2everyone.blogspot.com/2008/12/set-jlabel-text-italic.html?m=0
        Font italicFont = new Font(animalChoiceExplanationPrompt.getFont().getName(),Font.ITALIC,animalChoiceExplanationPrompt.getFont().getSize());
        animalChoiceExplanationPrompt.setFont(italicFont);

        // Setting the Layout of the two created labels.
        JPanel animalPrompts = new JPanel();
        animalPrompts.setLayout(new BoxLayout(animalPrompts, BoxLayout.PAGE_AXIS));

        animalPrompts.add(Box.createVerticalGlue());
        animalPrompts.add(animalChoicePrompt);
        animalPrompts.add(animalChoiceExplanationPrompt);
        animalPrompts.add(Box.createRigidArea(new Dimension(0, 20)));

        // GridLayout to add the animal choices
        GridLayout animalListDisplayLayout = new GridLayout(5,5);
        animalListDisplayLayout.setHgap(10);
        animalListDisplayLayout.setVgap(7);
        JPanel animalListDisplay = new JPanel(animalListDisplayLayout);
        for (String animalName : animalList) {
            Box animalComponent = Box.createHorizontalBox();
                    // technique to capitalize first letter : https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
            String capitalizedAnimalName = animalName.substring(0, 1).toUpperCase() + animalName.substring(1);
            JLabel animalNameDisplay = new JLabel(capitalizedAnimalName);
            animalComponent.add(animalNameDisplay);
            JTextField animalNumber = new JTextField();
            animalNumber.setText("0");
            animalNumberReceivers.add(animalNumber);
            animalComponent.add(animalNumber);
            animalListDisplay.add(animalComponent);
        }

        animalChoiceComponent.add(animalPrompts, BorderLayout.CENTER);
        animalChoiceComponent.add(animalListDisplay, BorderLayout.PAGE_END);
        
        // SIMULATE BUTTON
        JButton actionButton = new JButton("Simulate");

        ActionListener launchSim = e -> {
            getInputsAndLaunchSimulation(habitatListDisplay, animalNumberReceivers, scenarioListDisplay);
        };

        actionButton.addActionListener(launchSim);

        // PUTTING THE UI TOGETHER
        Box choiceComponents = Box.createVerticalBox();
        choiceComponents.setBorder(BorderFactory.createEmptyBorder(30,0,10,0));
        choiceComponents.add(habitatChoiceComponent);
        choiceComponents.add(scenarioChoiceComponent);
        choiceComponents.add(animalChoiceComponent);
        
        mainContainer.add(welcomeLabel, BorderLayout.NORTH);
        mainContainer.add(choiceComponents, BorderLayout.CENTER);
        mainContainer.add(actionButton, BorderLayout.SOUTH);

        frame.getContentPane().add(mainContainer);
        return frame;
    }

    /**
     * Retrieves all inputs in the correct form using helper methods defined in this class and launches the simulation.
     *
     * @param habitatChoiceDisplay (JComboBox) The ComboBox used by the user to select a habitat.
     * @param animalNumberReceivers (ArrayList<JTextField>) The list of TextFields used by the user to input numbers of each animal to create.
     * @param climateChangeScenarioChoiceDisplay (ComboBox) The ComboBox used by the user to select a climate change scenario.
     */

    private void getInputsAndLaunchSimulation(JComboBox habitatChoiceDisplay, ArrayList<JTextField> animalNumberReceivers, JComboBox climateChangeScenarioChoiceDisplay) {
        String chosenHabitat = getHabitatInput(habitatChoiceDisplay);
        if (chosenHabitat != null) {
            ArrayList<Integer> numbersInputted = getNumericValuesOfUserInputs(animalNumberReceivers);
            System.out.println("Ici" + numbersInputted.size());
            if (numbersInputted != null) {
                boolean generationSuccessful = generateAnimalDictionary(numbersInputted);
                if (generationSuccessful) {
                    String chosenSimulation = getScenarioInput(climateChangeScenarioChoiceDisplay);
                    launchSimulation(chosenHabitat, chosenSimulation);
                }
            }
        }

    }

    /**
     * Return a list of Integers from the Strings inputted by the user in the various TextFields.
     *
     * @param inputsList (ArrayList<JTextField>) The list of TextFields object in which the user inputted data.
     * @return (ArrayList<Integer>) The list of integers inputted by the user.
     */

    private ArrayList<Integer> getNumericValuesOfUserInputs (ArrayList<JTextField> inputsList)
    {
        ArrayList<Integer> inputtedNumbers = new ArrayList<>();
        for (JTextField inputReceiver : inputsList) {
            String inputValue = inputReceiver.getText();
            if (textIsANumber(inputValue)) {
                inputtedNumbers.add(Integer.valueOf(inputValue));
            } else {
                throwErrorMessage("One of the values inputted is not a number.");
                return null;
            }
        }
        System.out.println(inputtedNumbers.size());
        return inputtedNumbers;
    }

    /**
     * Helper methods for other classes to print error messages if needed.
     *
     * @param message (String) The error message.
     */
    private void throwErrorMessage(String message) {
        System.out.println(message);
    }

    /**
     * Check if a given String represents an int.
     * Source: technique found on XXXXXXXXX
     *
     * @param textToTest (String) The String object to test.
     */
    private boolean textIsANumber(String textToTest) {
        return textToTest.matches("[0-9]*");
    }

    /**
     * Generates a HashMap associating the name of each animal with the number of this animal that must be created.
     * This method heavily relies on the fact that animal names are stored in the animalList in the same order that they were on screen.
     *
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
            throwErrorMessage("Please check that all animal numbers were inputted correctly and try again.");
            // empty map
            return false;
        }
        return true;
    }

    /**
     * Reads the name of the chosen Habitat.
     *
     * @param habitatChoiceDisplay (JComboBox) The ComboBox used by the user to select a habitat.
     */

    private String getHabitatInput(JComboBox habitatChoiceDisplay)
    {
        String chosenHabitat = (String) habitatChoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a habitat.");
            return null;
        }
        return chosenHabitat;
    }

    /**
     * Reads the name of the chosen climate change scenario.
     *
     * @param scenarioChoiceDisplay (JComboBox) The ComboBox used by the user to select a climate change scenario.
     */

    private String getScenarioInput(JComboBox scenarioChoiceDisplay)
    {
        String chosenHabitat = (String) scenarioChoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a climate change scenario.");
            return null;
        }
        return chosenHabitat;
    }

    /**
     * Calls the GUIHandler to launch the simulation and switch screen.
     *
     * @param chosenHabitat (String) The name of the habitat chosen by the user.
     * @param chosenScenario (String) The name of the climate change scenario chosen by the user.
     */

    private void launchSimulation(String chosenHabitat, String chosenScenario)
    {
        handler.switchToSimulatorView(chosenHabitat,selectedAnimals,chosenScenario);
    }
}

