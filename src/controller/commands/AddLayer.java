package controller.commands;

import model.ImageProject;

/**
 * A command that adds a layer to the given {@code ImageProject}.
 */
public class AddLayer implements Command {

  private final String name;

  /**
   * Construct a new {@code AddLayer} command object.
   *
   * @param name the name to give to the new layer
   */
  public AddLayer(String name) {
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name of Layer cannot be empty");
    }
    this.name = name;
  }

  @Override
  public void run(ImageProject p) {
    p.addLayer(this.name);
  }
}
