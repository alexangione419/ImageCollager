package model.filters;

import model.Clamp;
import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter that darken a {@code ImageProject} based on each pixel's greatest component.
 */
public class DarkenValue implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int max = Math.max(p.getRed(), Math.max(p.getGreen(), p.getBlue()));

        int r = Clamp.execute(p.getRed() - max, 0, layer.getMaxPixel());
        int g = Clamp.execute(p.getGreen() - max, 0, layer.getMaxPixel());
        int b = Clamp.execute(p.getBlue() - max, 0, layer.getMaxPixel());

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
      }
    }

    return layerToModify;
  }
}
