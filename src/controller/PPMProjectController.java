package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.commands.Command;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A controller for manipulating a {@code PPMProject}.
 */
public class PPMProjectController implements ImageProjectController {

  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;
  private final Map<String, Command> commands;

  /**
   * Constructs a new {@code PPMProjectController}.
   *
   * @param model  the model to control
   * @param view   the view of the model
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
    this.commands = new HashMap<String, Command>();
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

    // begin command input loop
    boolean running = true;
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
      if (currCommand.equalsIgnoreCase("quit")) {
        try {
          this.view.renderMessage("WARNING: Quitting will delete any" +
                  " unsaved progress. Input y to confirm\n");
        } catch (IOException io){
          //pass
        }
        if (!sc.hasNext()) {
          throw new IllegalStateException("No input detected\n");
        }
        if (sc.next().equalsIgnoreCase("y")) {
          try {
            this.view.renderMessage("Bye Bye!\n");
          } catch (IOException io){
            //pass
          }
          break;
        }
      }
      if (this.commands.containsKey(currCommand)) {
        this.commands.get(currCommand).run(this.model);
      } else {
        try {
          this.view.renderMessage("Unsupported command\n");
        } catch (IOException io) {
          // pass
        }
      }








    }


  }
}
