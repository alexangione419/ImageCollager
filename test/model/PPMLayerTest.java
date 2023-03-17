package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import view.ImageProjectView;
import view.PPMProjectTextView;

import java.io.FileNotFoundException;

public class PPMLayerTest {

  ImageProject project5x6;
  ImageProject project3x4;

  @Before
  public void setup() {
    this.project5x6 = new PPMProject();
    this.project5x6.createNewProject(5, 6);

    this.project3x4 = new PPMProject();
    this.project3x4.createNewProject(3, 4);

  }

  @Test
  public void testInvalidLayerCreation() {
    try {
      Layer lay = new PPMLayer("bad project", null);
      fail("Project cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

    try {
      Layer lay = new PPMLayer(null, this.project3x4);
      fail("Layer name cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

    try {
      Layer lay = new PPMLayer(null, null);
      fail("Arguments cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

  }

  @Test
  public void testValidLayerCreation() {
    // creation separate from a project
    Layer funLayer = new PPMLayer("layer", project3x4);
    assertEquals("layer", funLayer.getName());
    assertEquals(255, funLayer.getMaxPixel());
  }

  @Test
  public void testGetName() {
    Layer blue = new PPMLayer("blue", this.project3x4);
    assertEquals("blue", blue.getName());

  }

  @Test
  public void testGetTotalPixels() {
    Layer green = new PPMLayer("green", this.project5x6);
    assertEquals(30, green.getTotalPixels());

    Layer armadillo = new PPMLayer("armadillo", this.project3x4);
    assertEquals(12, armadillo.getTotalPixels());

  }

  @Test
  public void testGetLayerData() {
    // initial blank opaque layer
    int[][] default56 = new int[30][4];
    int[][] default34 = new int[12][4];


    Layer pineapple = new PPMLayer("pineappleLayer", this.project5x6);
    assertArrayEquals(default56, pineapple.getLayerData());
    Layer pineapple2 = new PPMLayer("pineappleLayer2", this.project3x4);
    assertArrayEquals(default34, pineapple2.getLayerData());

    int[][] default34new = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}};
    pineapple2.addImageToLayer("smol.ppm", 0, 0);
    assertArrayEquals(default34new, pineapple2.getLayerData());
    pineapple2.applyFilter("red-component");

    int[][] default34red = {{225, 0, 0, 255}, {225, 0, 0, 255}, {225, 0, 0, 255},
            {225, 0, 0, 255}, {225, 0, 0, 255}, {225, 0, 0, 255}, {225, 0, 0, 255},
            {225, 0, 0, 255}, {225, 0, 0, 255}, {225, 0, 0, 255}, {225, 0, 0, 255},
            {225, 0, 0, 255}};
    assertArrayEquals(default34red, pineapple2.getLayerData());
  }

  @Test
  public void testGetUnfilteredLayer() {
    int[][] default56 = new int[30][4];
    int[][] default34 = new int[12][4];


    Layer pineapple = new PPMLayer("pineappleLayer", this.project5x6);
    assertArrayEquals(default56, pineapple.getUnfilteredLayer());
    Layer pineapple2 = new PPMLayer("pineappleLayer2", this.project3x4);
    assertArrayEquals(default34, pineapple2.getUnfilteredLayer());

    int[][] default34new = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}};
    pineapple2.addImageToLayer("smol.ppm", 0, 0);
    assertArrayEquals(default34new, pineapple2.getUnfilteredLayer());
    pineapple2.applyFilter("red-component");

    assertArrayEquals(default34new, pineapple2.getUnfilteredLayer());
  }

  @Test
  public void testGetMaxPixel() {
    Layer yellow = new PPMLayer("yellow", this.project5x6);
    assertEquals(255, yellow.getMaxPixel());
  }

  @Test
<<<<<<< Updated upstream
  public void testSetPixelColor() {
    this.createWithin.createNewProject(2, 2);
    this.createWithin.getActiveLayer().setPixelColor(0, 0, 255, 255, 255, 255);

    ImageProjectView view = new PPMProjectTextView(this.createWithin);

<<<<<<< Updated upstream
    assertEquals("255 255 255 255  0 0 0 0  \n"
        + "0 0 0 0  0 0 0 0  ", view.currentCanvas());
=======
    assertEquals("255 255 255 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  ", view.currentCanvas());
=======
  public void testAddInvalidImage() {
    Layer picadillo = new PPMLayer("picadillo", this.project5x6);
    try {
      picadillo.addImageToLayer("404.ppm", 0, 0);
      fail("Should not find the file");
    } catch (IllegalArgumentException a) {
      //pass
    }

    try {
      picadillo.addImageToLayer("404.ppm", 0, 0);
      fail("Should not find the file");
    } catch (IllegalArgumentException a) {
      //pass
    }
  }

  @Test
  public void testClearLayer() {
    Layer pine = new PPMLayer("pine", this.project5x6);
    pine.addImageToLayer("smol.ppm", 0, 0);
    int[][] default56new = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}};
    assertArrayEquals(default56new, pine.getLayerData());
  }


  @Test
  public void testAddingImageToLayer() {
    Layer topLeft = new PPMLayer("topleft", this.project5x6);

    int[][] default56 = new int[30][4];
    assertArrayEquals(default56, topLeft.getLayerData());


>>>>>>> Stashed changes
>>>>>>> Stashed changes
  }

}