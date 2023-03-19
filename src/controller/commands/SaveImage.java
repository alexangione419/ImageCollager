package controller.commands;

import java.io.IOException;
import model.ImageProject;

/**
 * A command for saving an image created by the given {@code ImageProject}.
 */
public class SaveImage implements Command {

  private final String fileName;

  /**
   * Constructs a new {@code SaveImage} command object.
   * @param fileName the file name to give the image
   */
  public SaveImage(String fileName) {
    if (fileName.isEmpty()) {
      throw new IllegalArgumentException("Invalid File Input");
    }
    this.fileName = fileName;
  }


  @Override
  public void run(ImageProject p) {
    try {
      p.saveImage(fileName);
    }
    catch (IOException e) {

    }
  }

}
