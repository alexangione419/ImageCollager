package view;

import java.io.IOException;

/**
 * An interface for viewing {@code ImageProject}s.
 */
public interface ImageProjectView {


  /**
   * Outputs what the current canvas looks like.
   * @throws IOException if the transmission of the message to the data output fails
   */
  void currentCanvas() throws IOException;

  /**
   * Renders a given message to the data output in the implementation.
   * @param message the message to be printed
   * @throws IOException if the transmission of the message to the data output fails
   */
  void renderMessage(String message) throws IOException;

}
