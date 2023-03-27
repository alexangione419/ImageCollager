package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that saves the given {@code ImageProject} into a .collage project file.
 */
public final class LoadProject extends ACommand {

  Scanner sc;

  /**
   * Constructs a new {@code LoadProject}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public LoadProject(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {
    // Make sure there is input to read
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String filePath = this.sc.next();

    try {
      try {
        this.model.loadProject(filePath);
        this.view.renderMessage("Loaded project " + filePath + ".\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The file at " + filePath + " is not a valid project file. "
            + "Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
