package controller.commands;

import model.ImageProject;

/**
 * A command for setting a filter to a specified {@code Layer} on a {@code ImageProject}.
 */
public class SetFilter implements Command {

  private final String layerName;
  private final String filter;

  /**
   * Constructs a new {@code SetFilter} command object.
   * @param layerName the name of the Layer to set to
   * @param filter the name of the filter to add
   */
  public SetFilter(String layerName, String filter) {
    if (layerName.isEmpty() || filter.isEmpty()) {
      throw new IllegalArgumentException("Arguments cannot be empty");
    }
    this.layerName = layerName;
    this.filter = filter;
  }

  @Override
  public void run(ImageProject p) {
    p.setFilter(filter, layerName);
  }
}
