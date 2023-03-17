package model.filters;

import model.Layer;

public class Brighten_Value implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    // modifies every pixel in the layer
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      int max = Math.max(layerToModify[i][0], Math.max(layerToModify[i][1], layerToModify[i][2]));
      for (int j = 0; j < layerToModify.length - 1; j++) {
        //adds max value to pixel without going over the cap
        if (layerToModify[i][j] + max > layer.getMaxPixel()) {
          layerToModify[i][j] = layer.getMaxPixel();
        } else {
          layerToModify[i][j] += max;
        }
      }
    }

    return layerToModify;
  }
}
