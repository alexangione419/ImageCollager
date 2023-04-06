package controller;

import controller.commands.LoadProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectGUIViewImpl;

/**
 * An implementation of {@code ImageProjectController} that allows users to manipulate an
 * {@code ImageProject}. This controller will use an {@code ImageProjectGUIViewImpl}.
 */
public class ImageProjectGUIController implements Features {
  private final ImageProject model;
  private ImageProjectGUIViewImpl view;


  /**
   * Constructs a new {@code ImageProjectGUIController}.
   *
   * @param model the model to control
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ImageProjectGUIController(ImageProject model)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Project argument cannot be null.");
    }
    this.model = model;
  }

  /**
   * Allows the controller to initialize the GUI view.
   * @param view the GUI to run
   * @throws IllegalStateException if the provided view is null
   */
  public void start(ImageProjectGUIViewImpl view) throws IllegalStateException {
    if (view == null) {
      throw new IllegalArgumentException("View argument cannot be null.");
    }
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void addImageToLayer(String layer, String image, int x, int y) {
    try {
      this.model.addImageToLayer(layer, image, x, y);
      this.view.runMainGUI();
    } catch (IllegalArgumentException a) {
      this.view.throwBadInput(a.getMessage());
    }
  }

  @Override
  public void addLayer(String layerName) {
    try {
      this.model.addLayer(layerName);
      this.view.runMainGUI();
    } catch (IllegalArgumentException a) {
      this.view.throwBadInput(a.getMessage());
    }

  }

  @Override
  public void loadProject(String filepath) throws IllegalArgumentException {
    new LoadProject(this.model).load(filepath);
    this.view.runMainGUI();
  }

  @Override
  public void saveProject(String name){
    try {
      new SaveProject(this.model).save(name);
    } catch (IOException io) {
      // welp
    }
  }

  @Override
  public void saveImage(String name) {
    try {
      new SaveImage(this.model).save(name);
    } catch (IOException io) {
      // welp
    } catch (IllegalArgumentException a) {
      this.view.throwBadInput(a.getMessage());
    }
  }

  @Override
  public void newProject(int width, int height) {
    try {
      this.model.createNewProject(width, height);
      this.view.runMainGUI();
    } catch (IllegalArgumentException a) {
      this.view.throwBadInput(a.getMessage());
    }
  }

  @Override
  public void setFilter(String filterName, String layerName) {
    try {
      this.model.setFilter(filterName, layerName);
      this.view.runMainGUI();
    } catch (IllegalArgumentException a) {
      this.view.throwBadInput(a.getMessage());
    }
  }

  @Override
  public void exit() {
    System.exit(0);
  }
}
