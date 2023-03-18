package controller.commands;

import model.ImageProject;

public class AddImageToLayer implements Command {

  private final String layerName;
  private final String imageFile;
  private final int x;
  private final int y;

  public AddImageToLayer(String layerName, String imageFile, int x, int y) {
    if (layerName.isEmpty() || imageFile.isEmpty()) {
      throw new IllegalArgumentException("Invalid input: Cannot be empty");
    }
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Invalid coordinates given");
    }

    this.layerName = layerName;
    this.imageFile = imageFile;
    this.x = x;
    this.y = y;

  }


  @Override
  public void run(ImageProject p) {
    p.addImageToLayer(this.layerName, this.imageFile, this.x, this.y);
  }
}
