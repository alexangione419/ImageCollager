package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

import model.ImageProjectState;


/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProcessorGUIView extends JFrame implements ImageProjectView, ActionListener {

  private ImageProjectState model;
  private JPanel mainPanel;
  private JPanel mainBottomPanel;
  private JScrollPane mainScrollPane;
  private JLabel radioDisplay;

  public ImageProcessorGUIView(ImageProjectState model) {
    super();
    setTitle("Image Processor");
    setSize(1200, 900);
    this.model = model;




    mainPanel = new JPanel();
    mainBottomPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    mainBottomPanel.setLayout(new BoxLayout(mainBottomPanel, BoxLayout.PAGE_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);


    // Creates a panel for displaying an image
    JPanel imageDisplay = new JPanel();
    imageDisplay.setBorder(BorderFactory.createTitledBorder("model name should go here"));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imageDisplay);

    // adds image to that panel
    JLabel imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon("images.ppm"));
    imageScrollPane.setPreferredSize(new Dimension(900, 500));
    imageDisplay.add(imageScrollPane);

    // radio buttons representing Layers
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Layers"));
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    JRadioButton[] radioButtons = new JRadioButton[5];
    //should eventually be number of layers in model

    ButtonGroup rGroup1 = new ButtonGroup();

    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton("Layer " + (i + 1));
      //radioButtons[i].setSelected(false);

      radioButtons[i].setActionCommand("RB" + (i + 1));
      radioButtons[i].addActionListener(this);
      rGroup1.add(radioButtons[i]);

      radioPanel.add(radioButtons[i]);

    }
    radioButtons[0].doClick();
    //radioDisplay = new JLabel("Should say what filter is clicked?");
    //radioPanel.add(radioDisplay);
    mainPanel.add(radioPanel);


  }

  @Override
  public void renderMessage(String message) throws IOException {
    //uhhhh
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }



}
