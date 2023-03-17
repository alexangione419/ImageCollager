package controller.commands;

import model.ImageProject;

public interface Command {

  void run(ImageProject p);
}
