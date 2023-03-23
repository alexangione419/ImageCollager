package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter that displayed only the blue component of an {@code ImageProject}.
 */
public class BlueComponent implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(),
            0, 0, p.getBlue(), p.getAlpha());
      }
    }

    return layerToModify;

  }
}
