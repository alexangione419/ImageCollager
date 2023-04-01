package model.filters;

import model.Layer;
import model.pixels.Pixel;

/**
 * A filter that darkens a {@code ImageProject} based on the luma value.
 */
public class DarkenLuma extends AbstractDarken {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel p = layerToModify[x][y];

        int luma = (int) ((0.2126 * p.getRed()) + (0.7152 * p.getGreen()) + (0.0722 * p.getBlue()));
        layerToModify[x][y] = super.darken(luma, p, layer);
      }
    }

    return layerToModify;

  }
}
