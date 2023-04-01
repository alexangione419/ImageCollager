package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that sets the filter of a given layer found on the given {@code ImageProject}.
 */
public final class SetFilter extends ACommand {

  private Scanner sc;

  /**
   * Constructs a new {@code SetFilter}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public SetFilter(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {

    // Make sure there is input to read
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String filterName = this.sc.next();

    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String layerName = this.sc.next();

    try {
      try {
        this.model.setFilter(filterName, layerName);
        this.view.renderMessage("Set the " + filterName + " filter to Layer " + layerName + ".\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("Invalid arguments. Either filter or layer does not exist. "
            + "Try again:\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }
}
