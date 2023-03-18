package controller.commands;

import java.io.IOException;
import model.ImageProject;

/**
 * Represents a valid command to be run on an Image Processor.
 */
public interface Command {

  /**
   * Allows a command to be run on a given processor.
   *
   * @param p the processor to run the command on
   */
  void run(ImageProject p) throws IOException;
}
