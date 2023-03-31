package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.ImageProject;
import controller.commands.ACommand;
import controller.commands.AddImageToLayer;
import controller.commands.AddLayer;
import controller.commands.FilterList;
import controller.commands.Help;
import controller.commands.LoadProject;
import controller.commands.NewProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import controller.commands.SetFilter;
import model.ImageProjectFileUtils;
import model.Layer;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;
import view.ImageProjectView;

/**
 * A implementation of {@code ImageProjectController} that allows users to manipulate an
 * {@code ImageProject}.
 */
public class ControllerImpl implements ImageProjectController {

  boolean running;
  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;
  private HashMap<String, ACommand> commands;


  /**
   * Constructs a new {@code ControllerImpl}.
   *
   * @param model the model to control
   * @param view  the view of the model
   * @param input the Readable from which to read input from
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public ControllerImpl(ImageProject model, ImageProjectView view, Readable input)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The Model for the Controller cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The View for the Controller cannot be null.");
    }

    if (input == null) {
      throw new IllegalArgumentException("The Readable for the Controller cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.sc = new Scanner(input);
    this.initCommands();

  }

  private void initCommands() {
    this.commands = new HashMap<String, ACommand>();

    this.commands.put("help", new Help(this.model, this.view));
    this.commands.put("?", new Help(this.model, this.view));
    this.commands.put("filter-list", new FilterList(this.model, this.view));
    this.commands.put("new-project", new NewProject(this.model, this.view, this.sc));
    this.commands.put("add-layer", new AddLayer(this.model, this.view, this.sc));
    this.commands.put("add-image-to-layer", new AddImageToLayer(this.model, this.view, this.sc));
    this.commands.put("set-filter", new SetFilter(this.model, this.view, this.sc));
    this.commands.put("load-project", new LoadProject(this.model, this.view, this, this.sc));
    this.commands.put("save-project", new SaveProject(this.model, this.view, this, this.sc));
    this.commands.put("save-image", new SaveImage(this.model, this.view, this, this.sc));

  }

  @Override
  public void start() throws IllegalStateException {

    this.running = true;
    boolean tryingToQuit = false;

    try {
      this.view.renderMessage("Welcome to our Image Processor.\n");
    } catch (IOException io) {
      //pass
    }

    // begin command input loop
    while (running) {

      if (!tryingToQuit) {
        try {
          this.view.renderMessage("Awaiting command:\n");
        } catch (IOException io) {
          //pass
        }
      }

      // Make sure there is input to read
      if (!sc.hasNext()) {
        throw new IllegalStateException("No input detected.");
      }

      String currCommand = sc.next();

      if (tryingToQuit) {
        if (currCommand.equalsIgnoreCase("y")) {
          this.running = false;
          try {
            this.view.renderMessage("Bye Bye!");
          } catch (IOException e) {
            //ignore
          }
          break;
        } else if (currCommand.equalsIgnoreCase("n")) {
          tryingToQuit = false;
          continue;
        }
      }

      //quitting
      if (currCommand.equalsIgnoreCase("quit")
          || (currCommand.equalsIgnoreCase("q"))) {

        try {
          this.view.renderMessage("WARNING: Quitting will delete any unsaved progress. "
              + "Confirm? (y/n)\n");
        } catch (IOException e) {
          //ignore
        }
        tryingToQuit = true;
        continue;
      }

      if (this.commands.containsKey(currCommand)) {
        this.commands.get(currCommand).run();
      } else {
        try {
          this.view.renderMessage("Invalid Command\n");
        } catch (IOException io) {
          //pass
        }
      }
    }
  }

  @Override
  public void saveImage(String name) throws IllegalStateException, IllegalArgumentException,
      IOException {
    if (!this.model.hasOpenProject()) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(name)) {

      String output = "P3\n"
          + this.model.getWidth() + " " + this.model.getHeight() + "\n"
          + this.model.getMaxPixelValue() + "\n"
          + this.model.currentCanvas() + "\n";

      ImageProjectFileUtils.createFile(name + ".ppm");
      ImageProjectFileUtils.writeToFile(name + ".ppm", output);
    }
  }

  @Override
  public void saveProject(String name) throws IllegalStateException, IOException {
    if (!this.model.hasOpenProject()) {
      throw new IllegalStateException("There's currently no open project.");
    }

    if (ImageProjectFileUtils.isFileNameValid(name)) {

      String output = name + "\n"
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

      ImageProjectFileUtils.createFile(name + ".collage");
      ImageProjectFileUtils.writeToFile(name + ".collage", output);
    }
  }

  @Override
  public void loadProject(String file) throws IllegalArgumentException {
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

            this.model.setActiveLayer(newLayer);
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
