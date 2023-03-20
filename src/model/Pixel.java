package model;

/**
 * Represents a pixel on a {@code ImageProject}. A {@code Pixel} has a red, green, blue,
 * and alpha component.
 */
public final class Pixel {

  private final int red;
  private final int green;
  private final int blue;
  private int alpha;

  /**
   * Constructs a new {@code Pixel} with the given components. Alpha is set to
   * the maximum pixel value.
   * @param model the model that this Pixel is in.
   * @param red the value of this {@code Pixel}'s red component
   * @param green the value of this {@code Pixel}'s green component
   * @param blue the value of this {@code Pixel}'s blue component
   */
  public Pixel(ImageProject model, int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = model.getMaxPixelValue();
  }

  /**
   * Constructs a new {@code Pixel} with the given components.
   * @param model the model that this Pixel is in.
   * @param red the value of this {@code Pixel}'s red component
   * @param green the value of this {@code Pixel}'s green component
   * @param blue the value of this {@code Pixel}'s blue component
   * @param alpha the value of this {@code Pixel}'s alpha component
   */
  public Pixel(ImageProject model, int red, int green, int blue, int alpha) {
    this(model, red, green, blue);
    this.alpha = alpha;
  }

  /**
   * Returns the red component of this {@code Pixel}.
   * @return the red component of this Pixel.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Returns the green component of this {@code Pixel}.
   * @return the green component of this Pixel.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Returns the blue component of this {@code Pixel}.
   * @return the blue component of this Pixel.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Returns the alpha component of this {@code Pixel}.
   * @return the alpha component of this Pixel.
   */
  public int getAlpha() {
    return this.alpha;
  }

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue + this.alpha + " ";
  }

}
