package model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import model.filters.Filter;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;


/**
 * A layer found in a {@code ProjectImpl}. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public final class LayerImpl implements Layer {

  private final String name;
  private final ImageProject project;
  private String currentFilter; // name of the filter currently applied to the layer
  private Pixel[][] currentLayer;
  private Pixel[][] unfilteredLayer; // a version of the layer with original pixel values retained
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
    this.currentLayer = new Pixel[this.project.getWidth()][this.project.getHeight()];
    this.unfilteredLayer = new Pixel[this.project.getWidth()][this.project.getHeight()];

    this.clearLayer();

    this.currentFilter = "normal"; // filter is normal by default

    this.supportedFilters = this.project.getFilters();
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
    return this.currentFilter;
  }

  @Override
  public Pixel[][] getLayerData() {
    return this.currentLayer;
  }

  @Override
  public Pixel[][] getUnfilteredLayer() {
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

    this.currentLayer = this.supportedFilters.get("normal").apply(this);
    this.currentLayer = this.supportedFilters.get(filterOption).apply(this);

    this.currentFilter = filterOption;
  }

  @Override
  public void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException {
    if (x < 0 || x > this.project.getWidth() || y < 0 || y > this.project.getHeight()) {
      throw new IllegalArgumentException("Invalid Coordinates given");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(imageFilename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    }

    String token;
    token = sc.next();

    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int y2 = y; y2 < y + height; y2++) {
      for (int x2 = x; x2 < x + width; x2++) {

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        if (y2 >= this.project.getHeight()
            || (x2 >= this.project.getWidth())) {
          continue;
        }

        this.currentLayer[x2][y2] = new RGBAPixel(this.getMaxPixel(), r, g, b);
        this.unfilteredLayer[x2][y2] = new RGBAPixel(this.getMaxPixel(), r, g, b);
      }
    }
    this.applyFilter(this.currentFilter);

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {
    this.currentLayer[x][y] = new RGBAPixel(this.getMaxPixel(), r, g, b, a);
    this.unfilteredLayer[x][y] = new RGBAPixel(this.getMaxPixel(), r, g, b, a);
  }

  @Override
  public void setPixelColor(int x, int y, Pixel pixel) {

    this.currentLayer[x][y] = new RGBAPixel(this.getMaxPixel(),
        pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getAlpha());
    this.unfilteredLayer[x][y] = new RGBAPixel(this.getMaxPixel(),
        pixel.getRed(), pixel.getGreen(), pixel.getBlue(), pixel.getAlpha());
  }

  @Override
  public void clearLayer() {
    this.currentLayer = new Pixel[this.project.getWidth()][this.project.getHeight()];
    this.unfilteredLayer = new Pixel[this.project.getWidth()][this.project.getHeight()];

    for (int x = 0; x < this.project.getWidth(); x++) {
      for (int y = 0; y < this.project.getHeight(); y++) {
        this.setPixelColor(x, y, 255, 255, 255, 0);
      }
    }
  }

  @Override
  public void makeVisible() {
    for (int x = 0; x < this.project.getWidth(); x++) {
      for (int y = 0; y < this.project.getHeight(); y++) {
        this.setPixelColor(x, y,
            this.currentLayer[x][y].getRed(),
            this.currentLayer[x][y].getGreen(),
            this.currentLayer[x][y].getBlue(),
            this.getMaxPixel());
      }
    }
  }
}
