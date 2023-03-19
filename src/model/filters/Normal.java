package model.filters;

import model.Layer;

/**
 * A filter that undoes any filters added.
 */
public class Normal implements Filter {

  @Override
  public int[][] apply(Layer layer) {
    int[][] newUnfiltered = new int[layer.getTotalPixels()][4];
    for (int i = 0; i < layer.getTotalPixels(); i++) {
      newUnfiltered[i][0] = layer.getUnfilteredLayer()[i][0];
      newUnfiltered[i][1] = layer.getUnfilteredLayer()[i][1];
      newUnfiltered[i][2] = layer.getUnfilteredLayer()[i][2];
      newUnfiltered[i][3] = layer.getUnfilteredLayer()[i][3];
    }
    return newUnfiltered;


  }
}
