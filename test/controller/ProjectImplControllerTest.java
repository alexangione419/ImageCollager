package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.StringReader;
import model.ImageProject;
import model.ProjectImpl;
import newcontroller.ControllerImpl;
import org.junit.After;
import org.junit.Test;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * A testing class for the {@code ControllerImpl}.
 */
public class ProjectImplControllerTest {

  protected ImageProjectController controller;
  protected ImageProjectView view;
  protected ImageProject g;

  @After
  public void cleanup() {
    File f = new File("P1.collage");
    f.delete();

    f = new File("P1.ppm");
    f.delete();

    f = new File("smol2.ppm");
    f.delete();

    f = new File("smol2.collage");
    f.delete();

    f = new File("smolLow2.ppm");
    f.delete();

    f = new File("smolLow2.collage");
    f.delete();
  }

  @Test
  public void invalidConstruction() {

    try {
      this.controller = new ControllerImpl(null, null, null);
      fail("Model is null");
    } catch (IllegalArgumentException e) {
      assertEquals("The Model for the Controller cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new ProjectImpl();
      this.controller = new ControllerImpl(this.g, null, null);
      fail("View is null");
    } catch (IllegalArgumentException e) {
      assertEquals("The View for the Controller cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new ProjectImpl();
      this.view = new PPMProjectTextView(this.g, System.out);
      this.controller = new ControllerImpl(this.g, this.view, null);
      fail("Output is null");
    } catch (IllegalArgumentException e) {
      assertEquals("The Readable for the Controller cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void welcomeMessage() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);
  }

  @Test
  public void quitMessageYes() {
    Readable input = new StringReader("new-project 2 2 quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals(
        "Welcome to our Image Processor.\nAwaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\nWARNING: Quitting will delete any"
            + " unsaved progress. Confirm? (y/n)\nBye Bye!",
        output.toString());
  }

  @Test
  public void quitMessageNo() {
    Readable input = new StringReader("new-project 2 2 quit n quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.\nAwaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\nWARNING: Quitting will delete any"
            + " unsaved progress. Confirm? (y/n)\nAwaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString());
  }

  @Test
  public void badInput() {
    Readable input = new StringReader("huh what quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badInput2() {
    Readable input = new StringReader("huh what a abb aff jjj quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void newProject() {
    Readable input = new StringReader("new-project 2 2 quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }

  @Test
  public void help() {
    Readable input = new StringReader("help quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nHere are all the available commands:\n"
            + "new-project: starts a new project.\n"
            + "save-project: save the current project.\n"
            + "load-project: loads a project found at the given file path.\n"
            + "add-layer: adds a new layer to the current project.\n"
            + "add-image-to-layer: adds an image at the given file path to the layer on "
            + "the current project that matches the same name.\n"
            + "set-filter: sets a filter for a specified layer.\n"
            + "save-image: saves the image present on the canvas.\n"
            + "filter-list: lists out all the supported filters.\n"
            + "help: lists out all the available commands.\n"
            + "?: lists out all the available commands.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void help2() {
    Readable input = new StringReader("? quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nHere are all the available commands:\n"
            + "new-project: starts a new project.\n"
            + "save-project: save the current project.\n"
            + "load-project: loads a project found at the given file path.\n"
            + "add-layer: adds a new layer to the current project.\n"
            + "add-image-to-layer: adds an image at the given file path to the layer on "
            + "the current project that matches the same name.\n"
            + "set-filter: sets a filter for a specified layer.\n"
            + "save-image: saves the image present on the canvas.\n"
            + "filter-list: lists out all the supported filters.\n"
            + "help: lists out all the available commands.\n"
            + "?: lists out all the available commands.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void filterListBeforeOpeningAProject() {
    Readable input = new StringReader("filter-list quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Please create or open a new project to use that command.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void filterListAfterOpeningAProject() {
    Readable input = new StringReader("new-project 2 2 filter-list quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    //the list of filters in the output
    String filters = output.toString()
        .split("Welcome to our Image Processor.\n")[1]
        .split("Here are all the available filters:\n")[1]
        .split("Awaiting command:\n")[0];

    //checks if each filter is in the model's hashmap
    for (String s : filters.split("\n")) {
      assertTrue(this.g.getFilters().containsKey(s));
    }
  }

  @Test
  public void badAddLayer() {
    Readable input = new StringReader("add-layer");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected", e.getMessage());
    }

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Please create or open a new project to use that command.\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badAddLayer2() {
    Readable input = new StringReader("new-project 2 2 add-layer");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected", e.getMessage());
    }

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void addLayer() {
    Readable input = new StringReader("new-project 2 2 add-layer quit y quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Layer quit was created.\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(2, this.g.getNumberOfLayers());
  }

  @Test
  public void addLayer2() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-layer Layer2 "
        + "add-layer Layer3 "
        + "add-layer Layer4 "
        + "add-layer Layer5 "
        + "quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Layer Layer2 was created.\n"
            + "Awaiting command:\n"
            + "Layer Layer3 was created.\n"
            + "Awaiting command:\n"
            + "Layer Layer4 was created.\n"
            + "Awaiting command:\n"
            + "Layer Layer5 was created.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(5, this.g.getNumberOfLayers());
  }

  @Test
  public void badAddImageLayer() {
    Readable input = new StringReader("add-image-to-layer");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Please create or open a new project to use that command.\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badAddImageLayer2() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer3() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer4() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer5() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer6() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0 0 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments. Try again:\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }

  //adding an image to a layer that doesn't exist
  @Test
  public void badAddImageLayer7() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer2 ./res/smo.ppm 0 0 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments. Try again:\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }

  @Test
  public void addImageLayer() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0 0 "
        + "add-image-to-layer Layer1 ./res/smol.ppm 0 0 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments. Try again:\n"
            + "Awaiting command:\n"
            + "Added ./res/smol.ppm to Layer Layer1 at (0, 0).\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }

  @Test
  public void badSetFilter() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

  }

  @Test
  public void badSetFilter2() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter normal ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

  }

  @Test
  public void badSetFilter3() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter normal Layer2");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments. Either filter or layer does not exist. "
            + "Try again:\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void setFilter() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter normal Layer1 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Set the normal filter to Layer Layer1.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badLoadProject() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badLoadProject2() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ./res/bad.collag");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "The file at ./res/bad.collag is not a valid project file. Try again\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);

  }

  @Test
  public void badLoadProject3() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ./res/bad.collage");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "The file at ./res/bad.collage is not a valid project file. Try again\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);

  }

  @Test
  public void loadProject() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ./res/good.collage "
        + "quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    this.controller.start();

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Loaded project ./res/good.collage.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);

    assertEquals("127 0 128 127 0 128 \n"
        + "0 0 128 127 127 255 ", this.g.currentCanvas());
  }

  @Test
  public void badSaveProject() {
    Readable input = new StringReader("save-project ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Please create or open a new project to use that command.\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badSaveProject2() {
    Readable input = new StringReader("new-project 2 2 save-project ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badSaveProject3() {
    Readable input = new StringReader("new-project 2 2 save-project something.txt");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "The name for the file is invalid. It must not contain any periods. "
            + "Try again\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void saveProject() {
    Readable input = new StringReader("new-project 2 2 save-project P1"
        + " quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    this.controller.start();

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Project was saved to as P1.collage.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badSaveImage() {
    Readable input = new StringReader("save-image ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Please create or open a new project to use that command.\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badSaveImage2() {
    Readable input = new StringReader("new-project 2 2 save-image ");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badSaveImage3() {
    Readable input = new StringReader("new-project 2 2 save-image something.png");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    try {
      this.controller.start();
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "The name for the file is invalid. It must not contain any periods. "
            + "Try again\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void saveImage() {
    Readable input = new StringReader("new-project 2 2 save-image P1"
        + " quit y");

    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.controller = new ControllerImpl(this.g, this.view, input);

    this.controller.start();

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Image was saved to as P1.ppm.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }
}