package model.pixels;

/**
 * Represents a pixel on a {@code ImageProject}. A {@code HSLPixel} has three components: a
 * hue, saturation, and light component.
 */
public final class HSLPixel implements Pixel {

  private double hue;
  private double saturation;
  private double light;


  public HSLPixel(double hue, double saturation, double light) {

    if (hue < 0 || hue > 360) {
      throw new IllegalArgumentException("Hue value cannot be less than 0 or more than 360.");
    }

    if (saturation < 0 || saturation > 1) {
      throw new IllegalArgumentException("Saturation value cannot be less than 0 or more than 1.");
    }

    if (light < 0 || light > 1) {
      throw new IllegalArgumentException("Light value cannot be less than 0 or more than 1.");
    }

    this.hue = hue;
    this.saturation = saturation;
    this.light = light;
  }

  /**
   * Returns the hue component of this {@code HSLPixel}.
   * @return the hue component of this {@code HSLPixel}.
   */
  public double getHue() {
    return this.hue;
  }

  /**
   * Returns the saturation component of this {@code HSLPixel}.
   * @return the saturation component of this {@code HSLPixel}.
   */
  public double getSaturation() {
    return this.saturation;
  }

  /**
   * Returns the light component of this {@code HSLPixel}.
   * @return the light component of this {@code HSLPixel}.
   */
  public double getLight() {
    return this.light;
  }

  @Override
  public int getAlpha() {
    return 255;
  }

  @Override
  public String toString() {
    return this.hue + " " + this.saturation + " " + this.light + " ";
  }

}
