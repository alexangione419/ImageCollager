package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * A class that represents a PPM Image Project. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public class ProjectImpl implements ImageProject {

  private int width;
  private int height;
  // represents the maximum value allowed in any pixel value
  private int maxPixelValue;
  private int activeLayer;
  private List<Layer> layers;

  //should be false if loadProject() or createNewProject() hasn't been called
  private boolean hasAOpenProject;

  private File projectFile;

  /**
   * Constructs a new PPMProject and initializes layers to an ArrayList of {@code Layer}s and sets
   * the name to "New Project".
   */
  public ProjectImpl() {
    this.layers = new ArrayList<Layer>();

    this.maxPixelValue = 255;
    this.activeLayer = 0;
    this.hasAOpenProject = false;
  }

  @Override
  public void saveImage(String name) throws IllegalStateException, IOException {
    if (name == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }

    String currentCanvas = this.currentCanvas();

    String output = "P3\n"
        + this.getWidth() + " " + this.getHeight() + "\n"
        + this.getMaxPixelValue() + "\n"
        + currentCanvas + "\n";

    FileWriter writer;

    try {
      writer = new FileWriter(name);
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }

    try {
      writer.write(output);
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }
    writer.close();
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

      if ((endOfLineCounter == this.getWidth()) &&
          (linesPassedCounter != (this.getHeight() - 1))) {
        endOfLineCounter = 0;
        linesPassedCounter++;
        results = results.concat("\n");
      }
    }

    return results;
  }

//  private String removeAlpha(String color) {
//    String curCanvas = this.currentCanvas();
//
//    //creates an array of string, where each string contains the R G B A values of each
//    //pixel
//    ArrayList<String> temp = new ArrayList<String>();
//
//    for (int p = 0; p < this.getWidth() * this.getHeight(); p++) {
//      temp.add(curCanvas.substring(p * 16, (p * 16) + 16));
//    }
//
//    String finalCanvas = "";
//
//    for (int i = 0; i < temp.size(); i++) {
//      String c = temp.get(i);
//
//      int alpha = Integer.parseInt(c.substring(12, 15));
//      int red = Integer.parseInt(c.substring(0, 2)) * (alpha / this.maxPixelValue);
//      int green = Integer.parseInt(c.substring(4, 6)) * (alpha / this.maxPixelValue);
//      int blue = Integer.parseInt(c.substring(8, 10)) * (alpha / this.maxPixelValue);
//      double[] tempColor = new double[] {red, green, blue};
//      temp.set(i, this.formatColor(tempColor))
//    }
//
//    for ()
//
//    return null;
//  }

  /**
   * Loops every layer to return back the color that will be displayed in the final image.
   * @param pixel the pixel to look at
   * @return a String of the R G B A values of the final color
   */
  private double[] finalColorAt(int pixel) {
    String results = "";
    int curActiveLayer = this.activeLayer;
    double[] finalColor = new double[4];

    //((y + 1) * w) - (w - (x + 1)) formula to get pixels from getLayerData

    for (Layer layer : this.layers) {
      this.setActiveLayer(layer.getName());

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

        finalColor[0] = (
            ((curAlpha / maxPixelVal * curRed + backgroundRed * (backgroundAlpha / maxPixelVal))
                * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage));

        finalColor[1] = (((curAlpha / maxPixelVal * curGreen + backgroundGreen * (backgroundAlpha
            / maxPixelVal))
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage));

        finalColor[2] = (
            ((curAlpha / maxPixelVal * curBlue + backgroundBlue * (backgroundAlpha / maxPixelVal))
                * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage));
      } else if ((this.activeLayer == 0) && (curAlpha != 0)) {
        finalColor[0] = curRed;
        finalColor[1] = curGreen;
        finalColor[2] = curBlue;
        finalColor[3] = curAlpha;
      }

    }

    this.setActiveLayer(curActiveLayer);

    return new double[] {
         (finalColor[0] *  (finalColor[3] / this.maxPixelValue)),
         (finalColor[1] *  (finalColor[3] / this.maxPixelValue)),
         (finalColor[2] *  (finalColor[3] / this.maxPixelValue))
     };
  }

  /**
   * Formats the given int[] (which represents a color in the RGBA format) into a String.
   * The method gives each RGBA component zero padded ("4" -> "004").
   * @return a formatted String that displays the RGBA values.
   */
  private String formatColor(double[] color) {
    String results = "";


    for (int c = 0; c < color.length; c++) {

      String componentRep = "";

      if (color[c] > 100) {
        componentRep = String.valueOf((int) color[c]);
      }
      else if ((color[c] < 100) && (color[c] >= 10)) {
        componentRep = String.valueOf("0" + (int) color[c]);
      }
      else if (color[c] < 10){
        componentRep = String.valueOf("00" + (int) color[c]);
      }

      results = results.concat(componentRep);
      results = results.concat(" ");
    }

    return results;
  }

  @Override
  public void saveProject(String name) throws IllegalStateException, IOException {
    if (!hasAOpenProject) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }

    if (!name.matches(".+\\..{2,}")) {
      throw new IllegalArgumentException("Name must be valid filename with valid suffix");
    }

    try {
      this.projectFile = new File(name);
      this.projectFile.createNewFile();
    } catch (IOException io) {
      throw new IOException("File error occurred");
    }

    FileWriter writer = null;
    try {
      writer = new FileWriter(name);
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }

    try {
      writer.write(name.substring(0, name.lastIndexOf(".")) + "\n");
      writer.write(this.width + " " + this.height + "\n");
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }
    for (Layer currLayer : this.layers) {
      try {
        writer.write(currLayer.getName() + " " + currLayer.getFilter() + "\n");
        for (int[] row : currLayer.getLayerData()) {
          writer.write(row[0] + " " + row[1] + " " + row[2] + " " + row[3] + "\n");
        }
      } catch (IOException io) {
        throw new IOException("File writer error encountered");
      }
    }
    writer.close();
  }

  //In the controller, make it so that if this method is called after a project is opened, it asks
  //if the user wants to save their previously opened project
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
          + " to zero.");
    }

    this.width = width;
    this.height = height;
    this.activeLayer = 0;
    this.hasAOpenProject = true;
    this.layers = new ArrayList<Layer>();
    this.layers.add(new LayerImpl("Layer1", this)); // make this white
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
  public boolean supportsAlpha() {
    return false;
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
    return this.maxPixelValue;
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
   * IllegalArgumentException.
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
