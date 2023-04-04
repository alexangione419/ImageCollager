package model;

import java.awt.image.BufferedImage;

import model.pixels.Pixel;

/**
 * An interface containing the various state functions needed in an Image Project. This interface is
 * separate from the Image Project interface so that sections of code that rely on having access to
 * the model only have access to getters relating to the state of the model.
 */
public interface ImageProjectState {

  /**
   * Returns the name of this {@code ImageProject}.
   *
   * @return the name of this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  String getName() throws IllegalStateException;

  /**
   * Returns the width size of this {@code ImageProject}.
   *
   * @return the width of this this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getWidth() throws IllegalStateException;

  /**
   * Returns the height size of this {@code ImageProject}.
   *
   * @return the height of this this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns the number of layers this {@code ImageProject} has.
   *
   * @return the number of layers in this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getNumberOfLayers() throws IllegalStateException;

  /**
   * Returns the maximum value allowed for a pixel.
   *
   * @return the maximum value of a pixel
   */
  int getMaxPixelValue();

  /**
   * Returns whether this {@code ImageProject} has an opened project.
   *
   * @return whether this {@code ImageProject} has an opened project
   */
  boolean hasOpenProject();

  /**
   * Returns whether a {@code Layer} whose name is the given String exists in this project.
   *
   * @param layerName the name of the layer to look for
   * @return true if the layer exists; otherwise false.
   */
  boolean doesLayerExist(String layerName);

  /**
   * Returns a buffered image of the current state of the project
   * @return a BufferedImage of the current project state
   */
  BufferedImage getImageRepresentation();

  /**
   * Outputs a String that represents what the current canvas looks like as an image. The String
   * should contain the RGBA values of every pixel on the canvas.
   *
   * @return a String that represents the current canvas
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  Pixel[][] currentCanvas() throws IllegalStateException;

}
