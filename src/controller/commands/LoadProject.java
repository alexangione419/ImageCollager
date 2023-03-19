package controller.commands;

import java.io.IOException;
import model.ImageProject;

public class LoadProject implements Command {

  private final String projectPath;

  public LoadProject(String pPath) {
    this.projectPath = pPath;
  }

  @Override
  public void run(ImageProject p) {
    p.loadProject(this.projectPath);
  }
}
