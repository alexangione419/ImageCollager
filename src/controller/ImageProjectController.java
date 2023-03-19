package controller;

/**
 * A controller for {@code ImageProject}s. This interface allows users to
 * manipulate {@code ImageProject}s.
 */
public interface ImageProjectController {

  /**
   * Starts the process to allows a user to control a {@code ImageProject}.
   *
   * @throws IllegalStateException if the controller is unable to read input or transmit output
   */
  void start() throws IllegalStateException;

}
