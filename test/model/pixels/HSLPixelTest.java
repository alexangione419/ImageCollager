package model.pixels;

import static org.junit.Assert.*;

import model.PixelTest;
import org.junit.Test;

public class HSLPixelTest extends PixelTest {

  @Test
  public void badConstructor() {
    try {
      HSLPixel p = new HSLPixel(400, 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Hue value cannot be less than 0 or more than 360.", e.getMessage());
    }

    try {
      HSLPixel p = new HSLPixel(-30, 0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Hue value cannot be less than 0 or more than 360.", e.getMessage());
    }

    try {
      HSLPixel p = new HSLPixel(0, -1, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Saturation value cannot be less than 0 or more than 1.", e.getMessage());
    }

    try {
      HSLPixel p = new HSLPixel(0, 2, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Saturation value cannot be less than 0 or more than 1.", e.getMessage());
    }

    try {
      HSLPixel p = new HSLPixel(0, 0.5, -1);
    } catch (IllegalArgumentException e) {
      assertEquals("Light value cannot be less than 0 or more than 1.", e.getMessage());
    }

    try {
      HSLPixel p = new HSLPixel(0, 0.5, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Light value cannot be less than 0 or more than 1.", e.getMessage());
    }
  }


  @Test
  public void getHue() {
    assertEquals(0, this.redHSL.getHue(), 0.1);
    assertEquals(120, this.greenHSL.getHue(), 0.1);
    assertEquals(240, this.blueHSL.getHue(), 0.1);
  }

  @Test
  public void getSaturation() {
    assertEquals(1, this.redHSL.getSaturation(), 0.1);
    assertEquals(1, this.greenHSL.getSaturation(), 0.1);
    assertEquals(1, this.blueHSL.getSaturation() , 0.1);
  }

  @Test
  public void getLight() {
    assertEquals(0.5, this.redHSL.getLight(), 0.1);
    assertEquals(0.5, this.greenHSL.getLight(), 0.1);
    assertEquals(0.5, this.blueHSL.getLight(), 0.1);
  }

}