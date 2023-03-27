package newcontroller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that adds a new layer to the given model.
 */
public final class AddLayer extends ACommand {

  Scanner sc;

  public AddLayer(ImageProject model, ImageProjectView view, Scanner sc) {
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
      throw new IllegalStateException("No input detected");
    }

    String layerName = this.sc.next();

    try {
      this.model.addLayer(layerName);
      try {
        this.view.renderMessage("Layer " + layerName + " was created.\n");
      } catch (IOException e) {
        //ignore
      }
    } catch (IllegalArgumentException e) {
      try {
        this.view.renderMessage("Invalid layer name. Layer name cannot be whitespace.\n"
            + "Try again:\n");
        this.run();
      } catch (IOException io) {
        //ignore
      }
    }
  }
}
