package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter that displayed only the red component of an {@code ImageProject}.
 */
public class RedComponent implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(),
            p.getRed(), 0, 0, p.getAlpha());
      }
    }

    return layerToModify;
  }
}
