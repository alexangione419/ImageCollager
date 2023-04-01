package model.filters;

import model.Layer;
import model.pixels.Pixel;

/**
 * A filter darkens a {@code ImageProject} based on each pixel's intensity.
 */
public class DarkenIntensity extends AbstractDarken {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int sum = p.getRed() + p.getGreen() + p.getBlue();
        int ave = sum / 3;

        layerToModify[x][y] = super.darken(ave, p, layer);
      }
    }

    return layerToModify;
  }
}
