package newcontroller.commands;

import java.io.IOException;
import java.util.Scanner;
import model.ImageProject;
import view.ImageProjectView;

/**
 * A command object that handles the quitting functionality. Unfortunately, this object cannot be
 * used as the System.exit() method prevents the controller to from being tested.
 */
public final class Quit extends ACommand {

  Scanner sc;

  public Quit(ImageProject model, ImageProjectView view, Scanner sc) {
    super(model, view);
    this.sc = sc;
  }

  @Override
  public void run() {

    try {
      this.view.renderMessage("WARNING: Quitting will delete any unsaved progress. "
          + "Confirm? (y/n)\n");
    } catch (IOException e) {
      //ignore
    }

    String currCommand = this.sc.next();

    if (currCommand.equalsIgnoreCase("y")) {
      System.exit(0);
    } else if (currCommand.equalsIgnoreCase("n")) {

    } else {
      try {
        this.view.renderMessage("Invalid Command\n");
      } catch (IOException e) {
        //ignore
      }
      this.run();
    }
  }
}
