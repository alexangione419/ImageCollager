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
  private String projectName;
  private List<Layer> layers;

  //should be false if loadProject() or createNewProject() hasn't been called
  private boolean hasAOpenProject;

  /**
   * Constructs a new PPMProject and initializes layers to an ArrayList of {@code Layer}s
   * and sets the name to "New Project".
   */
  public PPMProject() {
    this.projectName = "New Project";
    this.layers = new ArrayList<Layer>();
    this.hasAOpenProject = false;
  }

  @Override
  public void saveImagePPM(String filePath) throws IOException, IllegalStateException {

  }

  @Override
  public void saveProject(String filePath) throws IOException, IllegalStateException {

  }

  @Override
  public void loadProject(String filePath) throws IOException, IllegalStateException {

  }

  @Override
  public void createNewProject(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("A project's width or height cannot be less than or equal"
          + "to zero.");
    }

    this.width = width;
    this.height = height;
    this.hasAOpenProject = true;
    this.layers.add(new PPMLayer("Layer 1"));
  }

  @Override
  public void addLayer(String name) throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (name.isBlank() || name.equals(System.lineSeparator())) {
      throw new IllegalArgumentException("A layer name cannot be an empty string or "
          + "just whitespace.");
    }

    this.layers.add(new PPMLayer(name));
  }

  @Override
  public void removeLayer(String layerName) throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (this.layers.size() == 1) {
      throw new IllegalStateException("There is only 1 layer. Operation cannot be done.");
    }

    for (int i = 0; i < this.layers.size(); i++) {
      String curName = this.layers.get(i).getName();

      if (curName.equals(layerName)) {
        this.layers.remove(i);
        break;
      }

      if (i == (this.layers.size() - 1)) {
        throw new IllegalArgumentException("Tried to remove layer \"Layer \" but that layer doesn't exist "
            + "in this project.");
      }
    }
  }

  @Override
  public void setFilter(String filterName, String layerName)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalArgumentException("There's currently no open project.");
    }
  }
}
