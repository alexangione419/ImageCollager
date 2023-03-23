package model.pixels;

/**
 * Represents a pixel on a {@code ImageProject}. A {@code RGBAPixel} has a red, green, blue,
 * and alpha component.
 */
public final class RGBAPixel implements Pixel {

  private int red;
  private int green;
  private int blue;
  private int alpha;
  private int maxPixelValue;

  /**
   * Constructs a new {@code RGBAPixel} with the given components. Alpha is set to
   * the maximum pixel value.
   * @param maxPixelValue the maximum value that any of this RGBAPixel's components can have
   * @param red the value of this {@code RGBAPixel}'s red component
   * @param green the value of this {@code RGBAPixel}'s green component
   * @param blue the value of this {@code RGBAPixel}'s blue component
   * @throws IllegalArgumentException if the model is null OR if red, green, or blue is less than 0
   */
  public RGBAPixel(int maxPixelValue, int red, int green, int blue) throws IllegalArgumentException {
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

    this.alpha = this.maxPixelValue;
  }

  /**
   * Constructs a new {@code RGBAPixel} with the given components.
   * @param maxPixelValue the maximum value that any of this RGBAPixel's components can have
   * @param red the value of this {@code RGBAPixel}'s red component
   * @param green the value of this {@code RGBAPixel}'s green component
   * @param blue the value of this {@code RGBAPixel}'s blue component
   * @param alpha the value of this {@code RGBAPixel}'s alpha component
   * @throws IllegalArgumentException if the model is null OR if red, green, blue, or alpha
   *                                  is less than 0
   */
  public RGBAPixel(int maxPixelValue, int red, int green, int blue, int alpha)
      throws IllegalArgumentException {
    this(maxPixelValue, red, green, blue);

    if (alpha > this.maxPixelValue) {
      throw new IllegalArgumentException("This RGBAPixel's alpha component cannot have a value greater "
          + "than " + this.maxPixelValue);
    }

    this.alpha = alpha;
  }

  /**
   * Returns the red component of this {@code RGBAPixel}.
   * @return the red component of this RGBAPixel.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Returns the green component of this {@code RGBAPixel}.
   * @return the green component of this RGBAPixel.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Returns the blue component of this {@code RGBAPixel}.
   * @return the blue component of this RGBAPixel.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Returns the alpha component of this {@code RGBAPixel}.
   * @return the alpha component of this RGBAPixel.
   */
  public int getAlpha() {
    return this.alpha;
  }

  /**
   * Returns the max pixel value of this {@code RGBAPixel}.
   * @return the max pixel value of this RGBAPixel.
   */
  public int getMaxPixelValue() {
    return this.maxPixelValue;
  }

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue + " " +this.alpha + " ";
  }
}
