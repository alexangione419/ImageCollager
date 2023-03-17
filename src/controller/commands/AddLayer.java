package controller.commands;

import java.io.IOException;

import model.ImageProject;

public class AddLayer implements Command{
  private final String name;

  public AddLayer(String name) {
    if(name.isEmpty()) {
      throw new IllegalArgumentException("Name of Layer cannot be empty");
    }
    this.name = name;
  }
  @Override
  public void run(ImageProject p) {
    p.addLayer(this.name);
  }
}
