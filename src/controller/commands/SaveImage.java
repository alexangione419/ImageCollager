package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that saves the image produced by the given {@code ImageProject}.
 */
public final class SaveImage extends ACommand {

  Scanner sc;

  /**
   * Constructs a new {@code SaveImage}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public SaveImage(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {
    if (this.model.hasOpenProject()) {
      try {
        this.view.renderMessage("Please create or open a new project to use that command.\n");
      } catch (IOException e) {
        //ignore
      }
    }

    // Make sure there is input to read
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String fileName = this.sc.next();

    try {
      try {
        this.model.saveImage(fileName);
        this.view.renderMessage("Image was saved to as " + fileName + ".ppm.\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The name for the file is invalid. It must not contain any periods."
            + " Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
