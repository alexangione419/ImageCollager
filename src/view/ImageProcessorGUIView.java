package view;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import model.ImageProject;

/**
 * Represents a Generate User Interface for interacting with Image Processing Software.
 */
public class ImageProcessorGUIView extends JFrame {

  private JPanel mainPanel;
  private JScrollPane mainScrollPane;

  public ImageProcessorGUIView() {
    super();
    setTitle("Image Processor");
    setSize(800, 800);

    mainPanel = new JPanel();

    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // displays an image
    //show an image with a scrollbar
    JScrollPane imageScrollPage = new JScrollPane();
    JLabel image = new JLabel();
    image.setIcon(new ImageIcon("./src/view/images.jpg"));




  }
}
