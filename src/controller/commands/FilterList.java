package controller.commands;

import java.io.IOException;
import java.util.Map.Entry;
import model.ImageProject;
import model.filters.Filter;
import view.ImageProjectView;

/**
 * A command object that renders a message containing all the available filters that the given
 * {@code ImageProject} contains.
 */
public final class FilterList extends ACommand {

  /**
   * Constructs a new {@code FilterList}.
   *
   * @param model the model to use
   * @param view  the view to use to render messages
   */
  public FilterList(ImageProject model, ImageProjectView view) {
    super(model, view);
  }

  @Override
  public void run() {
    if (!this.model.hasOpenProject()) {
      try {
        this.view.renderMessage("Please create or open a new project to use that command.\n");
      } catch (IOException e) {
        //ignore
      }
    } else {
      try {
        this.view.renderMessage("Here are all the available filters:\n");

        for (Entry<String, Filter> e : this.model.getFilters().entrySet()) {
          this.view.renderMessage(e.getKey() + "\n");
        }
      } catch (IOException e) {
        //ignore
      }
    }
  }
}
