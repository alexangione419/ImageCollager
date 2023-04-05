package controller;

public interface Features {
  void addImageToLayer(String layer, String image, int x, int y);
  void addLayer(String layerName);
  void loadProject(String filepath);
  void saveProject(String name);
  void saveImage(String name);
  void newProject(int width, int height);
  void setFilter(String filterName, String layerName);
  void exit();


}
