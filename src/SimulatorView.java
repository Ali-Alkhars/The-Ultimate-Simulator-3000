import java.awt.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling, Ali Alkhars (K20055566) and Anton Sirgue (K21018741)
 * @version 2022.02.27
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String TIME_PREFIX = "Time: ";
    private final String TEMPERATURE_PREFIX = "Temperature: ";
    private final String SEASON_PREFIX = "Season: ";
    private JLabel stepLabel, infoLabel, timeLabel, temperatureLabel, seasonLabel;
    private JPanel population;
    private FieldView fieldView;

    // A map for storing colors for participants in the simulation
    private Map<String, Color> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;
    
    private GUIHandler handler;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width, GUIHandler handler)
    {
        this.handler = handler;
        stats = new FieldStats();
        colors = new LinkedHashMap<>();
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        timeLabel = new JLabel(TIME_PREFIX, JLabel.CENTER);
        temperatureLabel = new JLabel(TEMPERATURE_PREFIX, JLabel.CENTER);
        seasonLabel = new JLabel(SEASON_PREFIX, JLabel.CENTER);
        
        population = new JPanel();
        // Grid with 5 columns and as many rows as necessary.
        GridLayout gridLayout = new GridLayout(4,5);
        population.setLayout(gridLayout);
        
        setLocation(100, 50);

        fieldView = new FieldView(height, width);
        
        JFrame frame = new JFrame("Life Simulator 3000");
        frame.setMinimumSize(new Dimension(800, 600));

        FlowLayout simInfo = new FlowLayout();
        simInfo.setHgap(50);

        JPanel infoPane = new JPanel(simInfo);
        infoPane.add(infoLabel, BorderLayout.CENTER);
        infoPane.add(stepLabel, BorderLayout.CENTER);
        infoPane.add(timeLabel, BorderLayout.CENTER);
        infoPane.add(seasonLabel, BorderLayout.CENTER);
        infoPane.add(temperatureLabel, BorderLayout.CENTER);

        JButton launchLongSimButton = new JButton("Launch long simulation");
        ActionListener launchLongSim = e -> {
            handler.launchLongSimulation();
        };
        launchLongSimButton.addActionListener(launchLongSim);
        
        JButton launchHundredStepsButton = new JButton("Run 100 steps");
        ActionListener launchHundredSteps = e -> {
            handler.runHundredSteps();
        };
        launchHundredStepsButton.addActionListener(launchHundredSteps);
        
        JButton launchOneStepButton  = new JButton("Run 1 step");
        ActionListener launchOneStep = e -> {
            handler.runOneStep();
        };
        launchOneStepButton.addActionListener(launchOneStep);
        
        JButton goBackMenuButton  = new JButton("Run a new simulation");
        ActionListener goBackMenu = e -> {
            setVisible(false);
            handler.switchToMenuView();
        };
        goBackMenuButton.addActionListener(goBackMenu);
        
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(launchLongSimButton);
        buttons.add(launchHundredStepsButton);
        buttons.add(launchOneStepButton);
        buttons.add(goBackMenuButton);
        
        Box bottomComponents = Box.createVerticalBox();
        bottomComponents.add(population);
        bottomComponents.add(buttons);
        
        JPanel centeredFieldView = new JPanel(new BorderLayout());
        centeredFieldView.add(fieldView, BorderLayout.CENTER);
        add(infoPane, BorderLayout.NORTH);
        add(fieldView, BorderLayout.CENTER);
        add(bottomComponents, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    /**
     * Define a color to be used for a given class of specie.
     * @param speciesName The specie's name.
     * @param color The color to be used for the given class.
     */
    public void setColor(String speciesName, Color color)
    {
        colors.put(speciesName, color);
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    /**
     * @return The color to be used for a given class of specie.
     */
    private Color getColor(String speciesName)
    {
        Color col = colors.get(speciesName);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     *
     * Show the current status of the field.
     *
     * @param step Which iteration step it is.
     * @param time The current time in the simulation
     * @param season The current season in the simulation
     * @param temperature The current temperature in the simulation
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(int step, String time, String season, int temperature, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }

        stepLabel.setText(STEP_PREFIX + step);
        timeLabel.setText(TIME_PREFIX + time);
        seasonLabel.setText(SEASON_PREFIX + season);
        temperatureLabel.setText(TEMPERATURE_PREFIX + temperature + " C");
        stats.reset();

        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object specie = field.getObjectAt(row, col);
                if(specie != null) {
                    Species speciesObjects = (Species) specie;
                    stats.incrementCount(speciesObjects.getName());
                    fieldView.drawMark(col, row, getColor(speciesObjects.getName()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();
        
        fieldView.repaint();
        generatePopulationComponent(field);
    }
    
    /**
     *
     * Generates a population component with the color, count, and name of each species present in the simulation.
     * The technique to generate the small squares of a given color was found on https://zetcode.com/javaswing/basicswingcomponentsII/
     * @param field (Field) The field whose population status has to be displayed.
     */
    private void generatePopulationComponent(Field field)
    {
        population.removeAll();
        stats.checkCountIsValid(field);
        for (String speciesName : colors.keySet()) {
            int count = stats.getCount(speciesName);
            
            Box speciesDetails = Box.createHorizontalBox();
            population.add(speciesDetails);
            JPanel colorDisplay = new JPanel();
            colorDisplay.setMaximumSize(new Dimension(10, 10));
            colorDisplay.setBorder(LineBorder.createGrayLineBorder());
            colorDisplay.setBackground(colors.get(speciesName));
            // The pre-leading space should be improved as it is just a way to add a natural looking padding between the colored square and text. 
            JLabel nameAndCount = new JLabel(" " + speciesName + ": " + count);
            speciesDetails.add(colorDisplay);
            speciesDetails.add(nameAndCount);

            population.add(speciesDetails);
        }
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }

    /**
     * Provide a graphical view of a rectangular field. This is
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this
     * for your project if you like.
     */
    class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                    gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }

        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}