package model.filters;

import java.util.List;
import model.Layer;
import model.pixels.Pixel;
import model.pixels.PixelUtils;

/**
 * A filter that takes the HSL values of each pixel on a layer and multiplies the lightness levels
 * with the pixels on the composite image underneath to brighten the image.
 */
public class Screen implements Filter {

  List<Layer> layers;

  /**
   * Constructs a new {@code Screen} and stores the given List of layers.
   * @param layers the layers of the model
   */
  public Screen(List<Layer> layers) {
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
            (1 - ((1 - p.getLight()) * (1 - p2.getLight()))));
      }
    }

    return layerToModify;
  }
}
