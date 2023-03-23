package view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import model.ImageProjectState;


/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProcessorGUIView extends JFrame implements ImageProjectView{

  private ImageProjectState model;
  private JPanel mainPanel;

  public ImageProcessorGUIView(ImageProjectState model) {
    super();
    setTitle("Image Processor");
    setSize(800, 800);
    this.model = model;


    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
//    mainScrollPane = new JScrollPane(mainPanel);
//    add(mainScrollPane);


    // displays an image
    JPanel imageDisplay = new JPanel();
    imageDisplay.setBorder(BorderFactory.createTitledBorder("model name should go here"));
    imageDisplay.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imageDisplay);

    JLabel image = new JLabel();
    JScrollPane imageScrollPage = new JScrollPane(image);

    image.setIcon(new ImageIcon("./src/view/images.jpg"));

    imageScrollPage.setPreferredSize(new Dimension(200, 200));
    imageDisplay.add(image);







  }

  @Override
  public void renderMessage(String message) throws IOException {
    //uhhhh
  }
}
