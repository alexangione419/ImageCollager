package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a PPM Image Project. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public class PPMProject implements ImageProject {

  private int width;
  private int height;
  // represents the maximum value allowed in any pixel value
  private int maxPixelValue;
  private int activeLayer;
  private final List<Layer> layers;

  //should be false if loadProject() or createNewProject() hasn't been called
  private boolean hasAOpenProject;

  /**
   * Constructs a new PPMProject and initializes layers to an ArrayList of {@code Layer}s
   * and sets the name to "New Project".
   */
  public PPMProject() {
    this.layers = new ArrayList<Layer>();
    this.activeLayer = 0;
    this.hasAOpenProject = false;
  }

  @Override
  public void saveImagePPM(String filePath, String name) throws IOException, IllegalStateException {
    if (filePath == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }
  }

  @Override
  public void saveProject(String filePath, String name) throws IOException, IllegalStateException {
    if (filePath == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }

    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

  }

  //In the controller, make it so that if this method is called after a project is opened, it asks
  //if
  @Override
  public void loadProject(String filePath) throws IOException, IllegalStateException {
    if (filePath == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }
  }

  @Override
  public void createNewProject(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("A project's width or height cannot be less than or equal"
          + "to zero.");
    }

    this.width = width;
    this.height = height;
    this.activeLayer = 0;
    this.hasAOpenProject = true;
    this.layers.add(new PPMLayer("Layer 1", this)); // make this white
  }

  @Override
  public int getWidth() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    return this.width;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    return this.height;
  }

  @Override
  public int getNumberOfLayers() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    return this.layers.size();
  }

  @Override
  public Layer getActiveLayer() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    return this.layers.get(this.activeLayer);
  }

  @Override
  public int getMaxPixelValue() {
    return this.maxPixelValue;
  }

  @Override
  public void setActiveLayer(String layerName) throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null.");
    }

    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("The layer " + layerName + " does not exist in "
          + "this project");
    }

    else {
      this.activeLayer = this.layers.indexOf(this.getLayer(layerName));
    }
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null.");
    }

    if (layerName.isBlank() || layerName.equals(System.lineSeparator())) {
      throw new IllegalArgumentException("A layer name cannot be an empty string or "
          + "just whitespace.");
    }

    this.layers.add(new PPMLayer(layerName, this));
    this.activeLayer = this.layers.size() - 1;
  }

  @Override
  public void removeLayer(String layerName) throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null.");
    }

    if (this.layers.size() == 1) {
      throw new IllegalStateException("There is only 1 layer. Operation cannot be done.");
    }

    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("Tried to remove layer \"Layer \" but that layer doesn't exist "
          + "in this project.");
    }
    else {
      //adjusts activeLayer if this the index of the layer that's being removed
      //is greater than activeLayer
      if (this.activeLayer >= this.layers.indexOf(this.getLayer(layerName))) {
        this.activeLayer = (this.activeLayer != 0) ? this.activeLayer - 1 : 0;
      }

      this.layers.remove(this.getLayer(layerName));

    }
  }

  @Override
  public void setFilter(String filterName, String layerName)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (filterName == null || layerName == null) {
      throw new IllegalArgumentException("Layer name and/or Filter name cannot be null.");
    }

    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("Layer does not exist within project.");
    }

    try {
      this.getLayer(layerName).applyFilter(filterName);
    } catch (IllegalArgumentException a) {
      throw new IllegalArgumentException("Invalid filter");
    }
  }

  /**
   * Returns whether a {@code Layer} whose name is the given String exists in this project.
   * @param layerName the name of the layer to look for
   * @return true if the layer exists; otherwise false.
   */
  private boolean doesLayerExist(String layerName) {
    boolean result = false;

    for (Layer l : this.layers) {
      String curName = l.getName();

      if (curName.equals(layerName)) {
        result = true;
        break;
      }
    }

    return result;
  }

  /**
   * Returns the {@code Layer} whose name matches the given String.
   * @param layerName the name of the layer to look for
   * @return the {@code Layer} with the given layerName; otherwise, this method
   *         throws an IllegalArgumentException.
   */
  private Layer getLayer(String layerName) {
    Layer result = null;

    if (this.doesLayerExist(layerName)) {
      for (Layer l : this.layers) {
        String curName = l.getName();

        if (curName.equals(layerName)) {
          result = l;
          break;
        }
      }
    }

    else {
      throw new IllegalArgumentException("The layer " + layerName + " does not exist in "
          + "this project");
    }

    return result;
  }
}
