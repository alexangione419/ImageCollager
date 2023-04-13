package controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import model.ImageProject;
import model.ProjectImpl;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;
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
  protected ImageProject model;

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

    f = new File("something.png");
    f.delete();
  }

  @Test
  public void validSaveImage() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(2, 2);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);

    try {
      this.controller.saveImage("P1.ppm");
      this.controller.saveProject("P1");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("P1.collage"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }
    //checks that after saving the image, the loaded project is as expected
    Pixel[][] curCanvas = this.model.currentCanvas();

    this.controller.loadProject("P1.collage");

    assertArrayEquals(this.model.currentCanvas(), curCanvas);

    assertArrayEquals(new RGBAPixel[][]{{new RGBAPixel(255, 255, 0, 0),
        new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}}, curCanvas);
    assertArrayEquals(new RGBAPixel[][]{{new RGBAPixel(255, 255, 0, 0),
        new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}}, this.model.currentCanvas());
  }

  @Test
  public void validSaveImagePNG() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(2, 2);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);

    try {
      this.controller.saveImage("P1.ppm");
      this.controller.saveProject("P1");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("P1.collage"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }
    //checks that after saving the image, the loaded project is as expected
    Pixel[][] curCanvas = this.model.currentCanvas();

    this.controller.loadProject("P1.collage");

    assertArrayEquals(this.model.currentCanvas(), curCanvas);

    assertArrayEquals(new RGBAPixel[][]{{new RGBAPixel(255, 255, 0, 0),
        new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}}, curCanvas);
    assertArrayEquals(new RGBAPixel[][]{{new RGBAPixel(255, 255, 0, 0),
        new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}}, this.model.currentCanvas());
  }

  @Test
  public void invalidSaveImageUnsupportedFormat() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(2, 2);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);

    try {
      this.controller.saveImage("P1.gif");
      this.controller.saveProject("P1");
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Unsupported file format.", e.getMessage());
    }
  }

  @Test
  public void invalidSaveImage() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(2, 2);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);

    try {
      this.controller.saveImage("P1");
      this.controller.saveProject("P1");
      fail();
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("No file extension present. Please provide a supported file extension.",
          e.getMessage());
    }

    try {
      this.controller.saveImage("P1.");
      this.controller.saveProject("P1");
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Invalid file format. Please try a different file extension.",
          e.getMessage());
    }

    try {
      this.controller.saveImage("P1.gif");
      this.controller.saveProject("P1");
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Invalid file format. Please try a different file extension.",
          e.getMessage());
    }

    try {
      this.controller.saveImage("P1.ase");
      this.controller.saveProject("P1");
    } catch (IOException | IllegalArgumentException e) {
      assertEquals("Invalid file format. Please try a different file extension.",
          e.getMessage());
    }
  }

  @Test
  public void validSaveImageOneLayer() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    Pixel[][] curCanvas = this.model.currentCanvas();

    try {
      this.controller.saveImage("smol2.ppm");
      this.controller.saveProject("smol2");
    } catch (IOException io) {
      // ignore
    }

    Scanner sc = null;

    try {
      sc = new Scanner(new FileInputStream("smol2.ppm"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }

    assertNotNull(sc);
    assertEquals("P3", sc.next());
    assertEquals("3", sc.next());
    assertEquals("4", sc.next());
    assertEquals("255", sc.next());
    for (int i = 0; i < 12; i++) {
      assertEquals(sc.nextInt(), 225);
    }

    this.controller.loadProject("smol2.collage");
    Pixel[][] full = new RGBAPixel[][]{
        {new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}};

    assertArrayEquals(full, this.model.currentCanvas());

    assertArrayEquals(full, curCanvas);

    assertArrayEquals(curCanvas, this.model.currentCanvas());
  }

  @Test
  public void validSaveImageOneLayer2() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    Pixel[][] curCanvas = this.model.currentCanvas();

    assertEquals(3, this.model.getWidth());
    assertEquals(4, this.model.getHeight());

    try {
      this.controller.saveImage("smol2.ppm");
      this.controller.saveProject("smol2");
      this.controller.loadProject("./res/good.collage");
    } catch (IOException io) {
      // ignore
    }

    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    Pixel[][] arr = new RGBAPixel[][]{
        {new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 0),
            new RGBAPixel(255, 0, 0, 0)},
        {new RGBAPixel(255, 0, 0, 0),
            new RGBAPixel(255, 0, 0, 0),
            new RGBAPixel(255, 0, 0, 0)},
        {new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 0),
            new RGBAPixel(255, 0, 0, 0)},
        {new RGBAPixel(255, 0, 0, 0),
            new RGBAPixel(255, 0, 0, 0),
            new RGBAPixel(255, 0, 0, 0)}};
    assertNotEquals(arr, curCanvas);

    Pixel[][] arr2 = new RGBAPixel[][]{
        {new RGBAPixel(255, 127, 0, 128),
            new RGBAPixel(255, 0, 0, 128)},
        {new RGBAPixel(255, 127, 0, 128),
            new RGBAPixel(255, 127, 127, 255)}};
    assertArrayEquals(arr2, this.model.currentCanvas());

  }

  @Test
  public void validSaveImageMultipleLayers() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    this.model.addLayer("Layer2");
    this.model.addImageToLayer("Layer2", "./res/smolLow.ppm", 0, 0);

    try {
      this.controller.saveImage("smolLow2.ppm");
    } catch (IOException io) {
      // welp
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("smolLow2.ppm"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }

    assertNotNull(sc);
    assertEquals("P3", sc.next());
    assertEquals("3", sc.next());
    assertEquals("4", sc.next());
    assertEquals("255", sc.next());
    for (int i = 0; i < 12; i++) {
      assertEquals(sc.nextInt(), 119);
    }
  }

  @Test
  public void badSaveImage() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      try {
        this.controller.saveImage(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }

    this.model.createNewProject(3, 3);

    try {
      try {
        this.controller.saveImage(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null.", e.getMessage());
    }

    try {
      try {
        this.controller.saveImage("");
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("No file extension present. Please provide a supported file extension.", e.getMessage());
    }

    try {
      try {
        this.controller.saveImage("\n");
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("No file extension present. Please provide a supported file extension.", e.getMessage());
    }

    try {
      try {
        this.controller.saveImage(System.lineSeparator());
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("No file extension present. Please provide a supported file extension.", e.getMessage());
    }
  }

  @Test
  public void badLoadProject() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);

    try {
      this.controller.loadProject(null);
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    }

    try {
      this.controller.loadProject("tako.ppm");
      fail("Invalid filepath");
    } catch (IllegalArgumentException e) {
      assertEquals("File at filepath is not a .collage file.", e.getMessage());
    }

    try {
      this.controller.loadProject("tako.collage");
      fail("Invalid filepath");
    } catch (IllegalArgumentException e) {
      assertEquals("Project file not found at given filepath.", e.getMessage());
    }

    try {
      this.controller.loadProject("./res/bad.collage");
      fail("bad project file");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid project file at given filepath.", e.getMessage());
    }
  }

  @Test
  public void loadProject() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel[][] curCanvas = this.model.currentCanvas();

    assertEquals(3, this.model.getWidth());
    assertEquals(4, this.model.getHeight());

    this.controller.loadProject("./res/good.collage");

    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(3, this.model.getNumberOfLayers());

    this.model.setActiveLayer(0);
    assertEquals("normal", this.model.getActiveLayer().getFilter());

    this.model.setActiveLayer(1);
    assertEquals("red-component", this.model.getActiveLayer().getFilter());

    this.model.setActiveLayer(2);
    assertEquals("normal", this.model.getActiveLayer().getFilter());

    assertNotEquals(curCanvas, this.model.currentCanvas());

  }

  @Test
  public void validSaveProject() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    try {
      this.controller.saveProject("P1");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("P1.collage"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }
    assertNotNull(sc);

    assertEquals("P1", sc.next());
    assertEquals("3", sc.next());
    assertEquals("4", sc.next());
    assertEquals("Layer1", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 12; i++) {
      assertEquals("225 225 225 255",
          sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    }
  }

  @Test
  public void badSaveProject() {
    Readable input = new StringReader("quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      try {
        this.controller.saveProject(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Tried to save project with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }

    this.model.createNewProject(4, 4);

    try {
      try {
        this.controller.saveProject(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null.", e.getMessage());
    }

    try {
      try {
        this.controller.saveProject("");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.controller.saveProject("\n");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.controller.saveProject(" ");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.controller.saveProject(System.lineSeparator());
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.controller.saveProject("P1.txt");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("Name must be valid filename with no file extension.",
          e.getMessage());
    }
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
      this.model = new ProjectImpl();
      this.controller = new ControllerImpl(this.model, null, null);
      fail("View is null");
    } catch (IllegalArgumentException e) {
      assertEquals("The View for the Controller cannot be null.",
          e.getMessage());
    }

    try {
      this.model = new ProjectImpl();
      this.view = new PPMProjectTextView(this.model, System.out);
      this.controller = new ControllerImpl(this.model, this.view, null);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);
  }

  @Test
  public void quitMessageYes() {
    Readable input = new StringReader("new-project 2 2 quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badInput2() {
    Readable input = new StringReader("huh what a abb aff jjj quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void badNewProject() {
    Readable input = new StringReader("new-project 2.5 2.5 quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(2, 2);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Invalid argument, try again.\n"
            + "Awaiting command:\n"
            + "Invalid Command, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void newProject() {
    Readable input = new StringReader("new-project 2 2 quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void help() {
    Readable input = new StringReader("help quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.model.createNewProject(32, 32);
    this.controller = new ControllerImpl(this.model, this.view, input);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
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
      assertTrue(this.model.getFilters().containsKey(s));
    }
  }

  @Test
  public void badAddLayer() {
    Readable input = new StringReader("add-layer");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Layer quit was created.\n"
            + "Awaiting command:\nInvalid Command, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(2, this.model.getNumberOfLayers());
  }

  @Test
  public void addLayer2() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-layer Layer1 "
        + "quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid layer name. Layer name cannot be whitespace and cannot"
            + "share the same name as another layer. Try again:\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void addLayer3() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-layer Layer2 "
        + "add-layer Layer3 "
        + "add-layer Layer4 "
        + "add-layer Layer5 "
        + "quit y");
    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
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
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(5, this.model.getNumberOfLayers());
  }

  @Test
  public void badAddImageLayer() {
    Readable input = new StringReader("add-image-to-layer");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer3() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer4() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badAddImageLayer5() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  //adding an image to a layer that doesn't exist
  @Test
  public void badAddImageLayer7() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer2 ./res/smo.ppm 0 0 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  //giving a doubles as coordinates
  @Test
  public void badAddImageLayer8() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0.5 0.5 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid argument, try again.\n"
            + "Awaiting command:\n"
            + "Invalid Command, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void badAddImageLayer9() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 3 3 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments, try again.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void addImageLayer() {
    Readable input = new StringReader("new-project 2 2 "
        + "add-image-to-layer Layer1 ./res/smo.ppm 0 0 "
        + "add-image-to-layer Layer1 ./res/smol.ppm 0 0 "
        + "quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

    assertEquals("Welcome to our Image Processor.",
        output.toString().split("\n")[0]);

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Invalid arguments, try again.\n"
            + "Awaiting command:\n"
            + "Added ./res/smol.ppm to Layer Layer1 at (0, 0).\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertEquals(1, this.model.getNumberOfLayers());
  }

  @Test
  public void badSetFilter() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter ");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

  }

  @Test
  public void badSetFilter2() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter normal ");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

  }

  @Test
  public void badSetFilter3() {
    Readable input = new StringReader("new-project 2 2 "
        + "set-filter normal Layer2");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);
    this.controller.start();

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
  public void badLoadProjectInput() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badLoadProject2() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ./res/bad.collag");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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
  public void loadProjectInput() {
    Readable input = new StringReader("new-project 2 2 "
        + "load-project ./res/good.collage "
        + "quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    this.controller.start();

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Loaded project ./res/good.collage.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 127, 0, 128),
                new RGBAPixel(255, 0, 0, 128)},
            {new RGBAPixel(255, 127, 0, 128),
                new RGBAPixel(255, 127, 127, 255)}},
        this.model.currentCanvas());
  }

  @Test
  public void badSaveProjectInput() {
    Readable input = new StringReader("save-project ");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badSaveProject3() {
    Readable input = new StringReader("new-project 2 2 save-project something.txt");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

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
  public void badSaveImageInput() {
    Readable input = new StringReader("save-image ");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
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

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }
  }

  @Test
  public void badSaveImage3() {
    Readable input = new StringReader("new-project 2 2 save-image something.pn");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    try {
      this.controller.start();
      fail("no quit");
    } catch (IllegalStateException e) {
      assertEquals("No input detected.", e.getMessage());
    }

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "The name for the file is invalid. Try again\n"
            + "Awaiting command:\n",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }

  @Test
  public void saveImage() {
    Readable input = new StringReader("new-project 2 2 save-image P1.ppm"
        + " quit y");

    Appendable output = new StringBuilder();

    this.model = new ProjectImpl();
    this.view = new PPMProjectTextView(this.model, output);
    this.controller = new ControllerImpl(this.model, this.view, input);

    this.controller.start();

    assertEquals("Awaiting command:\n"
            + "Created new project with canvas size of 2x2.\n"
            + "Awaiting command:\n"
            + "Image was saved as P1.ppm.\n"
            + "Awaiting command:\n"
            + "WARNING: Quitting will delete any unsaved progress. Confirm? (y/n)\n"
            + "Bye Bye!",
        output.toString().split("Welcome to our Image Processor.\n")[1]);
  }
}