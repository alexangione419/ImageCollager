package controller.commands;

import controller.commands.Command;
import model.ImageProject;

public class AddImageToLayer implements Command {
  private final String layerName;
  private final String imageFile;
  public AddImageToLayer(String layerName, String imageFile) {
    if (layerName.isEmpty() || imageFile.isEmpty()) {
      throw new IllegalArgumentException("Invalid input: Cannot be empty");
    }
    this.layerName = layerName;
    this.imageFile = imageFile;
  }


  @Override
  public void run(ImageProject p) {
    p.addImageToLayer(this.layerName, this.imageFile);
  }
}
