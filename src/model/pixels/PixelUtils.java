package model.pixels;

/**
 * A class for converting {@code Pixel}s from one format into another.
 */
public final class PixelUtils {

//  /**
//   * This method takes the components of the given {@code RGBAPixel} and creates a new
//   * {@code HSLPixel} based off those values. This code was taken from the
//   * RGB to HSL conversion code atop of the A5 page.
//   * @param p the pixel to convert
//   * @return a new {@code HSLPixel} based on the given {@code RGBAPixel}'s components
//   */
//  public static HSLPixel convertRGBAtoHSL(RGBAPixel p) {
//    double r = ((double) p.getRed() / (double) p.getMaxPixelValue());
//    double g = ((double) p.getGreen() / (double) p.getMaxPixelValue());
//    double b = ((double) p.getBlue() / (double) p.getMaxPixelValue());
//
//    double componentMax = Math.max(r, Math.max(g, b));
//    double componentMin = Math.min(r, Math.min(g, b));
//    double delta = componentMax - componentMin;
//
//    double lightness = (componentMax + componentMin) / 2;
//
//    double hue, saturation;
//
//    if (delta == 0) {
//      hue = 0;
//      saturation = 0;
//    } else {
//      saturation = delta / (1 - Math.abs(2 * lightness - 1));
//      hue = 0;
//      if (componentMax == r) {
//        hue = (g - b) / delta;
//        hue = hue % 6;
//      } else if (componentMax == g) {
//        hue = (b - r) / delta;
//        hue += 2;
//      } else if (componentMax == b) {
//        hue = (r - g) / delta;
//        hue += 4;
//      }
//
//      hue = hue * 60;
//    }
//
//    return new HSLPixel(hue, saturation, lightness);
//  }
//
//  /**
//   * This method takes the components of the given {@code HSLPixel} and creates a new
//   * {@code RGBAPixel} based off those values. This code was taken from the
//   * RGB to HSL conversion code atop of the A5 page.
//   * @param p the pixel to convert
//   * @return a new {@code HSLPixel} based on the given {@code HSLPixel}'s components
//   */
//  public static RGBAPixel convertHSLtoRGBA(HSLPixel p) {
//    double r = convertFn(p.getHue(), p.getSaturation(), p.getLight(), 0) * 255;
//    double g = convertFn(p.getHue(), p.getSaturation(), p.getLight(), 8) * 255;
//    double b = convertFn(p.getHue(), p.getSaturation(), p.getLight(), 4) * 255;
//
//    return new RGBAPixel(255, (int) r, (int) g, (int) b, 255);
//  }

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
