package controller;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.ImageProject;
import model.ProjectImpl;
import view.ImageProjectGUIView;
import view.ImageProjectGUIViewImpl;

public class ImageProjectGUIController implements ImageProjectController{
  boolean running;
  private final ImageProject model;
  private final ImageProjectGUIView view;
  private final Scanner sc;


  /**
   * Constructs a new {@code PPMProjectController}.
   *
   * @param model the model to control
   * @param view  the view of the model
   * @param input the Readable from which to read input from
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ImageProjectGUIController(ImageProject model, ImageProjectGUIView view, Readable input)
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
    this.view.setDefaultLookAndFeelDecorated(false);

    this.view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.view.setVisible(true);


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
