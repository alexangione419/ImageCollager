package controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that adds an image from a file to a given layer on the given model.
 */
public final class AddImageToLayer extends ACommand {

  private Scanner sc;

  /**
   * Constructs a new {@code AddImageToLayer}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public AddImageToLayer(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {
    if (!this.model.hasOpenProject()) {
      try {
        this.view.renderMessage("Please create or open a new project to use that command.\n");
      } catch (IOException e) {
        //ignore
      }
    }

    // Make sure there is input to read
    if (!this.sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String layer = this.sc.next();

    if (!this.sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String filePath = this.sc.next();

    int[] coords = new int[2];

    for (int i = 0; i < coords.length; i++) {
      if (!this.sc.hasNext()) {
        throw new IllegalStateException("No input detected.");
      }

      try {
        coords[i] = this.sc.nextInt();
      }

      catch (InputMismatchException e) {
        try {
          this.view.renderMessage("Invalid argument, try again.\n");
          this.sc.next();
          return;
        }
        catch (IOException io) {
          //ignore
        }
      }
    }

    try {
      try {
        this.model.addImageToLayer(layer, filePath, coords[0], coords[1]);
        this.view.renderMessage("Added " + filePath + " to Layer " + layer
            + " at (" + coords[0] + ", " + coords[1] + ").\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid arguments, try again.\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
