import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.Border;

/**
 * NEEDS TO BE ADDED
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
    
    public MenuView (GUIHandler handler, ArrayList<String> animalChoices, ArrayList<String> habitatChoices, ArrayList<String> scenarioChoices)
    {
        habitatList = habitatChoices;
        animalList = animalChoices;
        climateChangeScenarioList = scenarioChoices;
        
        this.handler = handler;
        
        animalNumberReceivers = new ArrayList<>();
        selectedAnimals = new HashMap<>();
    }
    
    public JFrame createAndShow()
    {
        JFrame frame = new JFrame("Ultimate Simulator 3000");
        frame.setMinimumSize(new Dimension(600,450));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JLabel welcomeLabel = new JLabel("Welcome to the Ultimate Simulator 3000");
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 27));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // HABITAT CHOICE
        Box habitatChoiceComponent = Box.createHorizontalBox();
        JLabel habitatChoicePrompt = new JLabel("Choose a habitat for your simulation:");

        JComboBox habitatListDisplay = new JComboBox();
        for (String habitatName : habitatList) {
            habitatListDisplay.addItem(habitatName);
        }
        
        habitatChoiceComponent.add(habitatChoicePrompt);
        habitatChoiceComponent.add(habitatListDisplay);
        
        // CLIMATE CHANGE SCENARIO CHOICE
        Box scenarioChoiceComponent = Box.createHorizontalBox();
        
        JLabel scenarioChoicePrompt = new JLabel("Choose a climate change scenario:");
        JComboBox scenarioListDisplay = new JComboBox();
        for (String scenarioName : climateChangeScenarioList) {
            scenarioListDisplay.addItem(scenarioName);
        }
        
        scenarioChoiceComponent.add(scenarioChoicePrompt);
        scenarioChoiceComponent.add(scenarioListDisplay);
        
        // ANIMAL CHOICE COMPONENT
        JPanel animalChoiceComponent = new JPanel();
        animalChoiceComponent.setLayout(new BorderLayout());
        
        JLabel animalChoicePrompt = new JLabel("Please choose the animals you want to see evolve in this habitat:");
        JLabel animalChoiceExplanationPrompt = new JLabel("(Input the number of each of these animals you want to include, we recommend adding more than 300 animals)");

        animalChoicePrompt.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        animalChoiceExplanationPrompt.setAlignmentX(JLabel.CENTER_ALIGNMENT);

        // Technique to set the italic font: https://java2everyone.blogspot.com/2008/12/set-jlabel-text-italic.html?m=0
        Font italicFont = new Font(animalChoiceExplanationPrompt.getFont().getName(),Font.ITALIC,animalChoiceExplanationPrompt.getFont().getSize());
        animalChoiceExplanationPrompt.setFont(italicFont);

        // A Box layout to add both animal choice prompts in
        JPanel animalPrompts = new JPanel();
        animalPrompts.setLayout(new BoxLayout(animalPrompts, BoxLayout.PAGE_AXIS));

        animalPrompts.add(Box.createVerticalGlue());
        animalPrompts.add(animalChoicePrompt);
        animalPrompts.add(animalChoiceExplanationPrompt);
        animalPrompts.add(Box.createRigidArea(new Dimension(0, 20)));

        // GridLayout to add the animal choices
        GridLayout animalListDisplayLayout = new GridLayout(5,4);
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
        
        
        JButton actionButton = new JButton("Simulate");

        ActionListener launchSim = e -> {
            getInputsAndLaunchSimulation(habitatListDisplay, animalNumberReceivers, scenarioListDisplay);
        };

        actionButton.addActionListener(launchSim);

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

    private void throwErrorMessage(String message) {
        System.out.println(message);
    }

    private boolean textIsANumber(String textToTest) {
        return textToTest.matches("[0-9]*");
    }

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

    private String getHabitatInput(JComboBox habitatChoiceDisplay)
    {
        String chosenHabitat = (String) habitatChoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a habitat.");
            return null;
        }
        return chosenHabitat;
    }

    private String getScenarioInput(JComboBox scenarioChoiceDisplay)
    {
        String chosenHabitat = (String) scenarioChoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a climate change scenario.");
            return null;
        }
        return chosenHabitat;
    }

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

    private void launchSimulation(String chosenHabitat, String chosenScenario)
    {
        handler.switchToSimulatorView(chosenHabitat,selectedAnimals,chosenScenario);
    }
}

