package model.filters;

import model.Layer;

public class Brighten_Luma implements Filter{
  @Override
  public int[][] apply(Layer layer) {
    return layer.getLayerData();

  }
}
