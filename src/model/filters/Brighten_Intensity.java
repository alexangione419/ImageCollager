package model.filters;

import model.Layer;

public class Brighten_Intensity implements Filter{
  @Override
  public int[][] apply(Layer layer) {
    return layer.getLayerData();

  }
}
