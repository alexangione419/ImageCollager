package model.pixels;

/**
 * A class for converting {@code Pixel}s from one format into another.
 */
public final class PixelUtils {

  /**
   * Takes the given RGBAPixel and returns back a RGBAPixel with the alpha component removed.
   * @param p the pixel to convert
   * @return a new RGBAPixel with the alpha component removed.
   */
  public static RGBAPixel convertRGBAtoRGB(RGBAPixel p) {
    double r = p.getRed() * ((double) p.getAlpha() / p.getMaxPixelValue());
    double g = p.getGreen() * ((double) p.getAlpha() / p.getMaxPixelValue());
    double b = p.getBlue() * ((double) p.getAlpha() / p.getMaxPixelValue());

    return new RGBAPixel(p.getMaxPixelValue(), (int) r, (int) g, (int) b, p.getMaxPixelValue());
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


}
