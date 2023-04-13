package controller.commands;

import controller.ImageFileFormats;
import controller.ImageProjectController;
import controller.ImageProjectFileUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

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
        this.view.renderMessage("Image was saved as " + fileName + ".\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The name for the file is invalid. Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }

  /**
   * Saves the currently opened project as a .ppm image file.
   * @param name the name to give the file
   * @throws IOException if the method for some reason cannot create or write the .ppm file
   * @throws IllegalStateException if the {@code ImageProject} model doesn't have a loaded project
   */
  public void save(String name) throws IOException, IllegalStateException {
    if (!this.model.hasOpenProject()) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (name == null) {
      throw new IllegalArgumentException("File name cannot be null.");
    }

    if (!name.contains(".")) {
      throw new IllegalArgumentException("No file extension present."
          + " Please provide a supported file extension.");
    }

    String fileExtension = "";
    try {
      fileExtension = name.split("[.]")[1];
    }
    catch (IndexOutOfBoundsException e) {
      //ignore
    }

    if (ImageFileFormats.validFileFormat(fileExtension)
            && ImageProjectFileUtils.isFileNameValid(name.split("[.]")[0])) {
      if (fileExtension.equalsIgnoreCase("ppm")) {
        String output = "P3\n"
                + this.model.getWidth() + " " + this.model.getHeight() + "\n"
                + this.model.getMaxPixelValue() + "\n"
                + this.makeCanvasString() + "\n";

        ImageProjectFileUtils.createFile(name + ".");
        ImageProjectFileUtils.writeToFile(name + ".", output);
      } else {
        try {
          // retrieve image
          BufferedImage bi = this.model.getImageRepresentation();
          File outputfile = new File(name);
          ImageIO.write(bi, fileExtension, outputfile);
        } catch (IOException e) {
          throw new IllegalArgumentException("Invalid file format."
                  + " Please try a different file extension.");
        }
      }
    } else {
      throw new IllegalArgumentException("Invalid file format."
              + " Please try a different file extension.");
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
