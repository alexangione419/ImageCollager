package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import model.filters.BlueComponent;
import model.filters.BrightenIntensity;
import model.filters.BrightenLuma;
import model.filters.BrightenValue;
import model.filters.DarkenIntensity;
import model.filters.DarkenLuma;
import model.filters.DarkenValue;
import model.filters.Difference;
import model.filters.Filter;
import model.filters.GreenComponent;
import model.filters.Normal;
import model.filters.RedComponent;
import model.pixels.Pixel;
import model.pixels.PixelUtils;
import model.pixels.RGBAPixel;

/**
 * A class that represents a PPM Image Project. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public class ProjectImpl implements ImageProject {

  private String name;
  private int width;
  private int height;
  private int activeLayer;
  private List<Layer> layers;
  private HashMap<String, Filter> supportedFilters;

  //should be false if loadProject() or createNewProject() hasn't been called
  private boolean hasAOpenProject;

  /**
   * Constructs a new PPMProject and initializes layers to an ArrayList of {@code Layer}s and sets
   * the name to "New Project".
   */
  public ProjectImpl() {
    this.layers = new ArrayList<Layer>();

    this.activeLayer = 0;
    this.hasAOpenProject = false;
  }

  @Override
  public void saveImage(String name) throws IllegalStateException, IllegalArgumentException,
      IOException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(name)) {

      String output = "P3\n"
          + this.getWidth() + " " + this.getHeight() + "\n"
          + this.getMaxPixelValue() + "\n"
          + this.currentCanvas() + "\n";

      ImageProjectFileUtils.createFile(name + ".ppm");
      ImageProjectFileUtils.writeToFile(name + ".ppm", output);
    }
  }

  @Override
  public void saveProject(String name) throws IllegalStateException, IOException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(name)) {

      String output = name + "\n"
          + this.width + " " + this.height + "\n";

      for (Layer currLayer : this.layers) {
        output = output.concat(currLayer.getName() + " " + currLayer.getFilter() + "\n");

        int i = 0;

        for (Pixel[] row : currLayer.getLayerData()) {
          for (Pixel p : row) {
            output = output.concat(p.toString());

            if (i != ((this.width * this.height) - 1)) {
              output = output.concat("\n");
            }
            i++;
          }
        }
      }

      ImageProjectFileUtils.createFile(name + ".collage");
      ImageProjectFileUtils.writeToFile(name + ".collage", output);
    }
  }

  @Override
  public String currentCanvas() throws IllegalStateException {
    if (!this.hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    String results = "";

    for (int y = 0; y < this.getHeight(); y++) {
      for (int x = 0; x < this.getWidth(); x++) {
        Layer[] layers = new Layer[this.layers.size()];

        for (int i = 0; i < this.layers.size(); i++) {
          layers[i] = this.layers.get(i);
        }

        results = results.concat(
            PixelUtils.convertRGBAtoRGB(
                PixelUtils.finalColorAt(x, y, (double) this.getMaxPixelValue(), layers))
                .toStringRGB());
      }
      if (y != this.getHeight() - 1) {
        results = results.concat("\n");
      }
    }

    return results;
  }

  @Override
  public void loadProject(String file) throws IllegalArgumentException {
    if (file == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }

    if (!ImageProjectFileUtils.isProjectFile(file)) {
      throw new IllegalArgumentException("File at filepath is not a .collage file.");
    } else {
      Scanner sc;
      try {
        sc = new Scanner(new FileInputStream(file));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Project file not found at given filepath.");
      }

      //discards name of project
      checkNext(sc);
      sc.next();
      checkNext(sc);

      int width;
      int height;

      try {
        width = sc.nextInt();
      } catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }
      checkNext(sc);

      try {
        height = sc.nextInt();
      } catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }

      checkNext(sc);

      this.createNewProject(width, height);

      while (sc.hasNext()) {
        String newLayer = sc.next();
        checkNext(sc);
        String filter = sc.next();

        if (this.doesLayerExist(newLayer)) {
          this.setFilter(filter, newLayer);
        } else {
          this.addLayer(newLayer);
          this.setFilter(filter, newLayer);
        }

        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            checkNext(sc);

            int r = sc.nextInt();
            checkNext(sc);

            int g = sc.nextInt();
            checkNext(sc);

            int b = sc.nextInt();
            checkNext(sc);

            int a = sc.nextInt();

            this.setActiveLayer(newLayer);
            this.getActiveLayer().setPixelColor(x, y,
                new RGBAPixel(this.getMaxPixelValue(), r, g, b, a));
          }
        }
      }
    }
  }

  /**
   * Ensures that there is more input in file contents within the given scanner.
   *
   * @param scanner the scanner containing the file contents
   */
  private void checkNext(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalArgumentException("Invalid project file format");
    }
  }

  @Override
  public void createNewProject(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("A project's width or height cannot be less than or equal"
          + " to zero.");
    }

    this.width = width;
    this.height = height;
    this.activeLayer = 0;
    this.hasAOpenProject = true;
    this.layers = new ArrayList<Layer>();

    this.supportedFilters = new HashMap<String, Filter>();
    this.supportedFilters.put("normal", new Normal());
    this.supportedFilters.put("red-component", new RedComponent());
    this.supportedFilters.put("green-component", new GreenComponent());
    this.supportedFilters.put("blue-component", new BlueComponent());
    this.supportedFilters.put("brighten-value", new BrightenValue());
    this.supportedFilters.put("brighten-intensity", new BrightenIntensity());
    this.supportedFilters.put("brighten-luma", new BrightenLuma());
    this.supportedFilters.put("darken-value", new DarkenValue());
    this.supportedFilters.put("darken-intensity", new DarkenIntensity());
    this.supportedFilters.put("darken-luma", new DarkenLuma());

    this.supportedFilters.put("difference", new Difference(this.layers));

    this.layers.add(new LayerImpl("Layer1", this));
  }

  @Override
  public int getWidth() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    return this.width;
  }

  @Override
  public String getName() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }
    return Objects.requireNonNullElse(this.name, "Untitled Project");
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
  public int getMaxPixelValue() throws IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }
    return 255;
  }

  @Override
  public void setActiveLayer(String layerName)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (layerName == null) {
      throw new IllegalArgumentException("Layer name cannot be null.");
    }

    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("The layer " + layerName + " does not exist in "
          + "this project");
    } else {
      this.activeLayer = this.layers.indexOf(this.getLayer(layerName));
    }
  }

  @Override
  public void setActiveLayer(int layerIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (layerIndex < 0 || layerIndex >= this.getNumberOfLayers()) {
      throw new IllegalArgumentException("Layer index is out of bounds.");
    }

    this.activeLayer = layerIndex;

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

    if (layerName.contains("\n") || layerName.contains(" ")) {
      throw new IllegalArgumentException("A layer name cannot contain a space or a linebreak.");
    }

    this.layers.add(new LayerImpl(layerName, this));
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
      throw new IllegalArgumentException(
          "Tried to remove layer \"Layer\" but that layer doesn't exist "
              + "in this project.");
    } else {
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

  @Override
  public HashMap<String, Filter> getFilters() {
    return new HashMap<String, Filter>(this.supportedFilters);
  }

  @Override
  public boolean hasOpenProject() {
    return this.hasAOpenProject;
  }

  @Override
  public void addImageToLayer(String layerName, String imageFile, int x, int y) {
    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("Layer not found in current project.");
    }
    this.setActiveLayer(layerName);
    this.getActiveLayer().addImageToLayer(imageFile, x, y);
  }

  /**
   * Returns whether a {@code Layer} whose name is the given String exists in this project.
   *
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
   *
   * @param layerName the name of the layer to look for
   * @return the {@code Layer} with the given layerName; otherwise, this method throws an
   *         IllegalArgumentException.
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
    } else {
      throw new IllegalArgumentException("The layer " + layerName + " does not exist in "
          + "this project");
    }

    return result;
  }
}
