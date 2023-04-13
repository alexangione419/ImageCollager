package view;

import controller.Features;

/**
 * Represents an interface for a GUI view of an image processor.
 */
public interface ImageProjectGUIView {

  /**
   * Allows for controller features to added to the GUI. Creates the bridge between user giving the
   * view input and the controller acting on those inputs.
   * @param features the class that will be able to run the desired features
   */
  void addFeatures(Features features);

  /**
   * The method that displays the main screen of the GUI, including the image, the layer
   * information, and all the command buttons. It is used by the controller to update the view
   * whenever the model is changed.
   */
  void runMainGUI();

  /**
   * A method used by the controller to inform the GUI when bad input has been received and an error
   * message should be shown to the user.
   * @param ex the message given by the error
   */
  public void throwBadInput(String ex);

}
