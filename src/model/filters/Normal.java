package model.filters;

import model.Layer;

public class Normal implements Filter{
  @Override
  public int[][] apply(Layer layer) {
    return layer.getLayerData();
  }
}
