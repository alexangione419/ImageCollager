package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import view.ImageProjectView;
import view.PPMProjectTextView;

public class LayerImplTest {

  ImageProject project5x6;
  ImageProject project3x4;

  @Before
  public void setup() {
    this.project5x6 = new ProjectImpl();
    this.project5x6.createNewProject(5, 6);

    this.project3x4 = new ProjectImpl();
    this.project3x4.createNewProject(3, 4);

  }

  @Test
  public void testInvalidLayerCreation() {
    try {
      Layer lay = new LayerImpl("bad project", null);
      fail("Project cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

    try {
      Layer lay = new LayerImpl(null, this.project3x4);
      fail("Layer name cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

    try {
      Layer lay = new LayerImpl(null, null);
      fail("Arguments cannot be null");
    } catch (IllegalArgumentException a) {
      // pass
    }

  }

  @Test
  public void testValidLayerCreation() {
    // creation separate from a project
    Layer funLayer = new LayerImpl("layer", project3x4);
    assertEquals("layer", funLayer.getName());
    assertEquals(255, funLayer.getMaxPixel());
  }

  @Test
  public void testGetName() {
    Layer blue = new LayerImpl("blue", this.project3x4);
    assertEquals("blue", blue.getName());

  }

  @Test
  public void testGetTotalPixels() {
    Layer green = new LayerImpl("green", this.project5x6);
    assertEquals(30, green.getTotalPixels());

    Layer armadillo = new LayerImpl("armadillo", this.project3x4);
    assertEquals(12, armadillo.getTotalPixels());

  }

  @Test
  public void testGetLayerData() {
    // initial blank opaque layer
    int[][] default56 = new int[30][4];
    int[][] default34 = new int[12][4];


    Layer pineapple = new LayerImpl("pineappleLayer", this.project5x6);
    assertArrayEquals(default56, pineapple.getLayerData());
    Layer pineapple2 = new LayerImpl("pineappleLayer2", this.project3x4);
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


    Layer pineapple = new LayerImpl("pineappleLayer", this.project5x6);
    assertArrayEquals(default56, pineapple.getUnfilteredLayer());
    Layer pineapple2 = new LayerImpl("pineappleLayer2", this.project3x4);
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
    Layer yellow = new LayerImpl("yellow", this.project5x6);
    assertEquals(255, yellow.getMaxPixel());
  }

  @Test
  public void testSetPixelColor() {
//    this.project5x6.getActiveLayer().setPixelColor(0, 0, 255, 255, 255, 255);
//
//    ImageProjectView view = new PPMProjectTextView(this.project5x6);
//
//    assertEquals("255 255 255 255  0 0 0 0  \n"
//            + "0 0 0 0  0 0 0 0  ", view.currentCanvas());
//    assertEquals("255 255 255 255  0 0 0 255  \n"
//            + "0 0 0 255  0 0 0 255  ", view.currentCanvas());
  }

  @Test
  public void testAddInvalidImage() {
    Layer picadillo = new LayerImpl("picadillo", this.project5x6);
    try {
      picadillo.addImageToLayer("404.ppm", 0, 0);
      fail("Should not find the file");
    } catch (IllegalArgumentException a) {
      //pass
    }
  }

  @Test
  public void testClearLayer() {
    Layer pine = new LayerImpl("pine", this.project5x6);
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

    int[][] cleared56 = new int[30][4];
    pine.clearLayer();
    assertArrayEquals(cleared56, pine.getLayerData());
  }


  @Test
  public void testAddingImageToLayer() {
    Layer lots = new LayerImpl("topleft", this.project5x6);

    int[][] default56 = new int[30][4];
    assertArrayEquals(default56, lots.getLayerData());
    int[][] smolTopLeft = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {0, 0, 0, 0}};
    lots.addImageToLayer("smol.ppm", 0, 0);
    assertArrayEquals(smolTopLeft, lots.getLayerData());

    int[][] smolBottomRight = {{225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {119, 119, 119, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {119, 119, 119, 255},
            {119, 119, 119, 255}, {119, 119, 119, 255}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}};
    lots.addImageToLayer("smolLow.ppm", 2, 2);
    assertArrayEquals(smolBottomRight, lots.getLayerData());

    int[][] smolFallingOffTopRight = {{225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {0, 0, 0, 0}, {225, 225, 225, 255}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {0, 0, 0, 0}, {225, 225, 225, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {225, 225, 225, 255}, {225, 225, 225, 255}, {225, 225, 225, 255}, {119, 119, 119, 255},
            {119, 119, 119, 255}, {225, 225, 225, 255}, {0, 0, 0, 0}, {0, 0, 0, 0},
            {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}, {0, 0, 0, 0},
            {0, 0, 0, 0}, {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}};
    lots.addImageToLayer("smol.ppm", 4, 0);
    assertArrayEquals(smolFallingOffTopRight, lots.getLayerData());

  }

  @Test
  public void testAddImageWithBadCoords() {
    Layer fiveSix = new LayerImpl("fiveSix", this.project5x6);
    try {
      fiveSix.addImageToLayer("smol.ppm", -1, 0);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }
    try {
      fiveSix.addImageToLayer("smol.ppm", 1, -2);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      fiveSix.addImageToLayer("smol.ppm", 9, 3);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      fiveSix.addImageToLayer("smol.ppm", 3, 12);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      fiveSix.addImageToLayer("smol.ppm", -1, -4);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }

    try {
      fiveSix.addImageToLayer("smol.ppm", 10, 9);
      fail("Invalid Coordinates accepted");
    } catch (IllegalArgumentException a) {
      //do nothing
    }
  }

  @Test
  public void testApplyFilters() {
    Layer filterMe = new LayerImpl("filterTest", this.project3x4);
    filterMe.addImageToLayer("smolLow.ppm", 0, 0);
    int[][] beforeAll = {{119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255}, {119, 119, 119, 255},
            {119, 119, 119, 255}};
    assertArrayEquals(beforeAll, filterMe.getLayerData());
    int[][] afterRed = {{119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255}, {119, 0, 0, 255},
            {119, 0, 0, 255}};
    filterMe.applyFilter("red-component");
    assertArrayEquals(afterRed, filterMe.getLayerData());
    filterMe.applyFilter("normal");
    assertArrayEquals(beforeAll, filterMe.getLayerData());

  }

  @Test
  public void testBadApplyFilters() {
    Layer fail = new LayerImpl("fail", this.project5x6);
    try {
      fail.applyFilter("dne");
      fail("Filter does not exist");
    } catch (IllegalArgumentException a) {
      //do nothing
    }
  }


}