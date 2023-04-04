package controller;

import java.io.IOException;

public interface Features {
  void addImageToLayer(String layer, String image, int x, int y) throws IllegalArgumentException;
  void addLayer(String layerName) throws IllegalArgumentException, IllegalStateException;
  void loadProject(String filepath) throws IllegalArgumentException;
  void saveProject(String name);
  void saveImage(String name);
  void newProject(int width, int height);
  void setFilter(String filterName, String layerName) throws IllegalStateException,
          IllegalArgumentException ;
  void exit();


}
