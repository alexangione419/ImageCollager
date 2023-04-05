package model.filters;

import java.util.List;
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


  /**
   * Returns an array of {@code Layer}s omitting the layer's whose name is the same as.
   * layerToIgnore.
   * @param layers the list of layers
   * @param layerToIgnore the layer to omit from creating the composite image
   * @return an array of {@code Layer}s omitting the layer's whose name is the same as.
   */
  static Layer[] getCompositeImage(List<Layer> layers, String layerToIgnore) {
    Layer[] bottom = new Layer[layers.size() - 1];

    for (int i = 0; i < layers.size() - 1; i++) {
      if (!layers.get(i).getName().equals(layerToIgnore)) {
        bottom[i] = layers.get(i);
      }
    }

    return bottom;
  }
}
