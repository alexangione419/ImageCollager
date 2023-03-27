package newcontroller;

import controller.ImageProjectController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import model.ImageProject;
import newcontroller.commands.ACommand;
import newcontroller.commands.AddImageToLayer;
import newcontroller.commands.AddLayer;
import newcontroller.commands.FilterList;
import newcontroller.commands.Help;
import newcontroller.commands.LoadProject;
import newcontroller.commands.NewProject;
import newcontroller.commands.SaveImage;
import newcontroller.commands.SaveProject;
import newcontroller.commands.SetFilter;
import view.ImageProjectView;

public class ControllerImpl implements ImageProjectController {

  boolean running;
  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;
  private HashMap<String, ACommand> commands;


  /**
   * Constructs a new {@code PPMProjectController}.
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

    this.commands.put("load-project", new LoadProject(this.model, this.view, this.sc));
    this.commands.put("save-project", new SaveProject(this.model, this.view, this.sc));
    this.commands.put("save-image", new SaveImage(this.model, this.view, this.sc));

//    this.commands.put("quit", new Quit(this.model, this.view, this.sc));
//    this.commands.put("q", new Quit(this.model, this.view, this.sc));

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
}
