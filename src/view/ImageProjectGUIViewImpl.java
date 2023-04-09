package view;

import controller.Features;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import model.ImageProjectState;


/**
 * Represents a Graphic User Interface for interacting with Image Collage Software.
 */
public class ImageProjectGUIViewImpl extends JFrame implements ImageProjectGUIView, ActionListener {

  private ImageProjectState model; // The model this view will represent. Only has access to getter
  // methods for information purposes

  int currentLayerIndex;
  private final JPanel mainPanel;
  private final JPanel mainBottomPanel;
  private final JPanel introScreen;
  private final JPanel controls;
  private JPanel imageDisplay;
  private JLabel imageLabel;
  private JPanel radioPanel;
  private final JButton initialNewProjectButton;
  private final JButton initialLoadNewProjectButton;
  private final JButton nPButton;
  private final JButton aITLButton;
  private final JButton nLButton;
  private final JButton sFButton;
  private final JButton lPButton;
  private final JButton sPButton;
  private final JButton eButton;
  private final JButton sIButton;


  /**
   * Constructs a new {@code ImageProjectGUIViewImpl} with the given
   * {@code ImageProjectState} model.
   * @param model the model being represented by the GUI
   */
  public ImageProjectGUIViewImpl(ImageProjectState model) {
    super();
    this.model = model;

    this.setTitle("Image Processor");
    this.setSize(800, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.currentLayerIndex = 0;


    //Creates two panels to be top and bottom section
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.LINE_AXIS));
    this.mainPanel.setPreferredSize(new Dimension(1200, 500));
    this.mainBottomPanel = new JPanel();
    this.mainBottomPanel.setLayout(new BoxLayout(this.mainBottomPanel, BoxLayout.PAGE_AXIS));

    // adds top panel above bottom section
    this.mainBottomPanel.add(this.mainPanel);
    this.add(mainBottomPanel);

    //Pre-project section -------------------------------------------------------------------------
    this.introScreen = new JPanel();
    this.introScreen.setLayout(new GridLayout(3, 0, 10, 10));
    this.introScreen.setMaximumSize(new Dimension(200, 300));


    JTextPane intro = new JTextPane();
    intro.setEditable(false);
    // the next 3 lines just center the text in the text pane
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    intro.setParagraphAttributes(center, true);

    intro.setText("Please either create a new project or load an existing one.");
    this.introScreen.add(intro);

    JPanel initialButtons = new JPanel();
    initialButtons.setLayout(new GridLayout(0, 2, 10, 10));
    //Creates a button to make the user create a new project when first loading in
    this.initialNewProjectButton = new JButton("Create");
    //Creates a button to make the user load a project when they first load in
    this.initialLoadNewProjectButton = new JButton("Load");

    initialButtons.add(this.initialNewProjectButton);
    initialButtons.add(this.initialLoadNewProjectButton);

    this.introScreen.add(intro);
    this.introScreen.add(initialButtons);

    this.mainPanel.add(this.introScreen);


    //prepares buttons for mainGUI use
    // does not use buttons until runMainGUI is run
    // This panel contains relevant commands
    this.controls = new JPanel();

    // divides buttons into two categories to make them look nicer in GUI
    JPanel projectControls = new JPanel();
    projectControls.setLayout(new GridLayout(5, 0, 10, 10));
    controls.add(projectControls);

    JPanel layerControls = new JPanel();
    layerControls.setLayout(new GridLayout(3, 0, 10, 10));
    controls.add(layerControls);


    this.nLButton = new JButton("Add New Layer");
    layerControls.add(this.nLButton);
    this.aITLButton = new JButton("Add Image to Current Layer");
    layerControls.add(aITLButton);
    this.sFButton = new JButton("Set Filter on Current Layer");
    layerControls.add(this.sFButton);
    this.nPButton = new JButton("New Project");
    projectControls.add(this.nPButton);
    this.lPButton = new JButton("Load Project");
    projectControls.add(this.lPButton);
    this.sPButton = new JButton("Save Project");
    projectControls.add(this.sPButton);
    this.sIButton = new JButton("Save Image");
    projectControls.add(this.sIButton);
    this.eButton = new JButton("Exit");
    projectControls.add(this.eButton);

    //initializes components used in main panel
    this.imageDisplay = new JPanel();
    this.imageLabel = new JLabel();
    this.radioPanel = new JPanel();

