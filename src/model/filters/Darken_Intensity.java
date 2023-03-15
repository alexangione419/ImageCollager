package model.filters;

import model.Layer;

public class Darken_Intensity implements Filter {
  @Override
  public int[][] apply(Layer layer) {
    return layer.getLayerData();
  }
}
