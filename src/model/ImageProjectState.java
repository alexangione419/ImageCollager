package model;

import java.awt.image.BufferedImage;

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
   * Allows for the setting/changing of the name of the project.
   * @param name the name to change the project's name to
   */
  void setName(String name) throws IllegalStateException, IllegalArgumentException;

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
   * Returns a buffered image of the current state of the project.
   * @return a BufferedImage of the current project state
   */
  BufferedImage getImageRepresentation();

  /**
   * Sets the active layer to the {@code Layer} at the given layer index.
   *
   * @param layerIndex the name of the layer to set as active
   * @throws IllegalArgumentException if the layer index is greater than the number of layers or
   *                                  less than 0.
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void setActiveLayer(int layerIndex) throws IllegalArgumentException, IllegalStateException;

  /**
   * Gets the name of the projects active layer.
   * @return the name of the currently active layer.
   */
  String getActiveLayerName();

}
