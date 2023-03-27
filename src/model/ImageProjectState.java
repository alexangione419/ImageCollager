package model;

/**
 * An interface containing the various state functions needed in an Image Project. This interface is
 * separate from the Image Project interface so that sections of code that rely on having access to
 * the model only have access to getters relating to the state of the model.
 */
public interface ImageProjectState {

  /**
   * Returns the name of this {@code ImageProject}.
   *
   * @return the name of this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  String getName() throws IllegalStateException;

  /**
   * Returns the width size of this {@code ImageProject}.
   *
   * @return the width of this this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getWidth() throws IllegalStateException;

  /**
   * Returns the height size of this {@code ImageProject}.
   *
   * @return the height of this this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns the number of layers this {@code ImageProject} has.
   *
   * @return the number of layers in this {@code ImageProject}
   * @throws IllegalStateException if this {@code ImageProject} doesn't have a loaded project
   */
  int getNumberOfLayers() throws IllegalStateException;

  /**
   * Returns the maximum value allowed for a pixel.
   *
   * @return the maximum value of a pixel
   */
  int getMaxPixelValue();

}
