package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import model.filters.Blue_Component;
import model.filters.Brighten_Intensity;
import model.filters.Brighten_Luma;
import model.filters.Brighten_Value;
import model.filters.Darken_Intensity;
import model.filters.Darken_Luma;
import model.filters.Darken_Value;
import model.filters.Filter;
import model.filters.Green_Component;
import model.filters.Normal;
import model.filters.Red_Component;


/**
 * A layer found in a {@code PPMProject}. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public final class PPMLayer implements Layer {

  private final String name;
  private final ImageProject project; // the Project that this Layer is in
  private String currentFiler; // name of the filter currently applied to the layer
  private int[][] currentLayer;
  private int[][] unfilteredLayer; // a version of the layer with original pixel values retained
  private final HashMap<String, Filter> supportedFilters; // list allowed filters

  /**
   * Constructs a new {@code PPMLayer}.
   */
  public PPMLayer(String name, ImageProject project) throws IllegalArgumentException {
    if (project == null || name == null) {
      throw new IllegalArgumentException("Layer must have a valid name and project");
    }

    this.name = name;
    this.project = project;
    this.currentLayer = new int[project.getHeight() * project.getWidth()][4];
    this.unfilteredLayer = new int[project.getHeight() * project.getWidth()][4];

    this.currentFiler = "normal"; // filter is normal by default

    this.supportedFilters = new HashMap<>();
    this.supportedFilters.put("normal", new Normal());
    this.supportedFilters.put("red-component", new Red_Component());
    this.supportedFilters.put("green-component", new Green_Component());
    this.supportedFilters.put("blue-component", new Blue_Component());
    this.supportedFilters.put("brighten-value", new Brighten_Value());
    this.supportedFilters.put("brighten-intensity", new Brighten_Intensity());
    this.supportedFilters.put("brighten-luma", new Brighten_Luma());
    this.supportedFilters.put("darken-value", new Darken_Value());
    this.supportedFilters.put("darken-intensity", new Darken_Intensity());
    this.supportedFilters.put("darken-luma", new Darken_Luma());

  }


  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getTotalPixels() {
    return project.getHeight() * project.getWidth();
  }

  @Override
  public int[][] getLayerData() {
    return this.currentLayer.clone();
  }

  @Override
  public int[][] getUnfilteredLayer() {
    return this.unfilteredLayer.clone();
  }

  @Override
  public int getMaxPixel() {
    return this.project.getMaxPixelValue();
  }

  @Override
  public void applyFilter(String filterOption) throws IllegalArgumentException {
    if (!this.supportedFilters.containsKey(filterOption)) {
      throw new IllegalArgumentException("Unsupported filter");
    }
    this.currentLayer = this.supportedFilters.get(filterOption).apply(this);
  }

  @Override
  public void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(imageFilename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + imageFilename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    for (int r = x; r < this.project.getWidth(); r++) {
      for (int c = y; c < this.project.getHeight(); c++) {
        String token;
        token = sc.next();
        if (!token.equals("P3")) {
          throw new IllegalArgumentException("Invalid PPM file");
        }
        int width = sc.nextInt();
        int height = sc.nextInt();
        int maxValue = sc.nextInt();

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            this.currentLayer[(r * this.project.getWidth()) - (this.project.getWidth()
                - c)][0] = sc.nextInt();
            this.currentLayer[(r * this.project.getWidth()) - (this.project.getWidth()
                - c)][1] = sc.nextInt();
            this.currentLayer[(r * this.project.getWidth()) - (this.project.getWidth()
                - c)][2] = sc.nextInt();
            this.currentLayer[(r * this.project.getWidth()) - (this.project.getWidth()
                - c)][3] = sc.nextInt();
          }
        }
      }
    }

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {
    // (y * w) - (w - x)
  }

}
