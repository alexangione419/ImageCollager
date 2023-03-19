package controller.commands;

import java.io.IOException;
import model.ImageProject;

/**
 * A command for saving project files made from {@code ImageProject}s.
 */
public class SaveProject implements Command {

  private final String name;

  /**
   * Constructs a new {@code SaveProject} command object.
   *
   * @param name the name to give the project file
   */
  public SaveProject(String name) {
    if (name.isEmpty()) {
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
