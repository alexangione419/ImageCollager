package controller;

import controller.commands.AddImageToLayer;
import controller.commands.AddLayer;
import controller.commands.Command;
import controller.commands.LoadProject;
import controller.commands.NewProject;
import controller.commands.SaveImage;
import controller.commands.SaveProject;
import controller.commands.SetFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import model.ImageProject;
import model.filters.Filter;
import view.ImageProjectView;

/**
 * A controller for manipulating a {@code PPMProject}.
 */
public class PPMProjectController implements ImageProjectController {

  boolean running;
  private final ImageProject model;
  private final ImageProjectView view;
  private final Scanner sc;
  private HashMap<String, Command> commands;


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
    this.initCommands();

  }

  private void initCommands() {
    this.commands = new HashMap<String, Command>();


  }


  @Override
  public void start() throws IllegalStateException {
//    ImageProcessorGUIView.setDefaultLookAndFeelDecorated(false);
//    ImageProcessorGUIView frame = new ImageProcessorGUIView(new ProjectImpl());
//
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setVisible(true);
//
//
//    try {
//      // Set cross-platform Java L&F (also called "Metal")
//      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//
//    } catch (UnsupportedLookAndFeelException e) {
//      // handle exception
//    } catch (ClassNotFoundException e) {
//      // handle exception
//    } catch (InstantiationException e) {
//      // handle exception
//    } catch (IllegalAccessException e) {
//      // handle exception
//    } catch (Exception e) {
//    }

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
        case "help":
        case "?":
          try {
            this.view.renderMessage("Here are all the available commands:\n"
                + "new-project: starts a new project.\n"
                + "save-project: save the current project.\n"
                + "load-project: loads a project found at the given file path.\n"
                + "add-layer: adds a new layer to the current project.\n"
                + "add-image-to-layer: adds an image at the given file path to the layer on the "
                + "current project that matches the same name."
                + "set-filter: sets a filter for a specified layer.\n"
                + "save-image: saves the image present on the canvas.\n"
                + "filter-list: lists out all the supported filters.\n"
                + "help: lists out all the available commands.\n"
                + "?: lists out all the available commands.\n"
            );
          } catch (IOException e) {
            //pass
          }
          break;
        case "filter-list":
          try {
            if (!this.model.hasOpenProject()) {
              this.view.renderMessage("Please create or open a new project to use that command.\n");
            } else {
              this.view.renderMessage("Here are all the available filters:\n");

              for (Entry<String, Filter> s : this.model.getFilters().entrySet()) {
                this.view.renderMessage(s.getKey() + "\n");
              }
            }
          } catch (IOException e) {
            //pass
          }
          break;
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
          int width = sc.nextInt();
          if (!sc.hasNextInt()) {
            this.displayInvalidArgs();
            break;
          }
          int height = sc.nextInt();
          new NewProject(width, height).run(this.model);
          try {
            this.view.renderMessage("Created new project with canvas size of "
                + width + "x" + height + "."
            );
          } catch (IOException e) {
            //ignore
          }
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
