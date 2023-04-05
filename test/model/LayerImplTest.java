package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import model.pixels.Pixel;
import model.pixels.PixelUtils;
import model.pixels.RGBAPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for {@code Layer}s.
 */
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
  public void badLayerCreation() {
    try {
      Layer lay = new LayerImpl("bad model", null);
      fail("Project cannot be null");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer must have a valid name and project.", e.getMessage());
    }

    try {
      Layer lay = new LayerImpl(null, this.project3x4);
      fail("Layer name cannot be null");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer must have a valid name and project.", e.getMessage());
    }

    try {
      Layer lay = new LayerImpl(null, null);
      fail("Arguments cannot be null");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer must have a valid name and project.", e.getMessage());
    }
  }

  @Test
  public void validLayerCreation() {
    // creation separate from a model
    Layer funLayer = new LayerImpl("layer", this.project3x4);
    assertEquals("layer", funLayer.getName());
    assertEquals(255, funLayer.getMaxPixel());
  }

  @Test
  public void testGetName() {
    Layer blue = new LayerImpl("blueRGBA", this.project3x4);
    assertEquals("blueRGBA", blue.getName());
  }

  @Test
  public void validGetTotalPixels() {
    Layer green = new LayerImpl("greenRGBA", this.project5x6);
    assertEquals(30, green.getTotalPixels());

    Layer armadillo = new LayerImpl("armadillo", this.project3x4);
    assertEquals(12, armadillo.getTotalPixels());

  }

  @Test
  public void validGetLayerData() {
    // initial blank opaque layer
    Pixel p0 = new RGBAPixel(255, 0, 0, 0, 0);
    Pixel p1 = new RGBAPixel(255, 225, 225, 225);
    Pixel p2 = new RGBAPixel(255, 225, 0, 0);

    Layer pineapple = new LayerImpl("pineappleLayer", this.project5x6);

    for (Pixel[] pList : pineapple.getLayerData()) {
      for (Pixel p : pList) {
        assertEquals("255 255 255 ", p.toStringRGB());
      }
    }

    Layer pineapple2 = new LayerImpl("pineappleLayer2", this.project3x4);
    pineapple2.addImageToLayer("./res/smol.ppm", 0, 0);

    for (Pixel[] pList : pineapple2.getLayerData()) {
      for (Pixel p : pList) {
        assertEquals("225 225 225 ", p.toStringRGB());
      }
    }

    pineapple2.applyFilter("red-component");
    for (Pixel[] pList : pineapple2.getLayerData()) {
      for (Pixel p : pList) {
        assertEquals("225 0 0 ", p.toStringRGB());
      }
    }
  }

  @Test
  public void validGetUnfilteredLayer() {
    this.project3x4.createNewProject(3, 4);
    this.project3x4.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    assertArrayEquals(new RGBAPixel[][]{
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
            new RGBAPixel(255, 225, 225, 225)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("red-component", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)},
        {new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)},
        {new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)}}, this.project3x4.currentCanvas());

    for (Pixel[] pList : this.project3x4.getActiveLayer().getUnfilteredLayer()) {
      for (Pixel p : pList) {
        assertEquals("225 225 225 ", p.toStringRGB());
      }
    }
  }

  @Test
  public void validGetMaxPixel() {
    Layer yellow = new LayerImpl("yellow", this.project5x6);
    assertEquals(255, yellow.getMaxPixel());
  }

  @Test
  public void testSetPixelColor() {
    this.project3x4.getActiveLayer().setPixelColor(0, 0, 0, 0, 0, 255);

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 0, 0),
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
            new RGBAPixel(255, 255, 255, 255)}}, this.project3x4.currentCanvas());

    Pixel turquoiseGreen = new RGBAPixel(255, 30, 89, 69);
    Pixel pastelBlue = new RGBAPixel(255, 93, 155, 155);
    Pixel mahoganyBrown = new RGBAPixel(255, 76, 47, 39);

    this.project3x4.getActiveLayer().setPixelColor(0, 0, turquoiseGreen);
    this.project3x4.getActiveLayer().setPixelColor(1, 0, pastelBlue);
    this.project3x4.getActiveLayer().setPixelColor(2, 0, mahoganyBrown);

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 30, 89, 69),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 93, 155, 155),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 76, 47, 39),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)}},
        this.project3x4.currentCanvas());
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
    this.project3x4.createNewProject(3, 4);
    this.project3x4.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel[][] threeFoursWithImage = new RGBAPixel[][]{
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
    assertArrayEquals(threeFoursWithImage, this.project3x4.currentCanvas());

    this.project3x4.getActiveLayer().clearLayer();
    Pixel[][] threeFoursWhite = new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255)},
        {new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255)},
        {new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255),
            new RGBAPixel(255, 0, 0, 0, 255)}};
    assertArrayEquals(threeFoursWhite, this.project3x4.currentCanvas());
  }


  @Test
  public void testAddingImageToLayer() {
    this.project3x4.createNewProject(3, 4);
    this.project3x4.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel[][] threeFoursWithImage = new RGBAPixel[][]{
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
    assertArrayEquals(threeFoursWithImage, this.project3x4.currentCanvas());

    this.project3x4.addImageToLayer("Layer1", "./res/smolLow.ppm", 2, 2);

    assertArrayEquals(new RGBAPixel[][]{
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
            new RGBAPixel(255, 119, 119, 119),
            new RGBAPixel(255, 119, 119, 119)}}, this.project3x4.currentCanvas());
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
    this.project3x4.createNewProject(3, 4);
    this.project3x4.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel turquoiseGreen = new RGBAPixel(255, 30, 89, 69);
    Pixel pastelBlue = new RGBAPixel(255, 93, 155, 155);
    Pixel mahoganyBrown = new RGBAPixel(255, 76, 47, 39);
    Pixel purple = new RGBAPixel(255, 255, 0, 255);

    this.project3x4.getActiveLayer().setPixelColor(0, 0, turquoiseGreen);
    this.project3x4.getActiveLayer().setPixelColor(1, 0, pastelBlue);
    this.project3x4.getActiveLayer().setPixelColor(2, 0, mahoganyBrown);

    this.project3x4.setFilter("normal", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 30, 89, 69),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 93, 155, 155),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 76, 47, 39),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("red-component", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 30, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)},
        {new RGBAPixel(255, 93, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)},
        {new RGBAPixel(255, 76, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0),
            new RGBAPixel(255, 225, 0, 0)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("green-component", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 89, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0)},
        {new RGBAPixel(255, 0, 155, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0)},
        {new RGBAPixel(255, 0, 47, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0),
            new RGBAPixel(255, 0, 225, 0)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("blue-component", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 0, 0, 69),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225)},
        {new RGBAPixel(255, 0, 0, 155),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225)},
        {new RGBAPixel(255, 0, 0, 39),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225),
            new RGBAPixel(255, 0, 0, 225)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("brighten-intensity", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 92, 151, 131),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 227, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 130, 101, 93),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("darken-intensity", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 0, 27, 7),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 0, 21, 21),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 22, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("brighten-luma", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 105, 164, 144),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 234, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 128, 99, 91),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("darken-luma", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 0, 14, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 0, 14, 14),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 24, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("brighten-value", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 119, 178, 158),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 248, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {new RGBAPixel(255, 152, 123, 115),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)}},
        this.project3x4.currentCanvas());
    this.project3x4.setFilter("darken-value", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)},
            {new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0),
                new RGBAPixel(255, 0, 0, 0)}},
        this.project3x4.currentCanvas());

  }

  @Test
  public void testApplyBlendFilters() {
    this.project3x4.createNewProject(3, 4);
    this.project3x4.addImageToLayer("Layer1", "./res/smol.ppm", 0, 0);

    Pixel turquoiseGreen = new RGBAPixel(255, 30, 89, 69);
    Pixel pastelBlue = new RGBAPixel(255, 93, 155, 155);
    Pixel mahoganyBrown = new RGBAPixel(255, 76, 47, 39);
    Pixel purple = new RGBAPixel(255, 255, 0, 255);

    this.project3x4.getActiveLayer().setPixelColor(0, 0, turquoiseGreen);
    this.project3x4.getActiveLayer().setPixelColor(1, 0, pastelBlue);
    this.project3x4.getActiveLayer().setPixelColor(2, 0, mahoganyBrown);

    this.project3x4.setFilter("normal", "Layer1");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 30, 89, 69),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 93, 155, 155),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 76, 47, 39),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    this.project3x4.addLayer("Layer2");
    this.project3x4.getActiveLayer().setPixelColor(0, 0, purple);
    this.project3x4.getActiveLayer().setPixelColor(1, 0, purple);
    this.project3x4.getActiveLayer().setPixelColor(2, 0, purple);

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("difference", "Layer2");

    assertNotEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 225, 89, 186),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 162, 155, 100),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 179, 47, 216),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("normal", "Layer2");

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 255, 0, 255),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225)},
            {new RGBAPixel(255, 255, 0, 255),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225)},
            {new RGBAPixel(255, 255, 0, 255),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("multiply", "Layer2");

    assertEquals(0.5, purple.getLight(), 0.1);
    assertEquals(0.23333, turquoiseGreen.getLight(), 0.00001);
    assertEquals(0.48627, pastelBlue.getLight(), 0.00001);
    assertEquals(0.22549, mahoganyBrown.getLight(), 0.00001);

    RGBAPixel purpGreen = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(), 0.11665);
    RGBAPixel purpBlue = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(), 0.243135);
    RGBAPixel purpBrown = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(), 0.112745);

    assertArrayEquals(new RGBAPixel[][]{{purpGreen,
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
            {purpBlue,
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225)},
            {purpBrown,
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225),
                new RGBAPixel(255, 225, 225, 225)}},
        this.project3x4.currentCanvas());

    this.project3x4.setFilter("normal", "Layer2");

    assertArrayEquals(new RGBAPixel[][]{
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)},
        {new RGBAPixel(255, 255, 0, 255),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225),
            new RGBAPixel(255, 225, 225, 225)}}, this.project3x4.currentCanvas());

    this.project3x4.setFilter("screen", "Layer2");

    assertEquals(0.5, purple.getLight(), 0.1);
    assertEquals(0.23333, turquoiseGreen.getLight(), 0.00001);
    assertEquals(0.48627, pastelBlue.getLight(), 0.00001);
    assertEquals(0.22549, mahoganyBrown.getLight(), 0.00001);

    purpGreen = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(),
        (1 - ((1 - 0.5) * (1 - turquoiseGreen.getLight()))));
    purpBlue = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(),
        (1 - ((1 - 0.5) * (1 - pastelBlue.getLight()))));
    purpBrown = PixelUtils.convertHSLtoRGBA(
        purple.getHue(), purple.getSaturation(),
        (1 - ((1 - 0.5) * (1 - mahoganyBrown.getLight()))));

    assertArrayEquals(new RGBAPixel[][]{{purpGreen,
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255),
            new RGBAPixel(255, 255, 255, 255)},
            {purpBlue,
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255)},
            {purpBrown,
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255, 255)}},
        this.project3x4.currentCanvas());

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

  @Test
  public void testMakeVisible() {
    this.project3x4.createNewProject(2, 2);

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255, 255)},
            {new RGBAPixel(255, 255, 255, 255, 255),
                new RGBAPixel(255, 255, 255, 255, 255)}},
        this.project3x4.currentCanvas());

    this.project3x4.getActiveLayer().setPixelColor(0, 0, 255, 0, 0, 0);
    this.project3x4.getActiveLayer().setPixelColor(1, 0, 0, 255, 0, 0);
    this.project3x4.getActiveLayer().setPixelColor(0, 1, 0, 0, 255, 0);

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 0, 0, 0, 255),
                new RGBAPixel(255, 0, 0, 0, 255)},
            {new RGBAPixel(255, 0, 0, 0, 255),
                new RGBAPixel(255, 255, 255, 255, 255)}},
        this.project3x4.currentCanvas());

    this.project3x4.getActiveLayer().makeVisible();

    assertArrayEquals(new RGBAPixel[][]{
            {new RGBAPixel(255, 255, 0, 0, 255),
                new RGBAPixel(255, 0, 0, 255, 255)},
            {new RGBAPixel(255, 0, 255, 0, 255),
                new RGBAPixel(255, 255, 255, 255, 255)}},
        this.project3x4.currentCanvas());
  }

}





















