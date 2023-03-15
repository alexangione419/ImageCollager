package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A layer found in a {@code PPMProject}. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public final class PPMLayer implements Layer {

  private final String name;
  private final PPMProject project; // the PPMProject that this Layer is in
  private String currentFiler; // name of the filter currently applied to the layer
  private String unfilteredLayer; // a version of the layer with original pixel values retained
  // idea right now for it to the  filename of an unfiltered version of this layer
  // need a way to store the "normal" version, might be better ways than this
  private final ArrayList<String> supportedFilters; // list allowed filters

  /**
   * Constructs a new {@code PPMLayer}.
   */
  public PPMLayer(String name, PPMProject project) {
    this.name = name;
    this.project = project;
    this.currentFiler = "normal"; // filter is normal by default

    this.supportedFilters = new ArrayList<String>(Arrays.asList("normal", "red-component",
            "blue-component", "green-component", "brighten-value", "brighten-intensity",
            "brighten-luma", "darken-value", "darken-intensity", "darken-value"));
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void applyFilter(String filterOption) throws IllegalArgumentException {
    if (!this.supportedFilters.contains(filterOption)) {
      throw new IllegalArgumentException("Unsupported filter");
    }

  }

  @Override
  public void addImageToLayer(String imageFilename, int x, int y) throws IllegalArgumentException {

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {

  }
}
