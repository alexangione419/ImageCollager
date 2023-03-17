package model.filters;

import model.Layer;

public class Darken_Luma implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    // modifies every pixel in the layer
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      int luma = (int) (.2126 * layerToModify[i][0]) +
          (int) (.7152 * layerToModify[i][1]) + (int) (.0722 * layerToModify[i][2]);

      for (int j = 0; j < 3; j++) {
        //adds max value to pixel without going over the cap
        if (layerToModify[i][j] - luma < 0) {
          layerToModify[i][j] = 0;
        } else {
          layerToModify[i][j] -= luma;
        }
      }
    }

    return layerToModify;

  }
}
