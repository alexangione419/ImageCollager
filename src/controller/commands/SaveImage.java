package controller.commands;

import controller.ImageProjectController;
import controller.ImageProjectFileUtils;
import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import model.pixels.Pixel;
import view.ImageProjectView;

/**
 * A command object that saves the image produced by the given {@code ImageProject}.
 */
public final class SaveImage extends ACommand {

  private Scanner sc;
  private ImageProjectController controller;

  /**
   * Constructs a new {@code SaveImage}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public SaveImage(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  /**
   * Constructs a new {@code SaveImage} with no view or scanner.
   * This constructor should be used to use the save method directly.
   * @param model the model to use
   */
  public SaveImage(ImageProject model) {
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
        this.view.renderMessage("Image was saved to as " + fileName + ".ppm.\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The name for the file is invalid. It must not contain any periods."
            + " Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }

  public void save(String name) throws IOException {
    if (!this.model.hasOpenProject()) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(name)) {

      String output = "P3\n"
          + this.model.getWidth() + " " + this.model.getHeight() + "\n"
          + this.model.getMaxPixelValue() + "\n"
          + this.makeCanvasString() + "\n";

      ImageProjectFileUtils.createFile(name + ".ppm");
      ImageProjectFileUtils.writeToFile(name + ".ppm", output);
    }
  }

  private String makeCanvasString() {
    StringBuilder result = new StringBuilder();
    Pixel[][] listToStringify = this.model.currentCanvas();

    for (int y = 0; y < listToStringify[0].length; y++) {
      for (Pixel[] pixels : listToStringify) {
        Pixel pix = pixels[y];
        result.append(pix.getRed()).append(" ")
                .append(pix.getGreen()).append(" ").append(pix.getBlue()).append(" ");
      }
      if (y != listToStringify[0].length - 1) {
        result.append("\n");
      }
    }

    return result.toString();
  }

}
