package model;

import java.io.IOException;

/**
 * An interface for creating image projects that contain {@code Layers}.
 */
public interface ImageProject {

  /**
   * Saves the final image created from all the layers to a specified file path as an image file.
   *
   * @param filePath the file path to save the image
   * @throws IOException           if the file path is invalid.
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  void saveImage(String filePath) throws IllegalStateException;

  /**
   * Saves this project to a specified file path as a .collage file.
   *
   * @throws IOException           if the file path is invalid.
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  void saveProject(String name) throws IllegalStateException;

  /**
   * Loads a project at a given file path.
   *
   * @param filePath the file path of the file being loaded
   * @throws IOException if the file path is invalid OR is not a .collage file.
   */
  void loadProject(String filePath) throws IOException, IllegalStateException;

  /**
   * Creates a new project with the specified width and height.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @throws IllegalArgumentException if the width or height is less than 0.
   */
  void createNewProject(int width, int height) throws IllegalArgumentException;

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
   * Returns the current layer that the user is editing.
   *
   * @return the layer the user is currently editing
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  Layer getActiveLayer() throws IllegalStateException;

  /**
   * Returns the maximum value allowed for a pixel
   *
   * @return the maximum value of a pixel
   */
  int getMaxPixelValue();

  /**
   * Sets the active layer to the {@code Layer} whose name matches the given String.
   *
   * @param layerName the name of the layer to set as active
   * @throws IllegalArgumentException if the name is an empty String or null,
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void setActiveLayer(String layerName) throws IllegalArgumentException, IllegalStateException;

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
   * Adds a new layer to this project with the specified name. The active layer of this
   * {@code ImageProject} gets set to this layer.
   *
   * @param layerName the name to give the layer
   * @throws IllegalArgumentException if the name is an empty String or null
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void addLayer(String layerName) throws IllegalArgumentException, IllegalStateException;

  /**
   * Removes the first layer whose name matches the given String from this project. This method also
   * adjusts the active layer to ensure that it stays on the proper layer.
   *
   * @param layerName the name of the layer to remove.
   * @throws IllegalArgumentException if the given layer is NOT in the current project OR if there's
   *                                  only one layer in the current project OR if the given String
   *                                  is null
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void removeLayer(String layerName) throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets a filter to a specified layer from this project.
   *
   * @param filterName the name of the filter to set to the specified layer
   * @param layerName  the name of the layer receiving the given filter
   * @throws IllegalArgumentException if the given layer is NOT in the current project OR if
   *                                  filterName or layerName are null.
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void setFilter(String filterName, String layerName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Adds the image at the given file to the given layer.
   * @param layerName the layer to add the image to
   * @param imageFile the file of the image to add
   * @param x the x position to place the picture at
   * @param y the y position to place the picture at
   */
  void addImageToLayer(String layerName, String imageFile, int x, int y);
}
