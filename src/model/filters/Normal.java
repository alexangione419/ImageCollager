package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * A filter that undoes any filters added.
 */
public class Normal implements Filter {

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getUnfilteredLayer();

    Pixel[][] result = new Pixel[layerToModify.length][layerToModify[0].length];

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {
        Pixel pixelToCopy = layerToModify[x][y];
        result[x][y] = new RGBAPixel(pixelToCopy.getMaxPixelValue(),
            pixelToCopy.getRed(), pixelToCopy.getGreen(), pixelToCopy.getBlue(),
            pixelToCopy.getAlpha());
      }
    }

    return layer.getUnfilteredLayer();
  }
}
