package model.filters;

import model.Layer;

/**
 * A filter that darkens a {@code ImageProject} based on the luma value.
 */
public class DarkenLuma implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] layerToModify = layer.getLayerData();

    // modifies every pixel in the layer
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      int luma = (int) (.2126 * layerToModify[i][0]
          + (.7152 * layerToModify[i][1]) + (.0722 * layerToModify[i][2]));

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
