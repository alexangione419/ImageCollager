package model.filters;

import model.Layer;

/**
 * A filter that displayed only the blue component of an {@code ImageProject}.
 */
public class BlueComponent implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    for (int i = 0; i < layer.getTotalPixels(); i++) {
      layerToModify[i][0] = 0;
      layerToModify[i][1] = 0;
    }

    return layerToModify;

  }
}
