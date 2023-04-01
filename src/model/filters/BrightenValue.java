package model.filters;

import model.Layer;
import model.pixels.Pixel;

/**
 * A filter that brightens a {@code ImageProject} based on each pixel's greatest component.
 */
public class BrightenValue extends AbstractBrighten {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int max = Math.max(p.getRed(), Math.max(p.getGreen(), p.getBlue()));
        layerToModify[x][y] = super.brighten(max, p, layer);
      }
    }

    return layerToModify;
  }
}