    this.setVisible(true);
  }

  /**
   * Allows for controller features to added to the GUI. Creates the bridge between user giving the
   * view input and the controller acting on those inputs.
   * @param features the class that will be able to run the desired features
   */
  public void addFeatures(Features features) {
    // Tells the controller to create a new project
    this.initialNewProjectButton.addActionListener(evt ->
              features.newProject(this.getDesiredWidth(), this.getDesiredHeight()));

    // Tells the controller when to create a new project
    this.nPButton.addActionListener(evt ->
            features.newProject(this.getDesiredWidth(), this.getDesiredHeight()));

    // Tells the controller when to load a new project
    this.initialLoadNewProjectButton.addActionListener(evt ->
            features.loadProject(this.getNameToLoad()));

    // Tells the controller when to load a new project
    this.lPButton.addActionListener(evt ->
            features.loadProject(this.getNameToLoad()));

    // Tells the controller to add an image to the current Layer
    this.aITLButton.addActionListener(evt ->
            features.addImageToLayer(this.model.getActiveLayerName(), this.getDesiredImage(),
                    this.getDesiredX(), this.getDesiredY()));

    // Tells the controller which filter to add to the current Layer
    this.sFButton.addActionListener(evt ->
            features.setFilter(this.getDesiredFilter(), this.model.getActiveLayerName()));

    // tells the controller to save the project to a file
    this.sPButton.addActionListener(evt ->
            features.saveProject(this.getNameToSaveProject()));

    // tells the controller to save the current project as an image
    this.sIButton.addActionListener(evt ->
            features.saveImage(this.getDesiredImageName()));

    // tells the controller to add a Layer
    this.nLButton.addActionListener(evt ->
            features.addLayer(this.getDesiredLayerName()));

    // Tells the controller when to quit
    this.eButton.addActionListener(evt ->
            features.exit());
  }

  /**
   * Prompts the user with a file selection screen asking
   * them to select the desired project to load.
   * @return the absolute path of the project so the controller can load it
   */
  private String getNameToLoad() {
    String s = "";
    while (s.equalsIgnoreCase("")) {
      final JFileChooser fileChooser = new JFileChooser(".");
      fileChooser.setFileFilter(new FileNameExtensionFilter("Collages Only",
              "collage"));
      fileChooser.setDialogTitle("Select a project to load.");
      if (fileChooser.showOpenDialog(ImageProjectGUIViewImpl.this)
              == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        s = f.getAbsolutePath();
      }
    }
    return s;
  }

  /**
   * Prompts the user with a file selection screen asking
   * them to select the desired image to add to this project.
   * @return the absolute path of the image so the controller can add it to the model
   */
  private String getDesiredImage() {
    String s = "";
    while (s.equalsIgnoreCase("")) {
      final JFileChooser fileChooser = new JFileChooser(".");
      fileChooser.setFileFilter(new FileNameExtensionFilter("PPM Images", "ppm"));
      fileChooser.setDialogTitle("Select an image to add.");
      if (fileChooser.showOpenDialog(ImageProjectGUIViewImpl.this)
              == JFileChooser.APPROVE_OPTION) {
        File f = fileChooser.getSelectedFile();
        s = f.getAbsolutePath();
      }
    }
    return s;
  }

  /**
   * Prompts the user for the x location it should place an image.
   * @return an integer representing the desired x location of the image
   */
  private int getDesiredX() {
    return this.intInput("Please enter desired x" +
            " location of the image's top left.");
  }

  /**
   * Prompts the user for the y location it should place an image.
   * @return an integer representing the desired y location of the image
   */
  private int getDesiredY() {
    return this.intInput("Please enter desired y" +
            " location of the image's top left.");
  }

  /**
   * Prompts the user for the desired width of the project.
   * @return an integer representing the width of the project to create
   */
  private int getDesiredWidth() {
    return this.intInput("Please enter" +
            " project width as a number of pixels.");
  }

  /**
   * Prompts the user for the desired height of the project.
   * @return an integer representing the height of the project to create
   */
  private int getDesiredHeight() {
    return this.intInput("Please enter" +
            " project height as a number of pixels.");
  }

  /**
   * Creates and displays a prompt and collects an integer from the user.
   * @return the integer collected from the user
   */
  private int intInput(String message) {
    int h = -2;
    while (h == -2) {
      h = Integer.parseInt(JOptionPane.showInputDialog(message));
    }
    return h;
  }

  /**
   * Prompts the user for the name they want to give to the new layer they are creating.
   * @return the chosen name of the new layer
   */
  private String getDesiredLayerName() {
    return this.stringInput("Please enter name of Layer.");
  }

  /**
   * Prompts the user for the name they want to give to the new image they are creating.
   * @return the chosen name of the new image
   */
  private String getDesiredImageName() {
    return this.stringInput("Please enter name to save image as.");
  }

  /**
   * Prompts the user for the name of the filter they wish to apply.
   * @return the name of the filter
   */
  private String getDesiredFilter() {
    return this.stringInput("Please one of the following valid filters.\n" +
            "normal, red-component, green-component, blue-component, brighten-value," +
            " brighten-intensity, brighten-luma, darken-value, darken-intensity, darken-luma, " +
            "difference, multiply, screen");
  }

  /**
   * Prompts the user for the name they want to give to the new project they are saving.
   * @return the name of the project
   */
  private String getNameToSaveProject() {
    return this.stringInput("Please enter name to save project as.");
  }

  /**
   * Creates and displays a prompt and collects a String from the user.
   * @return the String collected from the user
   */
  private String stringInput(String message) {
    String s = "";
    while (s.equalsIgnoreCase("")) {
      s = JOptionPane.showInputDialog(message);
    }
    return s;
  }

  /**
   * The method that displays the main screen of the GUI, including the image, the layer
   * information, and all the command buttons. It is used by the controller to update the view
   * whenever the model is changed.
   */
  public void runMainGUI() {
    this.resetMainPanel();
    this.setSize(1200, 900);

    // Creates a panel for displaying an image
    this.imageDisplay = new JPanel();
    this.imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    this.imageDisplay.setBorder(BorderFactory.createTitledBorder(this.model.getName()));
    this.mainPanel.add(imageDisplay);

    // adds image to that panel
    this.imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(this.imageLabel);
    this.imageLabel.setIcon(new ImageIcon(this.model.getImageRepresentation()));
    imageScrollPane.setPreferredSize(new Dimension(900, 500));
    this.imageDisplay.add(imageScrollPane);
    //----------------------------------------------------------------------------------------------

    // radio buttons representing Layers
    this.radioPanel = new JPanel();
    this.radioPanel.setPreferredSize(new Dimension(300, 500));
    this.radioPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    this.radioPanel.setLayout(new BoxLayout(this.radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[this.model.getNumberOfLayers()];

    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      this.model.setActiveLayer(i);
      radioButtons[i] = new JRadioButton(this.model.getActiveLayerName());
      radioButtons[i].setAlignmentX(JRadioButton.CENTER_ALIGNMENT);
      radioButtons[i].setActionCommand("RB" + i);
      radioButtons[i].addActionListener(this);
      rGroup1.add(radioButtons[i]);

      this.radioPanel.add(radioButtons[i]);

    }
    radioButtons[this.currentLayerIndex].doClick();

    this.mainPanel.add(this.radioPanel);
    //----------------------------------------------------------------------------------------------

    this.mainBottomPanel.add(controls);
    this.repaint();
    this.revalidate();

  }

  /**
   * Resets the main panel of the GUI so that no new information is added on top of old information.
   */
  private void resetMainPanel() {
    this.mainPanel.remove(this.introScreen);
    this.mainPanel.remove(this.imageDisplay);
    this.mainPanel.remove(this.imageLabel);
    this.mainPanel.remove(this.radioPanel);
    this.repaint();
    this.revalidate();
  }


  /**
   * The action listener that primarily deals with the layer radio buttons. It determines which
   * button was pressed tells the controller to make that one the active layer.
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {  // MAKE THE CONTROLLER DO TEH SETTING
    if (e.getActionCommand().substring(0, 2).equalsIgnoreCase("RB")) {
      int index = Integer.parseInt(e.getActionCommand().substring(2, 3));
      this.model.setActiveLayer(index);
      this.currentLayerIndex = index;
    }
  }

  /**
   * A method used by the controller to inform the GUI when bad input has been received and an error
   * message should be shown to the user.
   * @param ex the message given by the error
   */
  public void throwBadInput(String ex) {
    JOptionPane.showMessageDialog(this, ex,
            "Bad Parameters", JOptionPane.ERROR_MESSAGE);
  }
}
