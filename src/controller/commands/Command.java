package controller.commands;

/**
 * An interface for Commands to be used by the {@code ImageProjectController}.
 */
public interface Command {

  /**
   * The run method allows each command object to collect input from the user, then call a
   * corresponding method in the model.
   */
  void run();

}
