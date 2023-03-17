package controller.commands;

import model.ImageProject;

public class SaveImage implements Command {
  private final String fileName;

  public SaveImage(String fileName) {
    if (fileName.isEmpty()) {
      throw new IllegalArgumentException("Invalid File Input");
    }
    this.fileName = fileName;
  }


  @Override
  public void run(ImageProject p) {
    p.saveImage(fileName);
  }

}
