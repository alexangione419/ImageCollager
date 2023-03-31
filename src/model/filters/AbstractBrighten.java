package model.filters;

import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;

public abstract class AbstractBrighten implements Filter{
  public Pixel brighten(int valToDarken, Pixel p, Layer layer) {
    int r = Clamp.execute(p.getRed() + valToDarken, 0, layer.getMaxPixel());
    int g = Clamp.execute(p.getGreen() + valToDarken, 0, layer.getMaxPixel());
    int b = Clamp.execute(p.getBlue() + valToDarken, 0, layer.getMaxPixel());

    return new RGBAPixel(layer.getMaxPixel(), r, g, b, p.getAlpha());
  }
}
