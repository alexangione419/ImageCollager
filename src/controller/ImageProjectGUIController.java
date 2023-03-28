package controller;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ImageProject;
import view.ImageProjectGUIViewImpl;
import view.ImageProjectView;

public class ImageProjectGUIController implements ImageProjectController{
  boolean running;
  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;


  /**
   * Constructs a new {@code PPMProjectController}.
   *
   * @param model the model to control
   * @param view  the view of the model
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ImageProjectGUIController(ImageProject model, ImageProjectView view, Readable input)
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
}
