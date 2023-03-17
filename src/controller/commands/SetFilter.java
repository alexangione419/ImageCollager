package controller.commands;

import model.ImageProject;

public class SetFilter implements Command {
  private final String layerName;
  private final String filter;

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
