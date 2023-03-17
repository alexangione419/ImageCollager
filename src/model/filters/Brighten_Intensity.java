package model.filters;

import model.Layer;

public class Brighten_Intensity implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    // modifies every pixel in the layer
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      int sum = layerToModify[i][0] + layerToModify[i][1] + layerToModify[i][2];
      int ave = sum / 3;

      for (int j = 0; j < 3; j++) {
        //adds max value to pixel without going over the cap
        if (layerToModify[i][j] + ave > layer.getMaxPixel()) {
          layerToModify[i][j] = layer.getMaxPixel();
        } else {
          layerToModify[i][j] += ave;
        }
      }
    }

    return layerToModify;
  }

}
