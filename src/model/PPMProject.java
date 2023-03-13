package model;

import java.io.IOException;

/**
 * A class that represents a PPM Image Project. PPM is an image file format that contains rows and
 * columns, each containing the red, green, and blue values for each pixel in an image. The PPM
 * files that this class takes in also contain the alpha value of an image.
 */
public class PPMProject implements ImageProject {

  private int width;
  private int height;

  private String projectName;

  @Override
  public void saveImagePPM(String filePath) throws IOException {

  }

  @Override
  public void saveProject(String filePath) throws IOException {

  }

  @Override
  public void loadProject(String filePath) throws IOException {

  }

  @Override
  public void createNewProject(int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("A project's width or height cannot be less than or equal"
          + "to zero.");
    }

    this.width = width;
    this.height = height;
    this.projectName = "New Project";
  }

  @Override
  public void addLayer(String name) throws IllegalArgumentException {

  }

  @Override
  public void removeLayer(Layer layer) {

  }

  @Override
  public void setFilter(Filter filter, Layer layer) {

  }
}
