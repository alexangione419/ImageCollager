package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * An abstract class for filters that darken each pixel on a layer.
 */
public abstract class AbstractDarken implements Filter {

  /**
   * Subtracts the given valToDarken to the given {@code Pixel}'s components.
   * @param valToDarken the amount to darkened by
   * @param p the pixel to modify
   * @param layer the layer that the {@code Pixel} resides in
   * @return the newly darkened {@code Pixel}
   */
  public Pixel darken(int valToDarken, Pixel p, Layer layer) {
    int r = Clamp.execute(p.getRed() - valToDarken, 0, layer.getMaxPixel());
    int g = Clamp.execute(p.getGreen() - valToDarken, 0, layer.getMaxPixel());
    int b = Clamp.execute(p.getBlue() - valToDarken, 0, layer.getMaxPixel());

    return new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
  }
}
