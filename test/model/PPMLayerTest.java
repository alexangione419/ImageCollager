package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class PPMLayerTest {

  ImageProject createWithin;
  ImageProject createSeparate;

  @Before
  public void setup() {
    this.createWithin = new PPMProject();
    this.createWithin.createNewProject(500, 600);

    this.createSeparate = new PPMProject();
    this.createSeparate.createNewProject(300, 400);

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
      Layer lay = new PPMLayer(null, this.createWithin);
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
    // creation through a project
    this.createWithin.addLayer("goodLayer");

    // creation separate from a project
    Layer separateCreate = new PPMLayer("layer", createSeparate);

  }

  @Test
  public void testGetName() {
    assertEquals("Layer 1", this.createWithin.getActiveLayer().getName());
    this.createWithin.addLayer("newLayer");
    assertEquals("newLayer", this.createWithin.getActiveLayer().getName());

    Layer blue = new PPMLayer("blue", this.createSeparate);
    assertEquals("blue", blue.getName());

  }

  @Test
  public void testGetTotalPixels() {
    assertEquals(300000, this.createWithin.getActiveLayer().getTotalPixels());

    Layer green = new PPMLayer("green", this.createSeparate);
    assertEquals(120000, green.getTotalPixels());
  }

  // ADD MORE TESTING FOR WHEN
  // - IMAGE ON LAYER
  // - BEFORE AND AFTER FILTER IS APPLIED
  @Test
  public void testGetLayerData() {
    // initial blank transparent layer
    int[][] withinDefault = new int[300000][4];
    int[][] separateDefault = new int[120000][4];

    assertArrayEquals(withinDefault, this.createWithin.getActiveLayer().getLayerData());

    Layer pineapple = new PPMLayer("pineappleLayer", this.createSeparate);
    assertArrayEquals(separateDefault, pineapple.getLayerData());

    this.createWithin.getActiveLayer().addImageToLayer("tako.ppm", 0, 0);
    assertArrayEquals(separateDefault, this.createWithin.getActiveLayer().getLayerData());
  }

  // ADD MORE TESTING FOR WHEN
  // - IMAGE ON LAYER
  // - BEFORE AND AFTER FILTER IS APPLIED
  @Test
  public void testGetUnfilteredLayer() {
    int[][] withinDefault = new int[300000][4];
    int[][] separateDefault = new int[120000][4];

    assertArrayEquals(withinDefault, this.createWithin.getActiveLayer().getLayerData());

    Layer orange = new PPMLayer("orange", this.createSeparate);
    assertArrayEquals(separateDefault, orange.getLayerData());
  }

  @Test
  public void testGetMaxPixel() {
    assertEquals(255, this.createWithin.getActiveLayer().getMaxPixel());

    Layer yellow = new PPMLayer("yellow", this.createSeparate);
    assertEquals(255, yellow.getMaxPixel());
  }

}