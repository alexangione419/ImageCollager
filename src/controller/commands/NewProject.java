package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;


/**
 * A command object that calls the given {@code ImageProject}'s createNewProject method with the
 * given input from the passed {@code Scanner}.
 */
public final class NewProject extends ACommand {

  Scanner sc;

  /**
   * Constructs a new {@code NewProject}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public NewProject(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {
    if (!this.sc.hasNextInt()) {
      try {
        this.view.renderMessage("Invalid argument, try again.\n");
      } catch (IOException e) {
        //ignore
      }
    }
    int width = this.sc.nextInt();

    if (!this.sc.hasNextInt()) {
      try {
        this.view.renderMessage("Invalid argument, try again.\n");
      } catch (IOException e) {
        //ignore
      }
    }

    int height = this.sc.nextInt();

    try {
      this.model.createNewProject(width, height);
      try {
        this.view.renderMessage("Created new project with canvas size of "
            + width + "x" + height + ".\n");
      } catch (IOException e) {
        //ignore
      }
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage("Invalid argument, try again.\n");
      } catch (IOException io) {
        //ignore
      }
    }
  }
}
