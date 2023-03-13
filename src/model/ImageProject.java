package model;

import java.io.IOException;

/**
 * An interface for creating image projects that contain {@code Layers}.
 */
public interface ImageProject {

  /**
   * Saves the final image created from all the layers to a specified file path as a .ppm file.
   *
   * @param filePath the file path to save the image
   * @throws IOException if the file path is invalid.
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  void saveImagePPM(String filePath) throws IOException, IllegalStateException;

  /**
   * Saves this project to a specified file path as a .collage file.
   *
   * @param filePath the file path to save the image
   * @throws IOException if the file path is invalid.
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  void saveProject(String filePath) throws IOException, IllegalStateException;

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
   * @return the width of this this {@code ImageProject}
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  int getWidth() throws IllegalStateException;

  /**
   * Returns the height size of this {@code ImageProject}.
   * @return the height of this this {@code ImageProject}
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns the number of layers this {@code ImageProject} has.
   * @return the number of layers in this {@code ImageProject}
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  int getNumberOfLayers() throws IllegalStateException;

  /**
   * Adds a new layer to this project with the specified name.
   *
   * @param name the name to give the layer
   * @throws IllegalArgumentException if the name is an empty String or null
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void addLayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Removes the first layer whose name matches the given String from this project.
   *
   * @param layerName the name of the layer to remove.
   * @throws IllegalArgumentException if the given layer is NOT in the current project OR
   *                                  if there's only one layer in the current project
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void removeLayer(String layerName) throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets a filter to a specified layer from this project.
   *
   * @param filterName the name of the filter to set to the specified layer
   * @param layerName the name of the layer receiving the given filter
   * @throws IllegalArgumentException if the given layer is NOT in the current project
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void setFilter(String filterName, String layerName) throws IllegalArgumentException, IllegalStateException;
}
