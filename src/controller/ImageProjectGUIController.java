package controller;

import view.ImageProjectGUIView;

/**
 * Represents a controller specifically for a GUI view for an image project.
 */
public interface ImageProjectGUIController extends Features {

  /**
   * Allows the controller to initialize the GUI view.
   * @param view the GUI to run
   * @throws IllegalStateException if the provided view is null
   */
  void start(ImageProjectGUIView view) throws IllegalStateException;
  
  
}
