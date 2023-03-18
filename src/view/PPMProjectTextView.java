package view;

import java.io.IOException;
import model.ImageProject;

/**
 * A text-based implementation of {@code ImageProjectView}. This class allows anyone to view a
 * {@code ImageProject} model.
 */
public class PPMProjectTextView implements ImageProjectView {

  private ImageProject model;
  private Appendable output;

  /**
   * Constructs a {@code PPMProjectTextView} and sets output to {@code System.out}.
   *
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
   *
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
  public String currentCanvas() {
    String results = "";

    int endOfLineCounter = 0;
    int linesPassedCounter = 0;
    String prevActiveLayer = this.model.getActiveLayer().getName();

    //for every pixel
    for (int p = 0; p < this.model.getWidth() * this.model.getHeight(); p++) {

      double[] finalColor = new double[4];

      //for each layer
      for (int i = 0; i < this.model.getNumberOfLayers(); i++) {
        this.model.setActiveLayer(i);

        //get the data on the current active layer
        int[][] curLayerData = this.model.getActiveLayer().getLayerData();

        double curRed = curLayerData[p][0];
        double curGreen = curLayerData[p][1];
        double curBlue = curLayerData[p][2];
        double curAlpha = curLayerData[p][3];

        if ((i != 0) && (curAlpha != 0)) {

          double backgroundRed = finalColor[0];
          double backgroundGreen = finalColor[1];
          double backgroundBlue = finalColor[2];
          double backgroundAlpha = finalColor[3];

          double maxPixelVal = this.model.getMaxPixelValue();

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


        } else if ((i == 0) && (curAlpha != 0)) {
          finalColor[0] = curRed;
          finalColor[1] = curGreen;
          finalColor[2] = curBlue;
          finalColor[3] = curAlpha;
        }
      }

      for (int c = 0; c < 4; c++) {

        String componentRep = "";

        if (finalColor[c] > 100) {
          componentRep = String.valueOf((int) finalColor[c]);
        }
        else if ((finalColor[c] < 100) && (finalColor[c] >= 10)) {
          componentRep = String.valueOf("0" + (int) finalColor[c]);
        }
        else if (finalColor[c] < 10){
          componentRep = String.valueOf("00" + (int) finalColor[c]);
        }

        results = results.concat(componentRep);
        results = results.concat(" ");
      }

//      if (endOfLineCounter != this.model.getWidth()) {
//        results = results.concat(" ");
//      }

      endOfLineCounter++;

      if ((endOfLineCounter == this.model.getWidth()) &&
          (linesPassedCounter != (this.model.getHeight() - 1))) {
        endOfLineCounter = 0;
        linesPassedCounter++;
        results = results.concat("\n");
      }
    }

    this.model.setActiveLayer(prevActiveLayer);
    return results;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }


}
