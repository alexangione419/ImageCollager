package model.pixels;

import model.Layer;

/**
 * A class for converting {@code Pixel}s from one format into another.
 */
public final class PixelUtils {

  /**
   * Takes the given RGBAPixel and returns back a RGBAPixel with the alpha component removed.
   * @param p the pixel to convert
   * @return a new RGBAPixel with the alpha component removed.
   */
  public static RGBAPixel convertRGBAtoRGB(Pixel p) {
    double r = p.getRed() * ((double) p.getAlpha() / p.getMaxPixelValue());
    double g = p.getGreen() * ((double) p.getAlpha() / p.getMaxPixelValue());
    double b = p.getBlue() * ((double) p.getAlpha() / p.getMaxPixelValue());

    return new RGBAPixel(p.getMaxPixelValue(), (int) r, (int) g, (int) b, p.getMaxPixelValue());
  }

  /**
   * This method takes the components of the given {@code HSLPixel} and creates a new
   * {@code RGBAPixel} based off those values. This code was taken from the
   * RGB to HSL conversion code atop of the A5 page.
   * @param hue the hue value (0-360)
   * @param saturation the saturation value (0.0-1.0)
   * @param light the lightness value (0.0-1.0)
   * @return a new {@code HSLPixel} based on the given {@code HSLPixel}'s components
   */
  public static RGBAPixel convertHSLtoRGBA(double hue, double saturation, double light) {
    double r = convertFn(hue, saturation, light, 0) * 255;
    double g = convertFn(hue, saturation, light, 8) * 255;
    double b = convertFn(hue, saturation, light, 4) * 255;

    return new RGBAPixel(255, (int) r, (int) g, (int) b, 255);
  }

  /**
   * Helper method that performs the translation from the HSL polygonal model to the more familiar
   * RGB model. This code was taken from the RGB to HSL conversion code atop of the A5 page.
   */
  private static double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue / 30)) % 12;
    double a = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

  /**
   * Loops every layer to return back the color that will be displayed in the final image.
   *
   * @param x the x-coordinate of the pixel to look at
   * @param y the y-coordinate of the pixel to look at
   * @return a Pixel containing the RGBA values of the final color
   */
  public static Pixel finalColorAt(int x, int y, double maxPixelVal, Layer[] layers) {

    double[] finalColor = new double[4];

    for (int i = 0; i < layers.length; i++) {


      Pixel[][] curLayerData = layers[i].getLayerData();

      double curRed = curLayerData[x][y].getRed();
      double curGreen = curLayerData[x][y].getGreen();
      double curBlue = curLayerData[x][y].getBlue();
      double curAlpha = curLayerData[x][y].getAlpha();

      if ((i != 0) && (curAlpha != 0)) {
        double backgroundRed = finalColor[0];
        double backgroundGreen = finalColor[1];
        double backgroundBlue = finalColor[2];
        double backgroundAlpha = finalColor[3];


        double alphaPercentage = ((curAlpha / maxPixelVal) + backgroundAlpha / maxPixelVal * (1
            - (curAlpha / maxPixelVal)));

        finalColor[3] = (alphaPercentage * maxPixelVal);

        finalColor[0] = (curAlpha / maxPixelVal * curRed + backgroundRed
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);

        finalColor[1] = (curAlpha / maxPixelVal * curGreen + backgroundGreen
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);

        finalColor[2] = (curAlpha / maxPixelVal * curBlue + backgroundBlue
            * (backgroundAlpha / maxPixelVal)
            * (1 - curAlpha / maxPixelVal)) * (1 / alphaPercentage);

      } else if ((i == 0) && (curAlpha != 0)) {
        finalColor[0] = curRed;
        finalColor[1] = curGreen;
        finalColor[2] = curBlue;
        finalColor[3] = curAlpha;
      }
    }

    RGBAPixel finalPixel = new RGBAPixel((int) maxPixelVal,
        (int) finalColor[0], (int) finalColor[1], (int) finalColor[2], (int) finalColor[3]);

    //return PixelUtils.convertRGBAtoRGB(finalPixel);
    return finalPixel;
  }


}
