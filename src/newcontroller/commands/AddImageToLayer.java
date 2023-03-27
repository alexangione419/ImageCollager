package newcontroller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that adds an image from a file to a given layer on the given model.
 */
public final class AddImageToLayer extends ACommand {

  Scanner sc;

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
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String layer = this.sc.next();

    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String filePath = this.sc.next();

    if (!sc.hasNextInt()) {
      throw new IllegalStateException("No input detected.");
    }

    int x = this.sc.nextInt();

    if (!sc.hasNextInt()) {
      throw new IllegalStateException("No input detected.");
    }

    int y = this.sc.nextInt();

    try {
      try {
        this.model.addImageToLayer(layer, filePath, x, y);
        this.view.renderMessage("Added " + filePath + " to Layer " + layer +
            " at (" + x + ", " + y + ").\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid arguments. Try again:\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
