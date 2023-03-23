package model.filters;

import model.Layer;
import model.pixels.Pixel;

/**
 * A filter to be applied to an {@code ImageProject} or a {@code Layer}. Filters affect the images
 * in their own unique ways.
 */
public interface Filter {

  /**
   * Applies this filter to a {@code Layer} within a {@code ImageProject} once.
   *
   * @param layer the player to apply the filter to
   */
  Pixel[][] apply(Layer layer);
}
