package model;

import java.io.IOException;
import java.util.HashMap;
import model.filters.Filter;

/**
 * An interface for creating image projects that contain {@code Layers}.
 */
public interface ImageProject extends ImageProjectState {

  /**
   * Outputs a String that represents what the current canvas looks like as an image. The String
   * should contain the RGBA values of every pixel on the canvas.
   *
   * @return a String that represents the current canvas
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  String currentCanvas() throws IllegalStateException;

  /**
   * Creates a new project with the specified width and height.
   *
   * @param width  the width of the canvas
   * @param height the height of the canvas
   * @throws IllegalArgumentException if the width or height is less than OR equal to 0.
   */
  void createNewProject(int width, int height) throws IllegalArgumentException;


  /**
   * Returns the current layer that the user is editing.
   *
   * @return the layer the user is currently editing
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  Layer getActiveLayer() throws IllegalStateException;


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
   * Returns a copy of the HashMap containing every filter that this {@code ImageProject} supports.
   *
   * @return a copy of the HashMap containing all the filters.
   */
  HashMap<String, Filter> getFilters();


  /**
   * Adds an image, specified by imageFile, to the {@code Layer} whose name matches layerName.
   *
   * @param layerName the name of the layer to add the image to
   * @param imageFile the file of the image to add
   * @param x         the x position to place the picture at
   * @param y         the y position to place the picture at
   */
  void addImageToLayer(String layerName, String imageFile, int x, int y);
}
