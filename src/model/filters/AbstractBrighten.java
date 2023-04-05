package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

/**
 * An abstract class for filters that brighten an image.
 */
public abstract class AbstractBrighten implements Filter {

  /**
   * Adds the given valToBrighten to the given {@code Pixel}'s components.
   * @param valToBrighten the amount to brighten by
   * @param p the pixel to modify
   * @param layer the layer that the {@code Pixel} resides in
   * @return the newly brightened {@code Pixel}
   */
  public Pixel brighten(int valToBrighten, Pixel p, Layer layer) {
    int r = Clamp.execute(p.getRed() + valToBrighten, 0, layer.getMaxPixel());
    int g = Clamp.execute(p.getGreen() + valToBrighten, 0, layer.getMaxPixel());
    int b = Clamp.execute(p.getBlue() + valToBrighten, 0, layer.getMaxPixel());

    return new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
  }
}
