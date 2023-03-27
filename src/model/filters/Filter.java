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
   * @param layer the layer to apply the filter to
   * @return the modified layer
   */
  Pixel[][] apply(Layer layer);
}
