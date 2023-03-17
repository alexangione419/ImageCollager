package controller;

public interface ImageProjectController {

  /**
   * Starts the process to allows a user to control a {@code ImageProject}.
   *
   * @throws IllegalStateException if the controller is unable to read input or transmit output
   */
  void start() throws IllegalStateException;

}
