package controller;

import java.io.IOException;

/**
 * A controller for {@code ImageProject}s. This interface allows users to manipulate
 * {@code ImageProject}s.
 */
public interface ImageProjectController {

  /**
   * Starts the process to allows a user to control a {@code ImageProject}.
   *
   * @throws IllegalStateException if the controller is unable to read input or transmit output
   */
  void start() throws IllegalStateException;


  /**
   * Saves the final image created from all the layers with the given name as an image file.
   *
   * @param name the name to give the image file
   * @throws IllegalArgumentException if the name is invalid (null or is empty)
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   * @throws IOException              if there is an issue writing to a file
   */
  void saveImage(String name) throws IllegalStateException, IllegalArgumentException, IOException;

  /**
   * Saves this {@code ImageProject} as a .collage file with the given file name. The .collage file
   * should contain the project width and height, as well as the contents, name, and filter of each
   * layer.
   *
   * @throws IllegalArgumentException if the name is invalid (null or is empty)
   * @throws IllegalStateException    if this {@code ImageProject} doesn't have a loaded project
   */
  void saveProject(String name) throws IllegalStateException, IllegalArgumentException, IOException;

  /**
   * Loads a project at a given file path.
   *
   * @param filePath the file path of the file being loaded
   * @throws IllegalArgumentException if file at the given file path is not a .collage file OR the
   *                                  file does not follow the .collage file format
   */
  void loadProject(String filePath) throws IllegalArgumentException;

}
