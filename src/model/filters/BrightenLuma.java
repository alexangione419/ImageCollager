package model.filters;

import model.Clamp;
import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter that brightens a {@code ImageProject} based on the luma value.
 */
public class BrightenLuma implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int luma = (int) ((0.2126 * p.getRed()) + (0.7152 * p.getGreen()) + (0.0722 * p.getBlue()));

        int r = Clamp.execute(p.getRed() + luma, 0, layer.getMaxPixel());
        int g = Clamp.execute(p.getGreen() + luma, 0, layer.getMaxPixel());
        int b = Clamp.execute(p.getBlue() + luma, 0, layer.getMaxPixel());

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
      }
    }

    return layerToModify;

  }
}
