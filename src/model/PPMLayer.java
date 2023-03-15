package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
  private final PPMProject project; // the PPMProject that this Layer is in
  private String currentFiler; // name of the filter currently applied to the layer
  private int[][] currentLayer;
  private int[][] unfilteredLayer; // a version of the layer with original pixel values retained
  private final HashMap<String, Filter> supportedFilters; // list allowed filters

  /**
   * Constructs a new {@code PPMLayer}.
   */
  public PPMLayer(String name, PPMProject project) {
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
  public int[][] getLayerData() {
    return this.currentLayer.clone();
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

  }

  @Override
  public void setPixelColor(int x, int y, int r, int g, int b, int a) {

  }

}
