package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import controller.Features;
import model.ImageProjectState;


/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProjectGUIViewImpl extends JFrame implements ActionListener {

  private ImageProjectState model;

  // Variables for GUI
  private JPanel mainPanel;
  private JPanel mainBottomPanel;
  private JPanel introScreen;
  private JPanel controls;
  private final JButton initialNewProjectButton;
  private final JButton initialLoadNewProjectButton;

  private int desiredWidthForDisplay;
  private int desiredHeightForDisplay;
  private String filename;
  private JTextArea heightInput;
  private JTextArea widthInput;


  public ImageProjectGUIViewImpl(ImageProjectState model) {
    super();
    this.model = model;

    this.setTitle("Image Processor");
    this.setSize(1200, 900);
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

    intro.setText("To create a new project, please input valid numerical arguments into the text " +
            "boxes below, then press create.");
    this.introScreen.add(intro);

//    this.heightInput = new JTextArea();
//    this.heightInput.setText("200");
//    this.widthInput = new JTextArea();
//    this.widthInput.setText("200");
//    this.desiredWidthForDisplay = Integer.parseInt(this.widthInput.getText());
//    this.desiredHeightForDisplay = Integer.parseInt(this.heightInput.getText());
//    JPanel dim = new JPanel();
//    dim.setLayout(new GridLayout(0, 2, 10, 10));
//    dim.add(heightInput);
//    dim.add(widthInput);

    JPanel initialButtons = new JPanel();
    initialButtons.setLayout(new GridLayout(0, 2, 10, 10));
    //Creates a button to make the user create a new project when first loading in
    this.initialNewProjectButton = new JButton("Create");
    this.initialNewProjectButton.setActionCommand("createNew");
    this.initialNewProjectButton.addActionListener(this);
    //Creates a button to make the user load a project when they first load in
    this.initialLoadNewProjectButton = new JButton("Load");
    initialButtons.add(this.initialNewProjectButton);
    initialButtons.add(this.initialLoadNewProjectButton);

    this.introScreen.add(intro);
    //this.introScreen.add(dim);
    this.introScreen.add(initialButtons);

    this.mainPanel.add(this.introScreen);
    this.setVisible(true);


  }

  public void addFeatures(Features features) {
    this.initialNewProjectButton.addActionListener(evt ->
            features.newProject(this.desiredWidthForDisplay, this.desiredHeightForDisplay));
    this.initialLoadNewProjectButton.addActionListener(evt ->
            features.loadProject("P1.collage"));
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equalsIgnoreCase("createNew")) {
      this.desiredHeightForDisplay = Integer.parseInt(JOptionPane.showInputDialog("Please enter numerical representation of height."));
      this.desiredWidthForDisplay = Integer.parseInt(JOptionPane.showInputDialog("Please enter numerical representation of width."));
    }

  }

  public void runMainGUI() {
    this.mainPanel.remove(this.introScreen);

    // Creates a panel for displaying an image
    JPanel imageDisplay = new JPanel();
    imageDisplay.setBorder(BorderFactory.createTitledBorder("this"));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    this.mainPanel.add(imageDisplay);

    // adds image to that panel
    JLabel imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon("images.jpg"));
    imageScrollPane.setPreferredSize(new Dimension(900, 500));
    imageDisplay.add(imageScrollPane);
    //----------------------------------------------------------------------------------------------

    // radio buttons representing Layers
    JPanel radioPanel = new JPanel();
    radioPanel.setPreferredSize(new Dimension(300, 500));
    radioPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[5];
    //should eventually be number of layers in model

    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton("Layer " + (i + 1));
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

    // Panel representing important commands
    controls = new JPanel();

    // divides buttons into two categories to make them look nicer in GUI
    JPanel layerControls = new JPanel();
    layerControls.setLayout(new GridLayout(3, 0, 10, 10));
    controls.add(layerControls);

    JPanel projectControls = new JPanel();
    projectControls.setLayout(new GridLayout(5, 0, 10, 10));
    controls.add(projectControls);

    JButton nLButton = new JButton("Add New Layer");
    layerControls.add(nLButton);
    JButton aITLButton = new JButton("Add Image to Current Layer");
    layerControls.add(aITLButton);
    JButton sFButton = new JButton("Set Filter on Current Layer");
    layerControls.add(sFButton);
    JButton nPButton = new JButton("New Project");
    projectControls.add(nPButton);
    JButton lPButton = new JButton("Load Project");
    projectControls.add(lPButton);
    JButton sPButton = new JButton("Save Project");
    projectControls.add(sPButton);
    JButton sIButton = new JButton("Save Image");
    projectControls.add(sIButton);
    JButton eButton = new JButton("Exit");
    projectControls.add(eButton);

    mainBottomPanel.add(controls);


  }


}
