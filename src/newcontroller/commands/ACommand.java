package newcontroller.commands;

import model.ImageProject;
import view.ImageProjectView;

/**
 * An abstract command object that contains a {@code ImageProject} and a {@code ImageProjectView}.
 */
public abstract class ACommand implements Command {

  protected final ImageProject model;
  protected final ImageProjectView view;

  public ACommand(ImageProject model, ImageProjectView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public abstract void run();
}
