package controller;

import controller.commands.AddImageToLayer;
import controller.commands.AddLayer;
import controller.commands.LoadProject;
import controller.commands.NewProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import controller.commands.SetFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A controller for manipulating a {@code PPMProject}.
 */
public class PPMProjectController implements ImageProjectController {
  boolean running;
  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;


  /**
   * Constructs a new {@code PPMProjectController}.
   *
   * @param model the model to control
   * @param view  the view of the model
   * @param input the Readable from which to read input from
   * @throws IllegalArgumentException if any of the given arguments are null
   */
  public PPMProjectController(ImageProject model, ImageProjectView view, Readable input)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("PPMProject for the PPMProjectController "
              + "cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException(
              "PPMProjectTextView for the PPMProjectController cannot be null.");
    }

    if (input == null) {
      throw new IllegalArgumentException("Readable for the PPMProjectController cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.sc = new Scanner(input);

  }


  /**
   * Adds in all the possible commands that the controller supports
   */
  private void initCommands() {

  }

  @Override
  public void start() throws IllegalStateException {
    try {
      this.view.renderMessage("Welcome to our Image Processor.\n");
    } catch (IOException io) {
      //pass
    }

    this.running = true;
    // begin command input loop
    while (running) {
      try {
        this.view.renderMessage("Awaiting command:\n");
      } catch (IOException io) {
        //pass
      }

      // Make sure there is input to read
      if (!sc.hasNext()) {
        throw new IllegalStateException("No input detected");
      }

      String currCommand = sc.next();
      switch (currCommand) {
        case "quit":
          try {
            this.view.renderMessage("WARNING: Quitting will delete any" +
                    " unsaved progress. Confirm? (y/n)\n");
          } catch (IOException io) {
            //pass
          }
          if (!sc.hasNext()) {
            throw new IllegalStateException("No input detected\n");
          }
          if (sc.next().equalsIgnoreCase("y")) {
            try {
              this.view.renderMessage("Bye Bye!\n");
            } catch (IOException io) {
              //pass
            }
            this.running = false;
          }
          break;
        case "new-project":
          if (!sc.hasNextInt()) {
            this.displayInvalidArgs();
            break;
          }
          int height = sc.nextInt();
          if (!sc.hasNextInt()) {
            this.displayInvalidArgs();
            break;
          }
          int width = sc.nextInt();
          new NewProject(height, width).run(this.model);
          break;
        case "load-project":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          new LoadProject(sc.next()).run(this.model);
          break;
        case "save-project":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          try {
            new SaveProject(sc.next()).run(this.model);
          } catch (IOException io) {
            throw new IllegalStateException(io);
          }

          break;
        case "add-layer":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          new AddLayer(sc.next()).run(this.model);
          break;
        case "add-image-to-layer":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          String layer = sc.next();
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          String image = sc.next();
          if (!sc.hasNextInt()) {
            this.displayInvalidArgs();
            break;
          }
          int x = sc.nextInt();
          if (!sc.hasNextInt()) {
            this.displayInvalidArgs();
            break;
          }
          new AddImageToLayer(layer, image, x, sc.nextInt()).run(this.model);
          break;
        case "set-filter":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          String layerName = sc.next();
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          new SetFilter(layerName, sc.next()).run(this.model);
          break;
        case "save-image":
          if (!sc.hasNext()) {
            this.displayInvalidArgs();
            break;
          }
          new SaveImage(sc.next()).run(this.model);
          break;
        default:
          try {
            this.view.renderMessage("Invalid Command\n");
          } catch (IOException io) {
            //pass
          }
      }
    }

  }

  private void displayInvalidArgs() {
    try {
      this.view.renderMessage("Invalid Arguments");
    } catch (IOException io) {
      //do nothing
    }
  }


}
