package controller.commands;

import model.ImageProject;

/**
 * A command that adds an image to the given {@code ImageProject} at a specified
 * position on the project canvas and on a specified {@code Layer}.
 */
public class AddImageToLayer implements Command {

  private final String layerName;
  private final String imageFile;
  private final int x;
  private final int y;

  /**
   * Creates a new {@code AddImageToLayer} command object.
   * @param layerName the name of the layer to add the image to
   * @param imageFile the image's file path
   * @param x the x-position to place the image
   * @param y the y-position to place the image
   */
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
