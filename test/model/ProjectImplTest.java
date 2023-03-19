package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class ProjectImplTest {

  ImageProject model;

  @Before
  public void init() {
    this.model = new ProjectImpl();
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
  public void badLoadProject() {
    try {
      this.model.loadProject(null);
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void badSaveProject() {
    this.model.createNewProject(4, 4);
    try {
      this.model.saveProject(null);
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSaveProject(){
    this.model.createNewProject(3, 5);
    this.model.addImageToLayer("Layer1", "smol.ppm", 0, 0);
    try {
      this.model.saveProject("P1.txt");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("P1.txt"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }
    assertNotNull(sc);

    assertEquals("P1", sc.next());
    assertEquals("3", sc.next());
    assertEquals("5", sc.next());
    assertEquals("Layer1", sc.next());
    assertEquals("normal", sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("225 225 225 255", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("0 0 0 0", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("0 0 0 0", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());
    assertEquals("0 0 0 0", sc.next() + " " + sc.next() + " " + sc.next() + " " + sc.next());





  }

  @Test
  public void validSaveImage() throws IOException {
    this.model.createNewProject(2, 2);
    this.model.saveImage("test.ppm");

    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 255);


    try {
      this.model.saveImage("P1.ppm");
    } catch (IOException io) {
      fail(io.getMessage());
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("P1.txt"));
    } catch (FileNotFoundException fnf) {
      fail("File not found");
    }

  }

  @Test
  public void badSaveImage() {
    try {
      try {
        this.model.saveImage(null);
      }
      catch (IOException e) {

      }
      fail("Null passed as an argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
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
  public void badGetWidth() {
    try {
      this.model.getWidth();
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
      this.model.addLayer("Test Layer " + i);
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
    this.model.addLayer("Test Layer");
    assertEquals(2, this.model.getNumberOfLayers());

    for (int i = 0; i < 32; i++) {
      this.model.addLayer("Test Layer " + i);
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
  }

  @Test
  public void validRemoveLayer() {
    this.model.createNewProject(32, 32);

    for (int i = 0; i < 32; i++) {
      this.model.addLayer("Test Layer " + i);
      assertEquals(2 + i, this.model.getNumberOfLayers());
      assertEquals("Test Layer " + i, this.model.getActiveLayer().getName());
    }

    assertEquals(33, this.model.getNumberOfLayers());
    assertEquals("Test Layer 31", this.model.getActiveLayer().getName());
    this.model.setActiveLayer("Test Layer 3");

    this.model.removeLayer("Test Layer 3");
    this.model.removeLayer("Test Layer 7");
    this.model.removeLayer("Test Layer 17");

    assertEquals(30, this.model.getNumberOfLayers());

    assertEquals("Test Layer 2", this.model.getActiveLayer().getName());
    this.model.removeLayer("Test Layer 2");
    assertEquals("Test Layer 1", this.model.getActiveLayer().getName());
    this.model.removeLayer("Test Layer 1");
    this.model.removeLayer("Layer1");
    assertEquals("Test Layer 0", this.model.getActiveLayer().getName());
    this.model.removeLayer("Test Layer 0");
    assertEquals("Test Layer 4", this.model.getActiveLayer().getName());

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
      this.model.addLayer("Layer 2");
      this.model.removeLayer("Layer ");
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
    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
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
    this.model.addLayer("Layer 2");
    this.model.addLayer("Layer 3");
    this.model.addLayer("Layer 4");
    assertEquals("Layer 4", this.model.getActiveLayer().getName());

    this.model.setActiveLayer("Layer 3");
    assertEquals("Layer 3", this.model.getActiveLayer().getName());
    this.model.setActiveLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());

    this.model.setActiveLayer(3);
    assertEquals("Layer 4", this.model.getActiveLayer().getName());
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
    } catch (IllegalArgumentException a)  {
      //do nothing
    }

    try {
      this.model.setFilter("red-component", "hello");
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
  public void testSetFilter() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "smolLow.ppm", 0, 0);

    this.model.setFilter("red-component", "Layer1");
    int[][] afterRed = {{119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}};
    assertArrayEquals(afterRed, this.model.getActiveLayer().getLayerData());
  }

  @Test
  public void testAddImageToLayer() {
    this.model.createNewProject(3, 4);
    assertArrayEquals(new int[12][4], this.model.getActiveLayer().getLayerData());
    this.model.addImageToLayer("Layer1", "smol.ppm", 0, 0);
    int[][] withImage = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}};
    assertArrayEquals(withImage, this.model.getActiveLayer().getLayerData());

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

    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
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

    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
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
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0 , 255);
    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 0, 255 , 128);

    assertEquals("127 0 128 0 0 0 \n"
        + "0 0 0 0 0 0 ", this.model.currentCanvas());
    this.model.setActiveLayer(2);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 0,200);

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

  @Test
  public void testBadSaveImage() {
    try {
      this.model.saveProject("name");
      fail("no project open");
    } catch (IllegalStateException s) {
      //pass
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.model.createNewProject(3, 4);

    try {
      this.model.saveProject(null);
      fail("null argument");
    } catch (IllegalArgumentException | IOException a) {
      //do nothing
    }

    try {
      this.model.saveProject(".ppm");
      fail("invalid name");
    } catch (IllegalArgumentException | IOException a) {
      //do nothing
    }
    try {
      this.model.saveProject("file");
      fail("invalid name-> needs suffix");
    } catch (IllegalArgumentException | IOException a) {
      //do nothing
    }

  }

  @Test
  public void testSaveImageOneLayer() {
    this.model.createNewProject(3, 4 );
    this.model.addImageToLayer("Layer1", "smol.ppm", 0, 0);

    try {
      this.model.saveImage("testSaveImage.ppm");
    } catch (IOException io) {
      // welp
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("testSaveImage.ppm"));
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

  }

  @Test
  public void testSaveImageMultipleLayers() {
    this.model.createNewProject(3, 4);
    this.model.addImageToLayer("Layer1", "smol.ppm", 0, 0);
    this.model.addLayer("Layer2");
    this.model.addImageToLayer("Layer2", "smolLow.ppm", 0, 0);

    try {
      this.model.saveImage("testSaveImage.ppm");
    } catch (IOException io) {
      // welp
    }

    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream("testSaveImage.ppm"));
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

}