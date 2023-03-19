package model.filters;

import model.Layer;

/**
 * A filter that darken a {@code ImageProject} based on each pixel's greatest component.
 */
public class DarkenValue implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    // modifies every pixel in the layer
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      int max = Math.max(layerToModify[i][0], Math.max(layerToModify[i][1], layerToModify[i][2]));
      for (int j = 0; j < 3; j++) {
        //adds max value to pixel without going over the cap
        if (layerToModify[i][j] - max < 0) {
          layerToModify[i][j] = 0;
        } else {
          layerToModify[i][j] -= max;
        }
      }
    }

    return layerToModify;
  }
}