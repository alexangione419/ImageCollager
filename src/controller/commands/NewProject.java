package controller.commands;

import model.ImageProject;

/**
 * Command for making a new project.
 */
public class NewProject implements Command {
  private final int height;
  private final int width;

  /**
   * Creates a new project command.
   * @param height of the new project
   * @param width of the new project
   */
  public NewProject(int height, int width) {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Invalid project dimensions");
    }
    this.height = height;
    this.width = width;
  }

  @Override
  public void run(ImageProject p) {
    p.createNewProject(this.width, this.height);
  }
}
