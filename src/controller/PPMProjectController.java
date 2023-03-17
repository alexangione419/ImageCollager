package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.AddImageToLayer;
import controller.commands.AddLayer;
import controller.commands.Command;
import controller.commands.LoadProject;
import controller.commands.NewProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import controller.commands.SetFilter;
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
  //private final Map<String, Command> commands;
  private final ArrayList<String> commands;

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
    //this.commands = new HashMap<String, Command>();
    this.commands = new ArrayList<>();
    this.commands.add("new-project");
    this.commands.add("load-project");
    this.commands.add("save-project");
    this.commands.add("add-layer");
    this.commands.add("add-image-to-layer");
    this.commands.add("set-filter");
    this.commands.add("save-image");
    this.commands.add("quit");
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
          new NewProject(sc.nextInt(), sc.nextInt()).run(this.model);
          break;
        case "load-project":
          new LoadProject(sc.next()).run(this.model);
          break;
        case "save-project":
          new SaveProject(sc.next()).run(this.model);
          break;
        case "add-layer":
          new AddLayer(sc.next()).run(this.model);
          break;
        case "add-image-to-layer":
          new AddImageToLayer(sc.next(), sc.next(), sc.nextInt(), sc.nextInt()).run(this.model);
          break;
        case "set-filter":
          new SetFilter(sc.next(), sc.next()).run(this.model);
          break;
        case "save-image":
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


}
