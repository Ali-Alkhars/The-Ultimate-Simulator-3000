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

<<<<<<< Updated upstream
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

=======
        JLabel welcomeLabel = createWelcomeLabel("Welcome to the Ultimate Simulator 3000");
        // HABITAT CHOICE SECTION
        JComboBox habitatListDisplay = createListDisplayFromList(habitatList);
        Box habitatChoiceComponent = createHabitatChoiceComponent(habitatListDisplay);
        // CLIMATE CHANGE SCENARIO CHANGE SECTION
        JComboBox scenarioListDisplay = createListDisplayFromList(climateChangeScenarioList);
        Box scenarioChoiceComponent = createScenarioChoiceComponent(scenarioListDisplay);
        // ANIMAL CHOICE SECTION
        JPanel animalChoiceComponent = createAnimalChoiceComponent();

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

    private JLabel createWelcomeLabel(String welcomeText)
    {
        JLabel welcomeLabel = new JLabel(welcomeText);
        welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 27));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return welcomeLabel;
    }

    private Box createHabitatChoiceComponent(JComboBox habitatListDisplay)
    {
        Box habitatChoiceComponent = Box.createHorizontalBox();
        JLabel habitatChoicePrompt = new JLabel("Choose a habitat for your simulation:");
        habitatChoiceComponent.add(habitatChoicePrompt);
        habitatChoiceComponent.add(habitatListDisplay);

        return habitatChoiceComponent;
    }

    private JComboBox createListDisplayFromList (ArrayList<String> list)
    {
        JComboBox listDisplay = new JComboBox();
        for (String element : list) {
            listDisplay.addItem(element);
        }
        return listDisplay;
    }

    private Box createScenarioChoiceComponent(JComboBox scenarioListDisplay)
    {
        Box scenarioChoiceComponent = Box.createHorizontalBox();
        JLabel scenarioChoicePrompt = new JLabel("Choose a climate change scenario:");
        scenarioChoiceComponent.add(scenarioChoicePrompt);
        scenarioChoiceComponent.add(scenarioListDisplay);

        return scenarioChoiceComponent;
    }

    private JPanel createAnimalChoiceComponent()
    {
        JPanel animalChoiceComponent = new JPanel();
        animalChoiceComponent.setLayout(new BorderLayout());
>>>>>>> Stashed changes

        JLabel animalChoicePrompt = new JLabel("Please choose the animals you want to see evolve in this habitat:");
<<<<<<< Updated upstream
        animalChoiceComponent.add(animalChoicePrompt);
        JPanel animalListDisplay = new JPanel(new FlowLayout(FlowLayout.CENTER, 10,10));
        animalChoiceComponent.setBorder(BorderFactory.createEmptyBorder(5,0,0,20));


        for (String animalName: animalList) {
            Box animalComponent = Box.createHorizontalBox();
            JLabel animalNameDisplay = new JLabel(animalName);
=======
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

        JPanel animalListDisplay = createAnimalListDisplay();

        animalChoiceComponent.add(animalPrompts, BorderLayout.CENTER);
        animalChoiceComponent.add(animalListDisplay, BorderLayout.PAGE_END);

        return animalChoiceComponent;
    }

    private JPanel createAnimalListDisplay()
    {
        GridLayout animalListDisplayLayout = new GridLayout(5,5);
        animalListDisplayLayout.setHgap(10);
        animalListDisplayLayout.setVgap(7);

        JPanel animalListDisplay = new JPanel(animalListDisplayLayout);

        for (String animalName : animalList) {
            Box animalComponent = Box.createHorizontalBox();
            // technique to capitalize first letter : https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
            String capitalizedAnimalName = animalName.substring(0, 1).toUpperCase() + animalName.substring(1);
            JLabel animalNameDisplay = new JLabel(capitalizedAnimalName);
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
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

=======
        return animalListDisplay;
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
     * Source for try/catch construct to catch non-numerical values: https://stackabuse.com/java-check-if-string-is-a-number/
     * @param inputsList (ArrayList<JTextField>) The list of TextFields object in which the user inputted data.
     * @return (ArrayList<Integer>) The list of integers inputted by the user.
     */
>>>>>>> Stashed changes
    private ArrayList<Integer> getNumericValuesOfUserInputs (ArrayList<JTextField> inputsList)
    {
        ArrayList<Integer> inputedNumbers = new ArrayList<>();
        for (JTextField inputReceiver : inputsList) {
            String inputValue = inputReceiver.getText();
<<<<<<< Updated upstream
            if (textIsANumber(inputValue)) {
                inputedNumbers.add(Integer.valueOf(inputValue));
            } else {
                throwErrorMessage("One of the values inputed is not a number.");
                return null;
            }
        }
        System.out.println(inputedNumbers.size());
        return inputedNumbers;
=======
            try {
                int numericValue = Integer.parseInt(inputValue);
                inputtedNumbers.add(numericValue);
            } catch (NumberFormatException e) {
                throwErrorMessage("One of the values inputted is not a number or on cell was left blank, please try again.");
            }
        }
        return inputtedNumbers;
>>>>>>> Stashed changes
    }

    private void throwErrorMessage(String message) {
        System.out.println(message);
    }

<<<<<<< Updated upstream
    private boolean textIsANumber(String textToTest) {
        if (textToTest.matches("[0-9]*")) {
            return true;
        }
        return false;
    }

=======
    /**
     * Generates a HashMap associating the name of each animal with the number of this animal that must be created.
     * This method heavily relies on the fact that animal names are stored in the animalList in the same order that they were on screen.
     *
     * @param numberOfEachAnimals (ArrayList<Integer>) The String object to test.
     */
>>>>>>> Stashed changes
    private boolean generateAnimalDictionary(ArrayList<Integer> numberOfEachAnimals)
    {
        if (numberOfEachAnimals.size() == animalList.size()) {
            for (int i=0; i<numberOfEachAnimals.size(); i++) {
                selectedAnimals.put(animalList.get(i), numberOfEachAnimals.get(i));
            }
        } else {
<<<<<<< Updated upstream
            throwErrorMessage("Please check that all animal numbers were inputed correctly and try again.");
            // empty map
=======
>>>>>>> Stashed changes
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