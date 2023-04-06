package controller;

/**
 * Interface outlining a class with the features found below.
 */
public interface Features {

  /**
   * Allows the inherited class to implement functionality for adding an image to a layer
   * dependent on its model.
   * @param layer The name of the layer to add the image to
   * @param image the path of the image to add to the layer
   * @param x the x location to place the image
   * @param y the y location to place the image
   */
  void addImageToLayer(String layer, String image, int x, int y);

  /**
   * Allows the inherited class to implement functionality for adding a new layer, dependant on
   * its model.
   * @param layerName the name of the layer to add
   */
  void addLayer(String layerName);

  /**
   * Allows the inherited class to implement functionality for loading a project, dependent on its
   * model.
   * @param filepath the absolute path of the file to load
   */
  void loadProject(String filepath);

  /**
   * Allows the inherited class to implement functionality for saving a project, dependent on its
   * model.
   * @param name the name to save the project under
   */
  void saveProject(String name);

  /**
   * Allows the inherited class to implement functionality for saving an image, dependent on its
   * model.
   * @param name the name to save the image under
   */
  void saveImage(String name);

  /**
   * Allows the inherited class to implement functionality for creating a new project,
   * dependent on its model.
   * @param width the desired width of the new project
   * @param height the desired height of the new project
   */
  void newProject(int width, int height);

  /**
   * Allows the inherited class to implement functionality for setting a filter on a project,
   * dependent on its model.
   * @param filterName the name of the desired filter
   * @param layerName the name layer to apply the filter to
   */
  void setFilter(String filterName, String layerName);

  /**
   * Allows the inherited class to quit and kill the current process.
   */
  void exit();


}
