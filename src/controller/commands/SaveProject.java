package controller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import model.ImageProjectFileUtils;
import model.Layer;
import model.pixels.Pixel;
import view.ImageProjectView;

/**
 * A command object that saves the currently opened {@code ImageProject} to a .collage file.
 */
public final class SaveProject extends ACommand {

  private Scanner sc;

  /**
   * Constructs a new {@code SaveProject}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public SaveProject(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  /**
   * Constructs a new {@code SaveProject} with no view or scanner.
   * This constructor should be used to use the save method directly.
   * @param model the model to use
   */
  public SaveProject(ImageProject model) {
    super(model, null);
  }

  @Override
  public void run() {
    if (!this.model.hasOpenProject()) {
      try {
        this.view.renderMessage("Please create or open a new project to use that command.\n");
      } catch (IOException e) {
        //ignore
      }
    }

    // Make sure there is input to read
    if (!sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String fileName = this.sc.next();

    try {
      try {
        this.save(fileName);
        this.view.renderMessage("Project was saved to as " + fileName + ".collage.\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The name for the file is invalid. It must not contain any periods."
            + " Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }

  /**
   * Creates a .collage file with the given name, if valid. The file contains each layer
   * present on the model, along with the name of the layer, its filter, and the RGBA values
   * of each pixel present on the layer.
   * @param fileName the name to give the project file
   * @throws IOException if the method is unable to create or write to the project file.
   */
  public void save(String fileName) throws IOException {
    if (!this.model.hasOpenProject()) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(fileName)) {

      String output = fileName + "\n"
          + this.model.getWidth() + " " + this.model.getHeight() + "\n";

      Layer[] layers = new Layer[this.model.getNumberOfLayers() - 1];
      String prevActiveLayerName = this.model.getActiveLayer().getName();

      for (int i = 0; i < this.model.getNumberOfLayers(); i++) {
        this.model.setActiveLayer(i);
        layers[i] = this.model.getActiveLayer();
      }

      this.model.setActiveLayer(prevActiveLayerName);

      for (Layer currLayer : layers) {
        output = output.concat(currLayer.getName() + " " + currLayer.getFilter() + "\n");

        int i = 0;

        for (Pixel[] row : currLayer.getLayerData()) {
          for (Pixel p : row) {
            output = output.concat(p.toString());

            if (i != ((this.model.getWidth() * this.model.getHeight()) - 1)) {
              output = output.concat("\n");
            }
            i++;
          }
        }
      }

      ImageProjectFileUtils.createFile(fileName + ".collage");
      ImageProjectFileUtils.writeToFile(fileName + ".collage", output);
    }

  }
}
