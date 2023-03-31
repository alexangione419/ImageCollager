package controller;

import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.commands.AddImageToLayer;
import model.ImageProject;
import view.ImageProjectGUIViewImpl;
import view.ImageProjectView;

public class ImageProjectGUIController implements ImageProjectController, Features{
  boolean running;
  private final ImageProject model;
  private final ImageProjectGUIViewImpl view;
  private final Scanner sc;


  /**
   * Constructs a new {@code PPMProjectController}.
   *
   * @param model the model to control
   * @param view  the view of the model
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ImageProjectGUIController(ImageProject model, ImageProjectGUIViewImpl view, Readable input)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Project argument cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("View argument cannot be null.");
    }

    if (input == null) {
      throw new IllegalArgumentException("Readable for the controller cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.sc = new Scanner(input);

  }

  @Override
  public void start() throws IllegalStateException {
    view.addFeatures(this);

    ImageProjectGUIViewImpl.setDefaultLookAndFeelDecorated(false);
    ImageProjectGUIViewImpl frame = new ImageProjectGUIViewImpl(this.model, new StringBuilder());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }


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
    this.view.resetGUI();
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
