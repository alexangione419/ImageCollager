package model.filters;

import model.Layer;

public class Red_Component implements Filter {
  @Override
  public int[][] apply(Layer layer) {
    return layer.getLayerData();
  }
}
