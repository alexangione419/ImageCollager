package model.filters;

import model.Layer;

public class Blue_Component implements Filter {

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
