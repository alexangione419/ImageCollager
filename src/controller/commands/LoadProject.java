package controller.commands;

import java.io.IOException;
import model.ImageProject;

/**
 * A command that loads in a project to the given {@code ImageProject}.
 */
public class LoadProject implements Command {

  private final String projectPath;

  /**
   * Constructs a new {@code LoadProject} command object.
   * @param pPath the project file path
   */
  public LoadProject(String pPath) {
    this.projectPath = pPath;
  }

  @Override
  public void run(ImageProject p) {
    p.loadProject(this.projectPath);
  }
}
