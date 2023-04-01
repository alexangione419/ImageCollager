package model.filters;

import java.util.List;
import model.Layer;
import model.pixels.Pixel;
import model.pixels.PixelUtils;

/**
 * A filter that takes the HSL values of each pixel on a layer and multiplies the lightness levels
 * with the pixels on the composite image underneath to darken the image.
 */
public class Multiply implements Filter {

  List<Layer> layers;

  /**
   * Constructs a new {@code Multiply} and stores the given List of layers.
   * @param layers the layers of the model
   */
  public Multiply(List<Layer> layers) {
    this.layers = layers;
  }

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    Layer[] bottom = Filter.getCompositeImage(this.layers, layer.getName());

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {

        Pixel p = layerToModify[x][y];
        Pixel p2 = PixelUtils.finalColorAt(x, y, layer.getMaxPixel(), bottom);

        layerToModify[x][y] = PixelUtils.convertHSLtoRGBA(
            p.getHue(),
            p.getSaturation(),
            p.getLight() * p2.getLight());
      }
    }

    return layerToModify;
  }
}
