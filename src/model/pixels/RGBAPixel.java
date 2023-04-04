package model.pixels;

import java.util.Objects;

/**
 * Represents a pixel on a {@code ImageProject}. A {@code RGBAPixel} has a red, green, blue, and
 * alpha component.
 */
public final class RGBAPixel implements Pixel {

  private int red;
  private int green;
  private int blue;
  private double hue;
  private double saturation;
  private double lightness;
  private int alpha;
  private int maxPixelValue;

  /**
   * Constructs a new {@code RGBAPixel} with the given components. Alpha is set to the maximum pixel
   * value.
   *
   * @param maxPixelValue the maximum value that any of this RGBAPixel's components can have
   * @param red           the value of this {@code RGBAPixel}'s red component
   * @param green         the value of this {@code RGBAPixel}'s green component
   * @param blue          the value of this {@code RGBAPixel}'s blue component
   * @throws IllegalArgumentException if the model is null OR if red, green, or blue is less than 0
   */
  public RGBAPixel(int maxPixelValue, int red, int green, int blue)
          throws IllegalArgumentException {
    if (maxPixelValue <= 0) {
      throw new IllegalArgumentException("Max Pixel Value should be greater than 0.");
    }

    this.maxPixelValue = maxPixelValue;

    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("The values of the red, green, and blue component "
              + "must all be 0 or greater.");
    }

    if (red > this.maxPixelValue) {
      throw new IllegalArgumentException("This Pixel's red component cannot have a value greater "
              + "than " + this.maxPixelValue + ".");
    }

    if (green > this.maxPixelValue) {
      throw new IllegalArgumentException("This Pixel's green component cannot have a value greater "
              + "than " + this.maxPixelValue + ".");
    }

    if (blue > this.maxPixelValue) {
      throw new IllegalArgumentException("This Pixel's blue component cannot have a value greater "
              + "than " + this.maxPixelValue + ".");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;

    this.setHSL();

    this.alpha = this.maxPixelValue;
  }

  /**
   * Constructs a new {@code RGBAPixel} with the given components.
   *
   * @param maxPixelValue the maximum value that any of this RGBAPixel's components can have
   * @param red           the value of this {@code RGBAPixel}'s red component
   * @param green         the value of this {@code RGBAPixel}'s green component
   * @param blue          the value of this {@code RGBAPixel}'s blue component
   * @param alpha         the value of this {@code RGBAPixel}'s alpha component
   * @throws IllegalArgumentException if the model is null OR if red, green, blue, or alpha is less
   *                                  than 0
   */
  public RGBAPixel(int maxPixelValue, int red, int green, int blue, int alpha)
          throws IllegalArgumentException {
    this(maxPixelValue, red, green, blue);

    if (alpha > this.maxPixelValue) {
      throw new IllegalArgumentException(
              "This RGBAPixel's alpha component cannot have a value greater "
                      + "than " + this.maxPixelValue);
    }

    this.alpha = alpha;
  }

  /**
   * Sets the hue, saturation, and lightness values of this {@code Pixel}. The code in this method
   * is derived from the A5 sample code for converting RGBA to HSL.
   */
  private void setHSL() {
    double r = ((double) this.getRed() / (double) this.getMaxPixelValue());
    double g = ((double) this.getGreen() / (double) this.getMaxPixelValue());
    double b = ((double) this.getBlue() / (double) this.getMaxPixelValue());

    double componentMax = Math.max(r, Math.max(g, b));
    double componentMin = Math.min(r, Math.min(g, b));
    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;

    double hue;
    double saturation;

    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == r) {
        hue = (g - b) / delta;
        hue = hue % 6;
      } else if (componentMax == g) {
        hue = (b - r) / delta;
        hue += 2;
      } else if (componentMax == b) {
        hue = (r - g) / delta;
        hue += 4;
      }

      hue = hue * 60;

      if (hue < 0) {
        hue += 360;
      }
    }

    this.hue = hue;
    this.saturation = saturation;
    this.lightness = lightness;
  }

  @Override
  public int getRed() {
    return this.red;
  }

  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  @Override
  public double getHue() {
    return this.hue;
  }

  @Override
  public double getSaturation() {
    return this.saturation;
  }

  @Override
  public double getLight() {
    return this.lightness;
  }

  @Override
  public int getAlpha() {
    return this.alpha;
  }

  @Override
  public int getMaxPixelValue() {
    return this.maxPixelValue;
  }

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue + " " + this.alpha + " ";
  }

  @Override
  public String toStringRGB() {
    return this.red + " " + this.green + " " + this.blue + " ";
  }

  @Override
  public String toStringHSL() {
    return this.hue + " " + this.saturation + " " + this.lightness + " ";
  }

  @Override
  public boolean equals(Object o) {
    // If the object is compared with itself then return true
    if (o == this) {
      return true;
    }

    if (!(o instanceof RGBAPixel)) {
      return false;
    }

    RGBAPixel p = (RGBAPixel) o;

    // Compare the data members and return accordingly
    return Double.compare(this.red, p.red) == 0
            && Double.compare(this.blue, p.blue) == 0
            && Double.compare(this.green, p.green) == 0
            && Double.compare(this.alpha, p.alpha) == 0
            && Double.compare(this.maxPixelValue, p.maxPixelValue) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue,
            this.hue, this.saturation, this.lightness, this.alpha, this.maxPixelValue);
  }
}

