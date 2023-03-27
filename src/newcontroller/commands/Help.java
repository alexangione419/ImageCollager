package newcontroller.commands;

import java.io.IOException;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that renders a message containing all the available commands that the given
 * {@code ImageProjectController} supports.
 */
public final class Help extends ACommand {

  /**
   * Constructs a new {@code Help}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   */
  public Help(ImageProject model, ImageProjectView view) {
    super(model, view);
  }

  @Override
  public void run() {
    try {
      this.view.renderMessage("Here are all the available commands:\n"
          + "new-project: starts a new project.\n"
          + "save-project: save the current project.\n"
          + "load-project: loads a project found at the given file path.\n"
          + "add-layer: adds a new layer to the current project.\n"
          + "add-image-to-layer: adds an image at the given file path to the layer on the "
          + "current project that matches the same name.\n"
          + "set-filter: sets a filter for a specified layer.\n"
          + "save-image: saves the image present on the canvas.\n"
          + "filter-list: lists out all the supported filters.\n"
          + "help: lists out all the available commands.\n"
          + "?: lists out all the available commands.\n"
      );
    } catch (IOException e) {
      //pass
    }
  }
}
