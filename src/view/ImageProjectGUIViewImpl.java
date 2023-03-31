package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
  private JPanel controls;
  private final JButton initialNewProjectButton;

  private int desiredWidthForDisplay;
  private int desiredHeightForDisplay;


  public ImageProjectGUIViewImpl(ImageProjectState model, Appendable toSend) {
    super();
    setTitle("Image Processor");
    setSize(1200, 900);
    this.model = model;

    //Creates two panels to be top and bottom section
    this.mainPanel = new JPanel();
    this.mainBottomPanel = new JPanel();
    this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.LINE_AXIS));
    this.mainPanel.setPreferredSize(new Dimension(1200, 500));
    this.mainBottomPanel.setLayout(new BoxLayout(this.mainBottomPanel, BoxLayout.PAGE_AXIS));

    // adds top panel above bottom section
    this.mainBottomPanel.add(this.mainPanel);
    this.add(mainBottomPanel);

    //Creates a button to make the user create a new project
    this.initialNewProjectButton = new JButton("Create new project.");
    this.initialNewProjectButton.setActionCommand("createNewProject");
    this.mainPanel.add(initialNewProjectButton);

    // Waits to create rest of GUI until view can see model has an open project
    if (this.model.hasOpenProject()) {
      this.mainPanel.remove(initialNewProjectButton);

      //----------------------------------------------------------------------------------------------

      // Creates a panel for displaying an image
      JPanel imageDisplay = new JPanel();
      imageDisplay.setBorder(BorderFactory.createTitledBorder("shit cum ballz"));
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
      layerControls.setLayout(new BoxLayout(layerControls, BoxLayout.Y_AXIS));
      layerControls.setLayout(new GridLayout(3, 0, 10, 10));
      controls.add(layerControls);

      JPanel projectControls = new JPanel();
      projectControls.setLayout(new BoxLayout(projectControls, BoxLayout.Y_AXIS));
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
      desiredWidthForDisplay = 0;
      desiredHeightForDisplay = 0;

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

  public void addFeatures(Features features) {
    System.out.println("This better happen");
    this.initialNewProjectButton.addActionListener(evt ->
            features.newProject(this.desiredWidthForDisplay, this.desiredHeightForDisplay));
    
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "createNewProject":
        System.out.println("There is no universe this doesn't print");
        this.desiredWidthForDisplay =
                Integer.parseInt(JOptionPane.showInputDialog(
                        "Enter the numerical width of your new project"));
        this.desiredHeightForDisplay =
                Integer.parseInt(JOptionPane.showInputDialog(
                        "Enter the numerical height of your new project"));

    }
  }

  public void resetGUI() {
    System.out.println("FUCK FUCK FUCK");
  }
}
