package model;

import static org.junit.Assert.*;

import model.pixels.Pixel;
import model.pixels.PixelUtils;
import model.pixels.RGBAPixel;
import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for the {@code RGBAPixel} class and the {@code PixelUtils} class.
 */
public class PixelTest {

  protected RGBAPixel redRGBA;
  protected RGBAPixel greenRGBA;
  protected RGBAPixel blueRGBA;
  protected RGBAPixel cyanRGBA;
  protected RGBAPixel purpleRGBA;
  protected RGBAPixel transparentWhiteRGBA;
  protected RGBAPixel whiteRGBA;
  protected RGBAPixel blackRGBA;
  protected RGBAPixel black100RGBA;

  @Before
  public void init() {
    this.redRGBA = new RGBAPixel(255, 255, 0, 0);
    this.greenRGBA = new RGBAPixel(255, 0, 255, 0);
    this.blueRGBA = new RGBAPixel(255, 0, 0, 255);
    this.cyanRGBA = new RGBAPixel(255, 0, 255, 255);
    this.purpleRGBA = new RGBAPixel(255, 255, 0, 255);
    this.transparentWhiteRGBA = new RGBAPixel(255, 255, 255, 255, 128);
    this.whiteRGBA = new RGBAPixel(255, 255, 255, 255, 255);
    this.blackRGBA = new RGBAPixel(255, 0, 0, 0, 255);
    this.black100RGBA = new RGBAPixel(100, 0, 0, 0, 100);
  }

  @Test
  public void validToString() {
    assertEquals("255 0 0 255 ", this.redRGBA.toString());
    assertEquals("0 255 0 255 ", this.greenRGBA.toString());
    assertEquals("0 0 255 255 ", this.blueRGBA.toString());
    assertEquals("0 255 255 255 ", this.cyanRGBA.toString());
    assertEquals("255 0 255 255 ", this.purpleRGBA.toString());
    assertEquals("255 255 255 128 ", this.transparentWhiteRGBA.toString());
  }

  @Test
  public void validToStringRGB() {
    assertEquals("255 0 0 ", this.redRGBA.toStringRGB());
    assertEquals("0 255 0 ", this.greenRGBA.toStringRGB());
    assertEquals("0 0 255 ", this.blueRGBA.toStringRGB());
    assertEquals("0 255 255 ", this.cyanRGBA.toStringRGB());
    assertEquals("255 0 255 ", this.purpleRGBA.toStringRGB());
    assertEquals("255 255 255 ", this.transparentWhiteRGBA.toStringRGB());
  }

  @Test
  public void validToStringHSL() {
    assertEquals("0.0 1.0 0.5 ", this.redRGBA.toStringHSL());
    assertEquals("120.0 1.0 0.5 ", this.greenRGBA.toStringHSL());
    assertEquals("240.0 1.0 0.5 ", this.blueRGBA.toStringHSL());
    assertEquals("180.0 1.0 0.5 ", this.cyanRGBA.toStringHSL());
    assertEquals("300.0 1.0 0.5 ", this.purpleRGBA.toStringHSL());
    assertEquals("0.0 0.0 1.0 ", this.transparentWhiteRGBA.toStringHSL());
  }
}