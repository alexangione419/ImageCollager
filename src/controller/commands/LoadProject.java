package controller.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.ImageProject;
import controller.ImageProjectFileUtils;
import model.pixels.RGBAPixel;
import view.ImageProjectView;

/**
 * A command object that saves the given {@code ImageProject} into a .collage project file.
 */
public final class LoadProject extends ACommand {

  private Scanner sc;

  /**
   * Constructs a new {@code LoadProject}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   * @param sc    the Scanner with the current user input
   */
  public LoadProject(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  /**
   * Constructs a new {@code LoadProject} with no view or scanner.
   * This constructor should be used to use the load method directly.
   * @param model the model to use
   */
  public LoadProject(ImageProject model) {
    super(model, null);
  }


  @Override
  public void run() {
    // Make sure there is input to read
    if (!this.sc.hasNext()) {
      throw new IllegalStateException("No input detected.");
    }

    String filePath = this.sc.next();

    try {
      try {
        this.load(filePath);
        this.view.renderMessage("Loaded project " + filePath + ".\n");
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("The file at " + filePath + " is not a valid project file. "
            + "Try again\n");
      }
    } catch (IOException e) {
      //ignore
    }
  }

  public void load(String file) {
    if (file == null) {
      throw new IllegalArgumentException("Filepath cannot be null.");
    }

    if (!ImageProjectFileUtils.isProjectFile(file)) {
      throw new IllegalArgumentException("File at filepath is not a .collage file.");
    } else {
      Scanner sc;
      try {
        sc = new Scanner(new FileInputStream(file));
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Project file not found at given filepath.");
      }

      //discards name of project
      checkNext(sc);
      sc.next();
      checkNext(sc);

      int width;
      int height;

      try {
        width = sc.nextInt();
      } catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }
      checkNext(sc);

      try {
        height = sc.nextInt();
      } catch (InputMismatchException e) {
        throw new IllegalArgumentException("Invalid project file at given filepath.");
      }

      checkNext(sc);

      this.model.createNewProject(width, height);

      while (sc.hasNext()) {
        String newLayer = sc.next();
        checkNext(sc);
        String filter = sc.next();

        if (this.model.doesLayerExist(newLayer)) {
          this.model.setFilter(filter, newLayer);
        } else {
          this.model.addLayer(newLayer);
          this.model.setFilter(filter, newLayer);
        }

        this.model.setActiveLayer(newLayer);
        for (int y = 0; y < height; y++) {
          for (int x = 0; x < width; x++) {
            checkNext(sc);

            int r = sc.nextInt();
            checkNext(sc);

            int g = sc.nextInt();
            checkNext(sc);

            int b = sc.nextInt();
            checkNext(sc);

            int a = sc.nextInt();

            this.model.getActiveLayer().setPixelColor(x, y,
                new RGBAPixel(this.model.getMaxPixelValue(), r, g, b, a));
          }
        }
      }
    }
  }

  /**
   * Ensures that there is more input in file contents within the given scanner.
   *
   * @param scanner the scanner containing the file contents
   */
  private void checkNext(Scanner scanner) {
    if (!scanner.hasNext()) {
      throw new IllegalArgumentException("Invalid project file format");
    }
  }

}
