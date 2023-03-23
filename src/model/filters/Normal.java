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
    return layer.getUnfilteredLayer();
  }
}
