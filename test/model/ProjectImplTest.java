package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import model.filters.Filter;
import model.filters.Normal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for {@code ProjectImpl}.
 */
public class ProjectImplTest {

  ImageProject model;

  @Before
  public void init() {
    this.model = new ProjectImpl();
  }

  @After
  public void removeSaveProjectTestFiles() {
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
  public void badCreateNewProject() {
    try {
      this.model.createNewProject(0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + " to zero.", e.getMessage());
    }

    try {
      this.model.createNewProject(-3, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + " to zero.", e.getMessage());
    }

    try {
      this.model.createNewProject(-3, -2);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + " to zero.", e.getMessage());
    }

    try {
      this.model.createNewProject(30, -20);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + " to zero.", e.getMessage());
    }
  }

  @Test
  public void validSaveImage() {
    this.model.createNewProject(2, 2);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);

    try {
      this.model.saveImage("P1");
      this.model.saveProject("P1");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("P1.collage"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }

    String curCanvas = this.model.currentCanvas();

    this.model.loadProject("P1.collage");

    assertEquals(this.model.currentCanvas(), curCanvas);
    assertEquals("255 0 0 0 0 0 \n0 0 0 0 0 0 ", curCanvas);
    assertEquals("255 0 0 0 0 0 \n0 0 0 0 0 0 ", this.model.currentCanvas());
  }

  @Test
  public void validSaveImageOneLayer() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    String curCanvas = this.model.currentCanvas();

    try {
      this.model.saveImage("smol2");
      this.model.saveProject("smol2");
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

    this.model.loadProject("smol2.collage");

    assertEquals("225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 ", this.model.currentCanvas());

    assertEquals("225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 ", curCanvas);

    assertEquals(curCanvas, this.model.currentCanvas());
  }

  @Test
  public void validSaveImageOneLayer2() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    String curCanvas = this.model.currentCanvas();

    assertEquals(3, this.model.getWidth());
    assertEquals(4, this.model.getHeight());

    try {
      this.model.saveImage("smol2");
      this.model.saveProject("smol2");
      this.model.loadProject("./res/good.collage");
    } catch (IOException io) {
      // ignore
    }

    assertEquals(2, this.model.getWidth());
    assertEquals(2, this.model.getHeight());
    assertNotEquals("255 255 255 255 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 \n"
        + "255 255 255 255 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 ", curCanvas);

    assertEquals("127 0 128 127 0 128 \n"
        + "0 0 128 127 127 255 ", this.model.currentCanvas());

  }

  @Test
  public void validSaveImageMultipleLayers() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);
    this.model.addLayer("Layer2");
    this.model.addImageToLayer("Layer2", "./res/smolLow.ppm", 0, 0);

    try {
      this.model.saveImage("smolLow2");
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
    try {
      try {
        this.model.saveImage(null);
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
        this.model.saveImage(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null.", e.getMessage());
    }

    try {
      try {
        this.model.saveImage("");
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveImage("\n");
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveImage(System.lineSeparator());
      } catch (IOException e) {
        //ignore
      }
      fail("Whitespace passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }
  }

  @Test
  public void badLoadProject() {
    try {
      this.model.loadProject(null);
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    }

    try {
      this.model.loadProject("tako.ppm");
      fail("Invalid filepath");
    } catch (IllegalArgumentException e) {
      assertEquals("File at filepath is not a .collage file.", e.getMessage());
    }

    try {
      this.model.loadProject("tako.collage");
      fail("Invalid filepath");
    } catch (IllegalArgumentException e) {
      assertEquals("Project file not found at given filepath.", e.getMessage());
    }

    try {
      this.model.loadProject("./res/bad.collage");
      fail("bad project file");
    } catch (IllegalArgumentException e) {
      assertEquals("Invalid project file at given filepath.", e.getMessage());
    }
  }

  @Test
  public void loadProject() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    String curCanvas = this.model.currentCanvas();

    assertEquals(3, this.model.getWidth());
    assertEquals(4, this.model.getHeight());

    this.model.loadProject("./res/good.collage");

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
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    try {
      this.model.saveProject("P1");
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
    try {
      try {
        this.model.saveProject(null);
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
        this.model.saveProject(null);
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be null.", e.getMessage());
    }

    try {
      try {
        this.model.saveProject("");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveProject("\n");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveProject(" ");
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveProject(System.lineSeparator());
      } catch (IOException e) {
        //ignore
      }
      fail("Invalid file name");
    } catch (IllegalArgumentException e) {
      assertEquals("File name cannot be whitespace.", e.getMessage());
    }

    try {
      try {
        this.model.saveProject("P1.txt");
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
  public void validGetMaxPixelValue() {
    this.model.createNewProject(32, 32);
    assertEquals(255, this.model.getMaxPixelValue());

    this.model.createNewProject(302, 2);
    assertEquals(255, this.model.getMaxPixelValue());
  }

  @Test
  public void badGetMaxPixelValue() {
    try {
      this.model.getMaxPixelValue();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validGetWidth() {
    this.model.createNewProject(32, 32);
    assertEquals(32, this.model.getWidth());

    this.model.createNewProject(320, 32);
    assertEquals(320, this.model.getWidth());
  }

  @Test
  public void badGetWidth() {
    try {
      this.model.getWidth();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validGetHeight() {
    this.model.createNewProject(32, 64);
    assertEquals(64, this.model.getHeight());

    this.model.createNewProject(100, 100);
    assertEquals(100, this.model.getHeight());
  }

  @Test
  public void badGetHeight() {
    try {
      this.model.getHeight();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validGetLayerNumber() {
    this.model.createNewProject(32, 32);
    for (int i = 1; i <= 100; i++) {
      this.model.addLayer("TestLayer" + i);
    }
    assertEquals(101, this.model.getNumberOfLayers());
  }

  @Test
  public void badLayerNumber() {
    try {
      this.model.getNumberOfLayers();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validAddLayer() {
    this.model.createNewProject(32, 32);
    this.model.addLayer("TestLayer");
    assertEquals(2, this.model.getNumberOfLayers());

    for (int i = 0; i < 32; i++) {
      this.model.addLayer("TestLayer" + i);
      assertEquals(3 + i, this.model.getNumberOfLayers());
    }

    assertEquals(34, this.model.getNumberOfLayers());
  }

  @Test
  public void badAddLayer() {
    try {
      this.model.addLayer("Layer 2");
      fail("Tried to add a layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("");
      fail("Tried to create a Layer with an empty string as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("     ");
      fail("Tried to create a Layer with just whitespace as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("\n");
      fail("Tried to create a Layer with just a newline as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer(System.lineSeparator());
      fail("Tried to create a Layer with a lineSeparator as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("Layer 1");
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot contain a space or a linebreak.",
          e.getMessage());
    }
  }

  @Test
  public void validRemoveLayer() {
    this.model.createNewProject(32, 32);

    for (int i = 0; i < 32; i++) {
      this.model.addLayer("TestLayer" + i);
      assertEquals(2 + i, this.model.getNumberOfLayers());
      assertEquals("TestLayer" + i, this.model.getActiveLayer().getName());
    }

    assertEquals(33, this.model.getNumberOfLayers());
    assertEquals("TestLayer31", this.model.getActiveLayer().getName());
    this.model.setActiveLayer("TestLayer3");

    this.model.removeLayer("TestLayer3");
    this.model.removeLayer("TestLayer7");
    this.model.removeLayer("TestLayer17");

    assertEquals(30, this.model.getNumberOfLayers());

    assertEquals("TestLayer2", this.model.getActiveLayer().getName());
    this.model.removeLayer("TestLayer2");
    assertEquals("TestLayer1", this.model.getActiveLayer().getName());
    this.model.removeLayer("TestLayer1");
    this.model.removeLayer("Layer1");
    assertEquals("TestLayer0", this.model.getActiveLayer().getName());
    this.model.removeLayer("TestLayer0");
    assertEquals("TestLayer4", this.model.getActiveLayer().getName());

  }

  @Test
  public void badRemoveLayer() {
    try {
      this.model.removeLayer("Layer 1");
      fail("Tried to remove a layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.removeLayer("Layer 1");
      fail("Tried to remove the only layer off this model");
    } catch (IllegalStateException e) {
      assertEquals("There is only 1 layer. Operation cannot be done.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("Layer2");
      this.model.removeLayer("Layer");
      fail("Tried to remove a layer that doesn't exist");
    } catch (IllegalArgumentException e) {
      assertEquals("Tried to remove layer \"Layer\" but that layer doesn't exist "
              + "in this project.",
          e.getMessage());
    }

    try {
      this.model.createNewProject(32, 32);
      this.model.removeLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void validGetActiveLayer() {
    this.model.createNewProject(32, 32);
    assertEquals("Layer1", this.model.getActiveLayer().getName());
    this.model.addLayer("Layer2");
    assertEquals("Layer2", this.model.getActiveLayer().getName());
  }

  @Test
  public void badGetActiveLayer() {
    try {
      this.model.getActiveLayer();
      fail("Tried to get the active layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }
  }

  @Test
  public void validSetActiveLayer() {
    this.model.createNewProject(32, 32);
    assertEquals("Layer1", this.model.getActiveLayer().getName());
    this.model.addLayer("Layer2");
    this.model.addLayer("Layer3");
    this.model.addLayer("Layer4");
    assertEquals("Layer4", this.model.getActiveLayer().getName());

    this.model.setActiveLayer("Layer3");
    assertEquals("Layer3", this.model.getActiveLayer().getName());
    this.model.setActiveLayer("Layer2");
    assertEquals("Layer2", this.model.getActiveLayer().getName());

    this.model.setActiveLayer(3);
    assertEquals("Layer4", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

  }

  @Test
  public void badSetActiveLayer() {
    try {
      this.model.setActiveLayer("Layer 1");
      fail("Tried to get the active layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    try {
      this.model.createNewProject(32, 32);
      this.model.setActiveLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.setActiveLayer(1);
      fail("Tried to get the active layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    try {
      this.model.createNewProject(32, 32);
      this.model.setActiveLayer(1);
      fail("Layer index is out of bounds.");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer index is out of bounds.",
          e.getMessage());
    }
  }

  @Test
  public void validSetFilter() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smolLow.ppm", 0, 0);

    assertEquals("119 119 119 119 119 119 119 119 119 \n"
        + "119 119 119 119 119 119 119 119 119 \n"
        + "119 119 119 119 119 119 119 119 119 \n"
        + "119 119 119 119 119 119 119 119 119 ", this.model.currentCanvas());

    this.model.setFilter("red-component", "Layer1");

    assertEquals("119 0 0 119 0 0 119 0 0 \n"
        + "119 0 0 119 0 0 119 0 0 \n"
        + "119 0 0 119 0 0 119 0 0 \n"
        + "119 0 0 119 0 0 119 0 0 ", this.model.currentCanvas());
  }

  @Test
  public void badSetFilter() {
    try {
      this.model.setFilter("Some Filter", "Layer1");
      fail("No loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.setFilter(null, null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name and/or Filter name cannot be null.",
          e.getMessage());
    }

    this.model.createNewProject(5, 5);
    try {
      this.model.setFilter("orange-component", "Layer1");
      fail("Invalid Filter");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      this.model.setFilter("redRGBA-component", "hello");
      fail("Unknown filter");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      this.model.setFilter(null, null);
      fail("Null is bad");
    } catch (IllegalArgumentException a) {
      //do nothing
    }
  }

  @Test
  public void validGetFilters() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "./res/smolLow.ppm", 0, 0);

    HashMap<String, Filter> temp = this.model.getFilters();

    temp.put("new-filter", new Normal());

    assertNotEquals(this.model.getFilters(), temp);
  }

  @Test
  public void validAddImageToLayer() {
    this.model.createNewProject(3, 4);
    assertEquals("0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 ", this.model.currentCanvas());

    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    String withImage = "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 \n"
        + "225 225 225 225 225 225 225 225 225 ";

    assertEquals(withImage, this.model.currentCanvas());

  }

  @Test
  public void currentCanvas4x4() {
    this.model.createNewProject(4, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 0 0 ", this.model.currentCanvas());

    assertEquals("Layer1", this.model.getActiveLayer().getName());

    this.model.addLayer("Layer2");
    assertEquals("Layer2", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

  }

  @Test
  public void currentCanvas4x2() {
    this.model.createNewProject(4, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 0 0 ", this.model.currentCanvas());
  }

  @Test
  public void currentCanvas3x4() {
    this.model.createNewProject(3, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 ", this.model.currentCanvas());
  }

  @Test
  public void currentCanvas2x2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());

    assertEquals("Layer1", this.model.getActiveLayer().getName());

    this.model.addLayer("Layer2");
    assertEquals("Layer2", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

  }

  @Test
  public void currentCanvasWithOpacityBlend() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());

    this.model.addLayer("Blue");
    this.model.addLayer("Green");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);
    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 0, 255, 128);

    assertEquals("127 0 128 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());
    this.model.setActiveLayer(2);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 0, 200);

    assertEquals("27 200 27 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());

  }

  @Test
  public void currentCanvasWithOpacityBlend2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());

    this.model.addLayer("Purple");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 255, 255);
    assertEquals("0 255 255 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());

    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(1, 0, 255, 0, 255, 128);

    assertEquals("0 255 255 128 0 128 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());
    assertEquals(2, this.model.getNumberOfLayers());

    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 255, 128);

    assertEquals("128 127 255 128 0 128 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());
  }
}