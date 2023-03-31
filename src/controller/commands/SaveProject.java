package controller.commands;

import controller.ImageProjectController;
import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that saves the currently opened {@code ImageProject} to a .collage file.
 */
public final class SaveProject extends ACommand {

  private Scanner sc;
  private ImageProjectController controller;

  /**
   * Constructs a new {@code SaveProject}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public SaveProject(ImageProject model, ImageProjectView view,
      ImageProjectController controller, Scanner sc) {
    super(model, view);
    this.sc = sc;
    this.controller = controller;
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
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String fileName = this.sc.next();

    try {
      try {
        this.controller.saveProject(fileName);
        this.view.renderMessage("Project was saved to as " + fileName + ".collage.\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The name for the file is invalid. It must not contain any periods."
            + " Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
