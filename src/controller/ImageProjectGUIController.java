package controller;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ImageProject;
import view.ImageProjectGUIViewImpl;

public class ImageProjectGUIController implements ImageProjectController, Features {
  boolean running;
  private final ImageProject model;
  private ImageProjectGUIViewImpl view;
  private final Scanner sc;


  /**
   * Constructs a new {@code ImageProjectGUIController}.
   *
   * @param model the model to control
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ImageProjectGUIController(ImageProject model, Readable input)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Project argument cannot be null.");
    }

    if (input == null) {
      throw new IllegalArgumentException("Readable for the controller cannot be null.");
    }

    this.model = model;
    this.sc = new Scanner(input);

  }

  public void start(ImageProjectGUIViewImpl view) throws IllegalStateException {
    if (view == null) {
      throw new IllegalArgumentException("View argument cannot be null.");
    }
    this.view = view;
    this.view.addFeatures(this);


  }

  @Override
  public void addImageToLayer(String layer, String image, int x, int y) {
    this.model.addImageToLayer(layer, image, x, y);
  }

  @Override
  public void addLayer(String layerName) {
    this.model.addLayer(layerName);
  }

  @Override
  public void loadProject(String filepath) {
    this.model.loadProject(filepath);
  }

  @Override
  public void saveProject(String name) {
    try {
      this.model.saveProject(name);
    } catch (IOException io) {
      // welp
    }
  }

  @Override
  public void saveImage(String name) {
    try {
      this.model.saveImage(name);
    } catch (IOException io) {
      // welp
    }
  }

  @Override
  public void newProject(int width, int height) {
    this.model.createNewProject(width, height);
    this.view.runMainGUI();
  }

  @Override
  public void setFilter(String filterName, String layerName) {
    this.model.setFilter(filterName, layerName);
  }

  @Override
  public void exit() {
    System.exit(0);
  }
}
