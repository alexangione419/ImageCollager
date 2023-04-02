package controller.commands;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;


/**
 * A command object that calls the given {@code ImageProject}'s createNewProject method with the
 * given input from the passed {@code Scanner}.
 */
public final class NewProject extends ACommand {

  private Scanner sc;

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

    int size[] = new int[2];

    for (int i = 0; i < size.length; i++) {
      if (!this.sc.hasNext()) {
        throw new IllegalStateException("No input detected.");
      }

      try {
        size[i] = this.sc.nextInt();
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
      this.model.createNewProject(size[0], size[1]);
      try {
        this.view.renderMessage("Created new project with canvas size of "
            + size[0] + "x" + size[1] + ".\n");
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
