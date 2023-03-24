package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter darkens a {@code ImageProject} based on each pixel's intensity.
 */
public class DarkenIntensity implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int sum = p.getRed() + p.getGreen() + p.getBlue();
        int ave = sum / 3;
        int r = Clamp.execute(p.getRed() - ave, 0, layer.getMaxPixel());
        int g = Clamp.execute(p.getGreen() - ave, 0, layer.getMaxPixel());
        int b = Clamp.execute(p.getBlue() - ave, 0, layer.getMaxPixel());

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
      }
    }

    return layerToModify;
  }
}
