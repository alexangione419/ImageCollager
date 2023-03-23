package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents a PPM Image Project. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public class ProjectImpl implements ImageProject {

  private int width;
  private int height;
  private int activeLayer;
  private List<Layer> layers;

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
        for (int[] row : currLayer.getLayerData()) {
          output = output.concat(row[0] + " " + row[1] + " " + row[2] + " " + row[3] + "\n");
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

    int endOfLineCounter = 0;
    int linesPassedCounter = 0;

    //for every pixel
    for (int p = 0; p < this.getWidth() * this.getHeight(); p++) {
      results = results.concat(this.formatColor(this.finalColorAt(p)));

      endOfLineCounter++;

      if ((endOfLineCounter == this.getWidth())
          && (linesPassedCounter != (this.getHeight() - 1))) {

        endOfLineCounter = 0;
        linesPassedCounter++;
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
    }

    else {
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
      }
      catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }
      checkNext(sc);

      try {
        height = sc.nextInt();
      }
      catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }

      ImageProject loaded = new ProjectImpl();
      loaded.createNewProject(width, height);

      while (sc.hasNext()) {
        String newLayer = sc.next();
        checkNext(sc);
        String filter = sc.next();
        int[][] newLayerData = new int[height * width][4];

        for (int i = 0; i < width * height; i++) {
          checkNext(sc);
          newLayerData[i][0] = sc.nextInt();
          checkNext(sc);
          newLayerData[i][1] = sc.nextInt();
          checkNext(sc);
          newLayerData[i][2] = sc.nextInt();
          checkNext(sc);
          newLayerData[i][3] = sc.nextInt();
        }

        loaded.addLayer(newLayer);
        int[][] data = loaded.getActiveLayer().getLayerData();
        data = newLayerData;
        loaded.setFilter(filter, newLayer);
      }
    }
  }

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
  public void addImageToLayer(String layerName, String imageFile, int x, int y) {
    if (!this.doesLayerExist(layerName)) {
      throw new IllegalArgumentException("Layer not found in current project.");
    }
    this.setActiveLayer(layerName);
    this.getActiveLayer().addImageToLayer(imageFile, x, y);
  }

  /**
   * Loops every layer to return back the color that will be displayed in the final image.
   *
   * @param pixel the pixel to look at
   * @return a String of the R G B A values of the final color
   */
  private double[] finalColorAt(int pixel) {
    double[] finalColor = new double[4];

    //((y + 1) * w) - (w - (x + 1)) formula to get pixels from getLayerData

    for (Layer layer : this.layers) {

      int[][] curLayerData = layer.getLayerData();

      double curRed = curLayerData[pixel][0];
      double curGreen = curLayerData[pixel][1];
      double curBlue = curLayerData[pixel][2];
      double curAlpha = curLayerData[pixel][3];

      if ((this.activeLayer != 0) && (curAlpha != 0)) {
        double backgroundRed = finalColor[0];
        double backgroundGreen = finalColor[1];
        double backgroundBlue = finalColor[2];
        double backgroundAlpha = finalColor[3];

        double maxPixelVal = this.getMaxPixelValue();

        double alphaPercentage = ((curAlpha / maxPixelVal) + backgroundAlpha / maxPixelVal * (1
            - (curAlpha / maxPixelVal)));

        finalColor[3] = (alphaPercentage * maxPixelVal);

        finalColor[0] = (curAlpha / maxPixelVal * curRed + backgroundRed
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);

        finalColor[1] = (curAlpha / maxPixelVal * curGreen + backgroundGreen
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);

        finalColor[2] = (curAlpha / maxPixelVal * curBlue + backgroundBlue
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);
        
      } else if ((this.activeLayer == 0) && (curAlpha != 0)) {
        finalColor[0] = curRed;
        finalColor[1] = curGreen;
        finalColor[2] = curBlue;
        finalColor[3] = curAlpha;
      }
    }

    return new double[]{
        (finalColor[0] * (finalColor[3] / this.getMaxPixelValue())),
        (finalColor[1] * (finalColor[3] / this.getMaxPixelValue())),
        (finalColor[2] * (finalColor[3] / this.getMaxPixelValue()))
    };
  }

  /**
   * Formats the given double[] (which represents a color in the RGBA format) into a String.
   * The method gives each RGBA component a space in between.
   *
   * @return a formatted String that displays the RGBA values.
   */
  private String formatColor(double[] color) {
    String results = "";

    for (int c = 0; c < color.length; c++) {

      String componentRep = "";

      componentRep = String.valueOf((int) color[c]);

      results = results.concat(componentRep);
      results = results.concat(" ");
    }

    return results;
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
