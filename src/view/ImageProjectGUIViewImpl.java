package view;

import controller.Features;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import model.ImageProjectState;


/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProjectGUIViewImpl extends JFrame {

  private ImageProjectState model;

  // Variables for GUI
  private JPanel mainPanel;
  private JPanel mainBottomPanel;
  private JPanel introScreen;
  private JPanel controls;
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



  public ImageProjectGUIViewImpl(ImageProjectState model) {
    super();
    this.model = model;

    this.setTitle("Image Processor");
    this.setSize(800, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    //Creates two panels to be top and bottom section
    this.mainPanel = new JPanel();
    this.mainBottomPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.LINE_AXIS));
    this.mainPanel.setPreferredSize(new Dimension(1200, 500));
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




    this.setVisible(true);


  }

  public void addFeatures(Features features) {
    this.initialNewProjectButton.addActionListener(evt ->
            features.newProject(this.getDesiredWidth(), this.getDesiredHeight()));

    this.initialLoadNewProjectButton.addActionListener(evt ->
            features.loadProject(this.getNameToLoad()));

    this.eButton.addActionListener(evt ->
            features.exit());
  }

  private int getDesiredWidth() {
    int w = 0;
    while (w == 0) {
      w = Integer.parseInt(JOptionPane.showInputDialog("Please enter numerical representation of width."));
    }
    return w;
  }

  private int getDesiredHeight() {
    int h = 0;
    while (h == 0) {
      h = Integer.parseInt(JOptionPane.showInputDialog("Please enter numerical representation of height."));
    }
    return h;
  }

  private String getNameToLoad() {
    String s = "";
    while (s.equalsIgnoreCase("")) {
      s = JOptionPane.showInputDialog("Please enter name of project to load.");
    }
    return s;
  }


  public void runMainGUI() {
    this.mainPanel.remove(this.introScreen);
    this.setSize(1200, 900);

    // Creates a panel for displaying an image
    JPanel imageDisplay = new JPanel();
    imageDisplay.setBorder(BorderFactory.createTitledBorder(this.model.getName()));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    this.mainPanel.add(imageDisplay);

    // adds image to that panel
    JLabel imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(this.model.getImageRepresentation()));
    imageScrollPane.setPreferredSize(new Dimension(900, 500));
    imageDisplay.add(imageScrollPane);
    //----------------------------------------------------------------------------------------------

    // radio buttons representing Layers
    JPanel radioPanel = new JPanel();
    radioPanel.setPreferredSize(new Dimension(300, 500));
    radioPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[this.model.getNumberOfLayers()];
    //should eventually be number of layers in model

    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton("Layer " + (i + 1)); // MAKE IT THE NAME OF ACTIVE LAYER
      radioButtons[i].setAlignmentX(JRadioButton.CENTER_ALIGNMENT);

      radioButtons[i].setActionCommand("RB" + (i + 1));
      rGroup1.add(radioButtons[i]);

      radioPanel.add(radioButtons[i]);

    }
    radioButtons[0].doClick();
    //radioDisplay = new JLabel("Should say what filter is clicked?");
    //radioPanel.add(radioDisplay);
    mainPanel.add(radioPanel);
    //----------------------------------------------------------------------------------------------


    mainBottomPanel.add(controls);


  }


}
