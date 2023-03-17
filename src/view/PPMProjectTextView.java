package view;

import java.io.IOException;
import model.ImageProject;

/**
 * A text-based implementation of {@code ImageProjectView}. This class allows anyone to view
 * a {@code ImageProject} model.
 */
public class PPMProjectTextView implements ImageProjectView {

  private ImageProject model;
  private Appendable output;

  /**
   * Constructs a {@code PPMProjectTextView} and sets output to {@code System.out}.
   * @param m the {@code ImageProject} to view
   */
  public PPMProjectTextView(ImageProject m) {
    if (m == null) {
      throw new IllegalArgumentException("ImageProject cannot be null.");
    }

    this.model = m;
    this.output = System.out;
  }

  /**
   * Constructs a new {@code PPMProjectTextView} with a specified {@code Appendable}.
   * @param m the {@code ImageProject} to view
   * @param a the {@code Appendable} to write to
   */
  public PPMProjectTextView(ImageProject m, Appendable a) {
    if ((m == null) || (a == null)) {
      throw new IllegalArgumentException("ImageProject and/or Appendable cannot be null.");
    }

    this.model = m;
    this.output = a;
  }

  @Override
  public String currentCanvas() throws IOException {
    String results = "";

    for (int i = 0; i < this.model.getNumberOfLayers(); i++) {
      this.model.setActiveLayer(i);

      int[][] temp = this.model.getActiveLayer().getLayerData();

      int endOfLineCounter = 0;
      int linesPassedCounter = 0;

      for (int l = 0; l < this.model.getWidth() * this.model.getHeight(); l++) {

        for (int c = 0; c < 4; c++) {
          results = results.concat(String.valueOf(temp[l][c]));
          results = results.concat(" ");
        }

        if (endOfLineCounter != this.model.getWidth()) {
          results = results.concat(" ");
        }

        endOfLineCounter++;

        if ((endOfLineCounter == this.model.getWidth()) &&
            (linesPassedCounter != (this.model.getHeight() - 1))) {
          endOfLineCounter = 0;
          linesPassedCounter++;
          results = results.concat("\n");
        }
      }
    }

    return results;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }


}
