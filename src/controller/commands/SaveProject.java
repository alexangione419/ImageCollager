package controller.commands;

import java.io.IOException;

import model.ImageProject;

public class SaveProject implements Command{
  private final String name;

  public SaveProject(String name) {
    if(name.isEmpty()) {
      throw new IllegalArgumentException("Name of project cannot be empty");
    }
    this.name = name;
  }
  @Override
  public void run(ImageProject p) throws IOException {
    try {
      p.saveProject(this.name);
    } catch (IOException io) {
      throw new IOException(io);
    }
  }
}
