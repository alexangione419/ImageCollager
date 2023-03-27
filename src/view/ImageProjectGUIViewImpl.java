package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.ImageProjectState;


/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProjectGUIViewImpl extends JFrame implements
    ImageProjectGUIView, ActionListener, ItemListener, ListSelectionListener {

  private ImageProjectState model;
  private String defaultName;
  private String name;
  private Appendable toSend;

  // Variables for GUI
  private JPanel mainPanel;
  private JPanel mainBottomPanel;
  private JScrollPane imageScrollPane;
  private JLabel radioDisplay;
  private JPanel controls;
  private JButton controlOptions[];

  public ImageProjectGUIViewImpl(ImageProjectState model, Appendable toSend) {
    super();
    setTitle("Image Processor");
    setSize(1200, 900);
    this.model = model;
    this.defaultName = "No project currently loaded";

    //Creates two panels to be top and bottom section
    mainPanel = new JPanel();
    mainBottomPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    mainBottomPanel.setLayout(new BoxLayout(mainBottomPanel, BoxLayout.PAGE_AXIS));
    // adds top panel above bottom section
    mainBottomPanel.add(mainPanel);
    add(mainBottomPanel);

    //----------------------------------------------------------------------------------------------

    // Creates a panel for displaying an image
    JPanel imageDisplay = new JPanel();
    imageDisplay.setBorder(BorderFactory.createTitledBorder("model name should go here"));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imageDisplay);

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
      radioButtons[i].addActionListener(this);
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
    // THIS IS BAD -> ASSUMES CONTROLS MODEL HAS
    List<String> controlsAllowed = new ArrayList<String>(Arrays.asList("Create new project",
        "Load project", "Save project", "Save current image", "Exit"));
    controlOptions = new JButton[controlsAllowed.size()];
    for (int i = 0; i < controlsAllowed.size(); i++) {
      controlOptions[i] = new JButton(controlsAllowed.get(i));
      controlOptions[i].addActionListener(this);
      controlOptions[i].setActionCommand(controlsAllowed.get(i));
      controls.add(controlOptions[i]);
    }
    controls.setAlignmentY(JPanel.CENTER_ALIGNMENT);
    mainBottomPanel.add(controls);

  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }


  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
