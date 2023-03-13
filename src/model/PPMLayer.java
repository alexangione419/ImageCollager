package model;

/**
 * A layer found in a {@code PPMProject}. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public final class PPMLayer implements Layer {

  private final String name;

  /**
   * Constructs a new {@code PPMLayer}.
   */
  public PPMLayer(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void applyFilter(String filerOption) throws IllegalArgumentException {

  }

  @Override
  public void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException {

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {

  }
}
