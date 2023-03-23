package model;

import static org.junit.Assert.assertEquals;

import model.pixels.RGBAPixel;
import org.junit.Test;

public class RGBAPixelTest extends PixelTest {

  @Test
  public void badConstructor() {
    try {
      RGBAPixel p = new RGBAPixel(0, 0, 0, 0);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Max Pixel Value should be greater than 0.", e.getMessage());
    }

    try {
      RGBAPixel p = new RGBAPixel(-255, 0, 0, 0);
    }
    catch (IllegalArgumentException e) {
      assertEquals("Max Pixel Value should be greater than 0.", e.getMessage());
    }

    try {
      RGBAPixel p = new RGBAPixel(255, -1, 0, 0);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The values of the red, green, and blue component "
          + "must all be 0 or greater.", e.getMessage());
    }

    try {
      RGBAPixel p = new RGBAPixel(255, 0, -10, 0);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The values of the red, green, and blue component "
          + "must all be 0 or greater.", e.getMessage());
    }

    try {
      RGBAPixel p = new RGBAPixel(255, 0, 0, -156);
    }
    catch (IllegalArgumentException e) {
      assertEquals("The values of the red, green, and blue component "
          + "must all be 0 or greater.", e.getMessage());
    }

    try {
      RGBAPixel p = new RGBAPixel(255, 0, 0, 156, -75);
    }
    catch (IllegalArgumentException e) {
      assertEquals("This Pixel's alpha component"
          + " must all be 0 or greater.", e.getMessage());
    }

    try {
      this.black100RGBA = new RGBAPixel(100, 0, 0, 255);
    }
    catch (IllegalArgumentException e) {
      assertEquals("This Pixel's blue component cannot have a value greater than 100."
          , e.getMessage());
    }

    try {
      this.cyanRGBA = new RGBAPixel(100, 0, 255, 100);
    }
    catch (IllegalArgumentException e) {
      assertEquals("This Pixel's green component cannot have a value greater than 100."
          , e.getMessage());
    }

    try {
      this.purpleRGBA = new RGBAPixel(200, 300, 0, 200);
    }
    catch (IllegalArgumentException e) {
      assertEquals("This Pixel's red component cannot have a value greater than 200."
          , e.getMessage());
    }
  }

  @Test
  public void getRed() {
    assertEquals(255, this.redRGBA.getRed());
    assertEquals(0, this.greenRGBA.getRed());
    assertEquals(0, this.blueRGBA.getRed());
    assertEquals(0, this.cyanRGBA.getRed());
    assertEquals(255, this.purpleRGBA.getRed());
    assertEquals(255, this.transparentWhiteRGBA.getRed());
  }

  @Test
  public void getGreen() {
    assertEquals(0, this.redRGBA.getGreen());
    assertEquals(255, this.greenRGBA.getGreen());
    assertEquals(0, this.blueRGBA.getGreen());
    assertEquals(255, this.cyanRGBA.getGreen());
    assertEquals(0, this.purpleRGBA.getGreen());
    assertEquals(255, this.transparentWhiteRGBA.getGreen());
  }

  @Test
  public void getBlue() {
    assertEquals(0, this.redRGBA.getBlue());
    assertEquals(0, this.greenRGBA.getBlue());
    assertEquals(255, this.blueRGBA.getBlue());
    assertEquals(255, this.cyanRGBA.getBlue());
    assertEquals(255, this.purpleRGBA.getBlue());
    assertEquals(255, this.transparentWhiteRGBA.getBlue());
  }

  @Test
  public void getAlpha() {
    assertEquals(255, this.redRGBA.getAlpha());
    assertEquals(255, this.greenRGBA.getAlpha());
    assertEquals(255, this.blueRGBA.getAlpha());
    assertEquals(128, this.transparentWhiteRGBA.getAlpha());
    this.cyanRGBA = new RGBAPixel(100, 0, 100, 100);
    assertEquals(100, this.cyanRGBA.getAlpha());
  }

}
