import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;



public class MenuView {

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

    public JFrame createAndShow() {
        JFrame frame = new JFrame("Hello World Java Swing");
        frame.setMinimumSize(new Dimension(800, 600));

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        JLabel welcomeLabel = new JLabel("Welcome to Ali and Anton's [Project Name]");
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 27));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // HABITAT CHOICE
        Box habitatChoiceComponent = Box.createVerticalBox();
        habitatChoiceComponent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        JLabel habitatChoicePrompt = new JLabel("Please choose a habitat for your simulation:");
        habitatChoiceComponent.add(habitatChoicePrompt);
        JComboBox habitatListDisplay = new JComboBox();

        for (String habitatName : habitatList) {
            habitatListDisplay.addItem(habitatName);
        }

        habitatChoiceComponent.add(habitatListDisplay);

        // ANIMAL CHOICE
        Box animalChoiceComponent = Box.createVerticalBox();
        animalChoiceComponent.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));


        JLabel animalChoicePrompt = new JLabel("Please choose the animals you want to see evolve in this habitat:");
        animalChoiceComponent.add(animalChoicePrompt);
        JPanel animalListDisplay = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        animalChoiceComponent.setBorder(BorderFactory.createEmptyBorder(5,0,0,20));


        for (String animalName: animalList) {
            Box animalComponent = Box.createHorizontalBox();
            JLabel animalNameDisplay = new JLabel(animalName);
            animalComponent.add(animalNameDisplay);
            JTextField animalNumber = new JTextField();
            animalNumber.setText("0");
            animalNumberReceivers.add(animalNumber);
            animalComponent.add(animalNumber);
            animalListDisplay.add(animalComponent);
        }
        animalChoiceComponent.add(animalListDisplay);

        // SCENARIO CHOICE
        Box scenarioChoiceComponent = Box.createVerticalBox();

        JLabel scenarioChoicePrompt = new JLabel("Please choose a climate change scenario for your simulation:");
        habitatChoiceComponent.add(scenarioChoicePrompt);
        JComboBox scenarioListDisplay = new JComboBox();

        for (String scenarioName : climateChangeScenarioList) {
            scenarioListDisplay.addItem(scenarioName);
        }

        habitatChoiceComponent.add(scenarioListDisplay);

        JButton actionButton = new JButton("Simulate");

        ActionListener launchSim = e -> {
            getInputsAndLaunchSimulation(habitatListDisplay, animalNumberReceivers, scenarioListDisplay);
        };

        actionButton.addActionListener(launchSim);

        Box choiceComponents = Box.createVerticalBox();
        choiceComponents.add(habitatChoiceComponent);
        choiceComponents.add(animalChoiceComponent);
        choiceComponents.add(scenarioChoiceComponent);

        mainContainer.add(welcomeLabel, BorderLayout.NORTH);
        mainContainer.add(actionButton, BorderLayout.SOUTH);
        mainContainer.add(choiceComponents, BorderLayout.CENTER);

        frame.getContentPane().add(mainContainer);
        return frame;
    }

    private ArrayList<Integer> getNumericValuesOfUserInputs (ArrayList<JTextField> inputsList)
    {
        ArrayList<Integer> inputedNumbers = new ArrayList<>();
        for (JTextField inputReceiver : inputsList) {
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

    private String getHabitatInput(JComboBox habitatChoiceDisplay)
    {
        String chosenHabitat = (String) habitatChoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a habitat.");
            return null;
        }
        return chosenHabitat;
    }

    private String getScenarioInput(JComboBox scenarioCoiceDisplay)
    {
        String chosenHabitat = (String) scenarioCoiceDisplay.getSelectedItem();
        if (chosenHabitat == null) {
            throwErrorMessage("You must choose a climate change scenario.");
            return null;
        }
        return chosenHabitat;
    }

    private void getInputsAndLaunchSimulation(JComboBox habitatChoiceDisplay, ArrayList<JTextField> animalNumberReceivers, JComboBox climateChangeScenarioChoiceDisplay) {
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