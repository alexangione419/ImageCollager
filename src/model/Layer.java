package model;

import java.io.IOException;

/**
 * A Layer a single slide of a project comprised of one or more images.
 */
public interface Layer {

  /**
   * Adds a given filter to the given layer.
   * @param filerOption the name of the filer to apply to the given layer
   * @throws IllegalArgumentException if filter is invalid
   */
  void applyFilter(String filerOption) throws IllegalArgumentException;

  /**
   * Places an image onto the given layer
   * The top left corner of the image will reside at the given x and y.
   * @param imageFilename the name of the image to add to the layer
   * @param x the x position in the layer to add the image to
   * @param y the y position in the layer to add the image to
   * @throws IllegalArgumentException if imageFilename cannot be found
   */
  void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException;

  /**
   * Changes the colors of a specific pixel.
   * @param x the x position of the pixel
   * @param y the y position of the pixel
   * @param r the red component of the pixel
   * @param g the green component of the pixel
   * @param b the blue component of the pixel
   * @param a the alpha component of the pixel
   */
  void setPixelColor(int x, int y, int r, int g, int b, int a);

}
