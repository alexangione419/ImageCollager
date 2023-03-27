package model.filters;

import java.util.List;
import model.Layer;
import model.LayerImpl;
import model.pixels.Pixel;
import model.pixels.PixelUtils;
import model.pixels.RGBAPixel;

/**
 * A filter that takes the difference in RGB values on the given layer with the rest of the
 * layers underneath.
 */
public class Difference implements Filter {

  List<Layer> layers;

  public Difference(List<Layer> layers) {
    this.layers = layers;
  }

  @Override
  public Pixel[][] apply(Layer layer) {
    Pixel[][] layerToModify = layer.getLayerData();

    Layer[] bottom = new Layer[this.layers.size() - 1];

    for (int i = 0; i < this.layers.size() - 1; i++) {
      if (!this.layers.get(i).getName().equals(layer.getName())) {
        bottom[i] = this.layers.get(i);
      }
    }

    for (int y = 0; y < layerToModify[0].length; y++) {
      for (int x = 0; x < layerToModify.length; x++) {

        Pixel p = layerToModify[x][y];
        Pixel p2 = PixelUtils.finalColorAt(x, y, layer.getMaxPixel(), bottom);

        layerToModify[x][y] = new RGBAPixel(layer.getMaxPixel(),
            Math.abs(p.getRed() - p2.getRed()),
            Math.abs(p.getGreen() - p2.getGreen()),
            Math.abs(p.getBlue() - p2.getBlue()),
            p.getAlpha());
      }
    }

    return layerToModify;
  }
}
