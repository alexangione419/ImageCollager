package model.filters;

import model.Layer;

/**
 * A filter that displayed only the green component of an {@code ImageProject}.
 */
public class GreenComponent implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    for (int i = 0; i < layer.getTotalPixels(); i++) {
      layerToModify[i][0] = 0;
      layerToModify[i][2] = 0;
    }

    return layerToModify;
  }
}
