package controller.commands;

import model.ImageProject;
import view.ImageProjectView;

/**
 * An abstract command object that contains a {@code ImageProject} and a {@code ImageProjectView}.
 */
public abstract class ACommand implements Command {

  protected final ImageProject model;
  protected final ImageProjectView view;

  /**
   * Constructs a new {@code ACommand}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   */
  public ACommand(ImageProject model, ImageProjectView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public abstract void run();
}
