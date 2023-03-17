package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import model.ImageProject;
import model.PPMProject;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * A controller for manipulating a {@code PPMProject}.
 */
public class PPMProjectController implements ImageProjectController {

  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;
  private final Map<String, Runnable> commands;

  /**
   * Constructs a new {@code PPMProjectController}.
   * @param model the model to control
   * @param view the view of the model
   * @param output the Readable from which to read input from
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public PPMProjectController(ImageProject model, ImageProjectView view, Readable output)
    throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("PPMProject for the PPMProjectController "
          + "cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException(
          "PPMProjectTextView for the PPMProjectController cannot be null.");
    }

    if (output == null) {
      throw new IllegalArgumentException("Readable for the PPMProjectController cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.sc = new Scanner(output);
    this.commands = new HashMap<String, Runnable>();
  }


  /**
   * Adds in all the possible commands that the controller supports
   */
  private void initCommands() {

  }

  @Override
  public void start() throws IllegalStateException {
    try {
      System.out.println(this.view.currentCanvas());
    } catch (Exception e) {
      //pass
    }
  }
}
