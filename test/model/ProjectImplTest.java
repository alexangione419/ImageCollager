package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import controller.ControllerImpl;
import controller.ImageProjectController;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import javax.imageio.ImageIO;
import model.filters.Filter;
import model.filters.Normal;
import model.pixels.Pixel;
import model.pixels.RGBAPixel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ImageProjectView;
import view.PPMProjectTextView;

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

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("Layer 1");
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot contain a space or a linebreak.",
          e.getMessage());
    }

    this.init();

    try {
      this.model.createNewProject(32, 32);
      this.model.addLayer("Layer1");
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer Layer1 already exists in this project.",
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

    Pixel[][] full = new RGBAPixel[][]{
        {new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119)},
        {new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119)},
        {new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119)}};
    assertArrayEquals(full, this.model.currentCanvas());

    this.model.setFilter("red-component", "Layer1");

    Pixel[][] semi = new RGBAPixel[][]{
        {new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0)},
        {new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0)},
        {new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0),
            new RGBAPixel(255, 119, 0, 0)}};
    assertArrayEquals(semi, this.model.currentCanvas());
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
    Pixel[][] threeFours = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};

    assertArrayEquals(threeFours, this.model.currentCanvas());

    this.model.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel[][] withImage = new RGBAPixel[][]{
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

    assertArrayEquals(withImage, this.model.currentCanvas());

  }

  @Test
  public void currentCanvas4x4() {
    this.model.createNewProject(4, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());
    Pixel[][] fourFours = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};

    assertArrayEquals(fourFours, this.model.currentCanvas());

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

    Pixel[][] fourTwos = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(fourTwos, this.model.currentCanvas());
  }

  @Test
  public void currentCanvas3x4() {
    this.model.createNewProject(3, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    Pixel[][] threeFours = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(threeFours, this.model.currentCanvas());
  }

  @Test
  public void currentCanvas2x2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    Pixel[][] blank = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(blank, this.model.currentCanvas());

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
    Pixel[][] twoTwos = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};

    assertArrayEquals(twoTwos, this.model.currentCanvas());

    this.model.addLayer("Blue");
    this.model.addLayer("Green");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);
    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 0, 255, 128);

    Pixel[][] twoTwosWithColor = new RGBAPixel[][]{
        {new RGBAPixel(255, 127, 0, 128),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwosWithColor, this.model.currentCanvas());
    this.model.setActiveLayer(2);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 0, 200);

    Pixel[][] twoTwosWithColor2 = new RGBAPixel[][]{
        {new RGBAPixel(255, 27, 200, 27),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwosWithColor2, this.model.currentCanvas());

  }

  @Test
  public void currentCanvasWithOpacityBlend2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    Pixel[][] twoTwos = new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwos, this.model.currentCanvas());

    this.model.addLayer("Purple");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 255, 255);
    Pixel[][] twoTwos2 = new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwos2, this.model.currentCanvas());

    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(1, 0, 255, 0, 255, 128);

    Pixel[][] twoTwos3 = new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 127, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwos3, this.model.currentCanvas());
    assertEquals(2, this.model.getNumberOfLayers());

    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 255, 128);

    Pixel[][] twoTwos4 = new RGBAPixel[][]{
        {new RGBAPixel(255, 128, 127, 255),
            new RGBAPixel(255, 255, 255, 255)},
        {new RGBAPixel(255, 255, 127, 255),
            new RGBAPixel(255, 255, 255, 255)}};
    assertArrayEquals(twoTwos4, this.model.currentCanvas());
  }

  @Test
  public void testGetActiveLayerName() {
    this.model.createNewProject(3, 4);
    assertEquals("Layer1", this.model.getActiveLayerName());
    this.model.addLayer("Cancun");
    assertEquals("Cancun", this.model.getActiveLayerName());
    this.model.setActiveLayer("Layer1");
    assertEquals("Layer1", this.model.getActiveLayerName());
    this.model.setActiveLayer(1);
    assertEquals("Cancun", this.model.getActiveLayerName());

  }


  @Test
  public void testGetName() {
    try {
      this.model.getName();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }

    this.model.createNewProject(3, 3);
    assertEquals(this.model.getName(), "Untitled Project");

    this.model.setName("Project1");
    assertEquals(this.model.getName(), "Project1");
  }

  @Test
  public void testSetName() {
    try {
      this.model.setName("Project 1");
      fail();
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }

    this.model.createNewProject(3, 3);
    assertEquals(this.model.getName(), "Untitled Project");

    try {
      this.model.setName(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot be null.", e.getMessage());
    }

    try {
      this.model.setName("");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot be an empty string or "
          + "just whitespace.", e.getMessage());
    }

    try {
      this.model.setName(System.lineSeparator());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot be an empty string or "
          + "just whitespace.", e.getMessage());
    }

    try {
      this.model.setName("\n");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot be an empty string or just whitespace.",
              e.getMessage());
    }

    try {
      this.model.setName(" ");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot be an empty string or just whitespace.",
              e.getMessage());
    }

    try {
      this.model.setName("Project 1");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Project name cannot contain a space or a linebreak.", e.getMessage());
    }

    this.model.setName("Project1");
    assertEquals(this.model.getName(), "Project1");
  }


  @Test
  public void testImageRepresentation() {
    this.model.createNewProject(200, 200);
    this.model.addImageToLayer("Layer1", "./res/chromelogo.ppm", 0, 0);

    Appendable output = new StringBuilder();
    Readable input = new StringReader("");
    ImageProjectView view = new PPMProjectTextView(this.model, output);
    ImageProjectController controller = new ControllerImpl(this.model, view, input);

    BufferedImage img = this.model.getImageRepresentation();

    BufferedImage img2 = null;
    try {
      img2 = ImageIO.read(new File("strawberry.jpg"));
    } catch (IOException e) {
    }

//    BufferedImage img2 = new BufferedImage()
    Pixel[][] proj = this.model.currentCanvas();

    for (int y = 0; y < this.model.getWidth(); y++) {
      for (int x = 0; x < this.model.getHeight(); x++) {
        int rgbVal = (proj[y][x].getRed() << 16)
            + (proj[y][x].getGreen() << 8) + proj[y][x].getBlue();

        assertEquals(rgbVal, img.getRGB(y, x));
      }
    }
  }
}