package model.pixels;

/**
 * An interface for Pixels that appear on {@code ImageProject}. Since Pixels can have different
 * components (RGBA, HSL, etc.), this interface provides a way to convert {@code Pixel}s from one
 * format to another.
 */
public interface Pixel {

  /**
   * Returns the max pixel value of this {@code Pixel}.
   *
   * @return the max pixel value of this {@code Pixel}
   */
  int getMaxPixelValue();

  /**
   * Returns the alpha component of this {@code Pixel}.
   *
   * @return the alpha component of this {@code Pixel}
   */
  int getAlpha();

  /**
   * Returns the red component of this {@code Pixel}.
   *
   * @return the red component of this {@code Pixel}
   */
  int getRed();

  /**
   * Returns the green component of this {@code Pixel}.
   *
   * @return the green component of this {@code Pixel}
   */
  int getGreen();

  /**
   * Returns the blue component of this {@code Pixel}.
   *
   * @return the blue component of this {@code Pixel}
   */
  int getBlue();

  /**
   * Returns the hue value of this {@code Pixel} based off its RGB values.
   *
   * @return the hue value of this {@code Pixel}
   */
  double getHue();

  /**
   * Returns the saturation value of this {@code Pixel} based off its RGB values.
   *
   * @return the saturation value of this {@code Pixel}
   */
  double getSaturation();

  /**
   * Returns the lightness value of this {@code Pixel} based off its RGB values.
   *
   * @return the lightness value of this {@code Pixel}
   */
  double getLight();

  /**
   * Returns a String containing this {@code Pixel}'s RGBA components seperated by a spaces.
   *
   * @return a String representation of this {@code Pixel}
   */
  String toString();

  /**
   * Returns a String containing this {@code Pixel}'s RGB components seperated by a spaces.
   *
   * @return a String representation of this {@code Pixel}
   */
  String toStringRGB();

  /**
   * Returns a String containing this {@code Pixel}'s HSL components seperated by a spaces.
   *
   * @return a String representation of this {@code Pixel}
   */
  String toStringHSL();

}
