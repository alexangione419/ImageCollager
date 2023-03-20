package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import model.filters.BlueComponent;
import model.filters.BrightenIntensity;
import model.filters.BrightenLuma;
import model.filters.BrightenValue;
import model.filters.DarkenIntensity;
import model.filters.DarkenLuma;
import model.filters.DarkenValue;
import model.filters.Filter;
import model.filters.GreenComponent;
import model.filters.Normal;
import model.filters.RedComponent;


/**
 * A layer found in a {@code ProjectImpl}. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public final class LayerImpl implements Layer {

  private final String name;
  private final ImageProject project;
  private String currentFiler; // name of the filter currently applied to the layer
  private int[][] currentLayer;
  private int[][] unfilteredLayer; // a version of the layer with original pixel values retained
  private final HashMap<String, Filter> supportedFilters; // list allowed filters

  /**
   * Constructs a new {@code PPMLayer}.
   */
  public LayerImpl(String name, ImageProject project) throws IllegalArgumentException {
    if (project == null || name == null) {
      throw new IllegalArgumentException("Layer must have a valid name and project.");
    }

    this.name = name;
    this.project = project;
    this.currentLayer = new int[project.getHeight() * project.getWidth()][4];

    this.unfilteredLayer = new int[project.getHeight() * project.getWidth()][4];

    this.currentFiler = "normal"; // filter is normal by default

    this.supportedFilters = new HashMap<>();
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
  public String getFilter() {
    return this.currentFiler;
  }

  @Override
  public int[][] getLayerData() {
    return this.currentLayer;
  }

  @Override
  public int[][] getUnfilteredLayer() {
    return this.unfilteredLayer;
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
    this.currentFiler = filterOption;
  }

  @Override
  public void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException {
    if (x < 0 || x > this.project.getWidth() || y < 0 || y > this.project.getHeight()) {
      throw new IllegalArgumentException("Invalid Coordinates given");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("./res/" + imageFilename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
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

    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    int xLim = x + width;
    int yLim = y + height;
    // ensures the image will not out of the bounds of the layer
    if (x + width > this.project.getWidth()) {
      xLim = this.project.getWidth();
    }
    if (y + height > this.project.getHeight()) {
      yLim = this.project.getHeight();
    }

    for (int r = x; r < xLim; r++) {
      for (int c = y; c < yLim; c++) {
        int conversion = (((c + 1) * this.project.getWidth())
            - (this.project.getWidth() - (r + 1))) - 1;

        this.currentLayer[conversion][0] = sc.nextInt();
        this.currentLayer[conversion][1] = sc.nextInt();
        this.currentLayer[conversion][2] = sc.nextInt();
        this.currentLayer[conversion][3] = this.getMaxPixel();

        this.unfilteredLayer[conversion][0] = this.currentLayer[conversion][0];
        this.unfilteredLayer[conversion][1] = this.currentLayer[conversion][1];
        this.unfilteredLayer[conversion][2] = this.currentLayer[conversion][2];
        this.unfilteredLayer[conversion][3] = this.getMaxPixel();
      }
    }

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {
    // (y * w) - (w - x)
    //((y + 1) * (w + (|w-h|))) - ((h + (|w-h|)) - (x + 1) - 1

    this.currentLayer[(((y + 1) * this.project.getWidth()) - (this.project.getHeight() - (x + 1)))
        - 1]
        [0] = r;
    this.currentLayer[(((y + 1) * this.project.getWidth()) - (this.project.getHeight() - (x + 1)))
        - 1]
        [1] = g;
    this.currentLayer[(((y + 1) * this.project.getWidth()) - (this.project.getHeight() - (x + 1)))
        - 1]
        [2] = b;
    this.currentLayer[(((y + 1) * this.project.getWidth()) - (this.project.getHeight() - (x + 1)))
        - 1]
        [3] = a;

  }

//  private int getPixelIndex(int x, int y) {
//    int sizeDiff = this.project.getWidth() - this.project.getHeight();
//    return (((y + 1) * (this.project.getWidth() + Math.abs(sizeDiff))) -
//        ((this.project.getHeight() + Math.abs(sizeDiff)) - (x + 1))) - 1;
//  }

  @Override
  public void clearLayer() {
    this.currentLayer = new int[project.getHeight() * project.getWidth()][4];
    this.unfilteredLayer = new int[project.getHeight() * project.getWidth()][4];
  }

}
