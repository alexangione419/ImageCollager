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
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProjectGUIViewImpl extends JFrame implements ActionListener {

  private ImageProjectState model;

  int currentLayerIndex;

  private JPanel mainPanel;
  private JPanel mainBottomPanel;
  private JPanel introScreen;
  private JPanel controls;
  private JPanel imageDisplay;
  private JLabel imageLabel;
  private JScrollPane imageScrollPane;
  private JPanel radioPanel;
  private final JButton initialNewProjectButton;
  private final JButton initialLoadNewProjectButton;
  private JButton nPButton;
  private JButton aITLButton;
  private JButton nLButton;
  private JButton sFButton;
  private JButton lPButton;
  private JButton sPButton;
  private JButton eButton;
  private JButton sIButton;


  /**
   * Constructs a new {@code ImageProjectGUIViewImpl} with the given
   * {@code ImageProjectState} model.
   * @param model
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
    //this.introScreen.add(dim);
    this.introScreen.add(initialButtons);

    this.mainPanel.add(this.introScreen);


    //prepares buttons for mainGUI use
    // does not use buttons until runMainGUI is run
    // Panel representing important commands
    this.controls = new JPanel();

    // divides buttons into two categories to make them look nicer in GUI
    JPanel layerControls = new JPanel();
    layerControls.setLayout(new GridLayout(3, 0, 10, 10));
    controls.add(layerControls);

    JPanel projectControls = new JPanel();
    projectControls.setLayout(new GridLayout(5, 0, 10, 10));
    controls.add(projectControls);

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

  private int getDesiredX() {
    return this.intInput("Please enter desired x" +
            " location of the image's top left.");
  }

  private int getDesiredY() {
    return this.intInput("Please enter desired y" +
            " location of the image's top left.");
  }

  private int getDesiredWidth() {
    return this.intInput("Please enter" +
            " project width as a number of pixels.");
  }

  private int getDesiredHeight() {
    return this.intInput("Please enter" +
            " project height as a number of pixels.");
  }

  private int intInput(String message) {
    int h = -2;
    while (h == -2) {
      h = Integer.parseInt(JOptionPane.showInputDialog(message));
    }
    return h;
  }

  private String getDesiredLayerName() {
    return this.stringInput("Please enter name of Layer.");
  }

  private String getDesiredImageName() {
    return this.stringInput("Please enter name to save image as.");
  }

  private String getDesiredFilter() {
    return this.stringInput("Please one of the following valid filters.\n" +
            "normal, red-component, green-component, blue-component, brighten-value," +
            " brighten-intensity, brighten-luma, darken-value, darken-intensity, darken-luma, " +
            "difference, multiply, screen");
  }

  private String getNameToSaveProject() {
    return this.stringInput("Please enter name to save project as.");
  }

  private String stringInput(String message) {
    String s = "";
    while (s.equalsIgnoreCase("")) {
      s = JOptionPane.showInputDialog(message);
    }
    return s;
  }

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
    this.imageScrollPane = new JScrollPane(this.imageLabel);
    this.imageLabel.setIcon(new ImageIcon(this.model.getImageRepresentation()));
    this.imageScrollPane.setPreferredSize(new Dimension(900, 500));
    this.imageDisplay.add(this.imageScrollPane);
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

  private void resetMainPanel() {
    this.mainPanel.remove(this.introScreen);
    this.mainPanel.remove(this.imageDisplay);
    this.mainPanel.remove(this.imageLabel);
    this.mainPanel.remove(this.radioPanel);
    this.repaint();
    this.revalidate();
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().substring(0, 2).equalsIgnoreCase("RB")) {
      int index = Integer.parseInt(e.getActionCommand().substring(2, 3));
      this.model.setActiveLayer(index);
      this.currentLayerIndex = index;
    }
  }


  public void throwBadInput(Exception a) {
    JOptionPane.showMessageDialog(this, a.getMessage(),
            "Bad Parameters", JOptionPane.ERROR_MESSAGE);
  }
}
