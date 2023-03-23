package model.pixels;

/**
 * An interface for Pixels that appear on {@code ImageProject}. Since Pixels can have different
 * components (RGBA, HSL, etc.), this interface provides a way to convert {@code Pixel}s from one
 * format to another.
 */
public interface Pixel {

  /**
   * Returns the alpha component of this {@code Pixel}.
   * @return the alpha component of this {@code Pixel}.
   */
  int getAlpha();

  /**
   * Returns a String containing all of this {@code Pixel}'s components seperated by a spaces.
   * @return a String representation of this {@code Pixel}.
   */
  String toString();

}
