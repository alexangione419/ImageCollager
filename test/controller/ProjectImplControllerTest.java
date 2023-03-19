package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import model.ImageProject;
import model.ProjectImpl;
import org.junit.Test;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * A testing class for the {@code PPMProjectController}.
 */
public class ProjectImplControllerTest {

  protected ImageProjectController controller;
  protected ImageProjectView view;
  protected ImageProject g;

  @Test
  public void invalidConstruction() {

    try {
      this.controller = new PPMProjectController(null, null, null);
      fail("Model is null");
    } catch (IllegalArgumentException e) {
      assertEquals("PPMProject for the PPMProjectController cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new ProjectImpl();
      this.controller = new PPMProjectController(this.g, null, null);
      fail("View is null");
    } catch (IllegalArgumentException e) {
      assertEquals("PPMProjectTextView for the PPMProjectController cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new ProjectImpl();
      this.view = new PPMProjectTextView(this.g, System.out);
      this.controller = new PPMProjectController(this.g, this.view, null);
      fail("Output is null");
    } catch (IllegalArgumentException e) {
      assertEquals("Readable for the PPMProjectController cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void welcomeMessage() {
    Readable input = new StringReader("new-project quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
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
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals(
        "Welcome to our Image Processor.\nAwaiting command:\nAwaiting command:\nWARNING: Quitting will delete any"
            +
            " unsaved progress. Confirm? (y/n)\nBye Bye!\n",
        output.toString());
  }

  @Test
  public void quitMessageNo() {
    Readable input = new StringReader("new-project 2 2 quit n quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.\nAwaiting command:"
            + "\nAwaiting command:\nWARNING: Quitting will delete any"
            + " unsaved progress. Confirm? (y/n)\nAwaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!\n",
        output.toString());
  }

  @Test
  public void badInput() {
    Readable input = new StringReader("huh what quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\nInvalid Command\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badInput2() {
    Readable input = new StringReader("huh what a abb aff jjj quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
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
            + "Bye Bye!\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void newProject() {
    Readable input = new StringReader("new-project 2 2 quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nAwaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }


  @Test
  public void addLayer() {
    Readable input = new StringReader("new-project 2 2 add-layer quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nAwaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }

  @Test
  public void addImageLayer() {
    Readable input = new StringReader("new-project 2 2 add-image-to-layer Layer1 smol.ppm 0 0 "
        + "quit y");
    Appendable output = new StringBuilder();

    this.g = new ProjectImpl();
    this.view = new PPMProjectTextView(this.g, output);
    this.g.createNewProject(32, 32);
    this.controller = new PPMProjectController(this.g, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nAwaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.g.getWidth());
    assertEquals(2, this.g.getHeight());
    assertEquals(1, this.g.getNumberOfLayers());
  }
}