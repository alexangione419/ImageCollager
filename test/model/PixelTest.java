package model;

import static org.junit.Assert.*;

import model.pixels.HSLPixel;
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

  protected HSLPixel whiteHSL;
  protected HSLPixel blackHSL;
  protected HSLPixel redHSL;
  protected HSLPixel blueHSL;
  protected HSLPixel greenHSL;
  protected HSLPixel cyanHSL;
  protected HSLPixel purpleHSL;

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

    this.redHSL = new HSLPixel(0, 1.0, 0.5);
    this.greenHSL = new HSLPixel(120, 1.0, 0.5);
    this.blueHSL = new HSLPixel(240, 1.0, 0.5);

    this.blackHSL = new HSLPixel(0, 0, 0.0);
    this.whiteHSL = new HSLPixel(300, 0.0, 1.0);

    this.cyanHSL = new HSLPixel(180, 1.0, 0.5);
    this.purpleHSL = new HSLPixel(300, 1.0, 0.5);
  }

  @Test
  public void convertRGBAtoHSL() {
    assertEquals("255 0 0 255 ", this.redRGBA.toString());
    assertEquals("255 255 255 255 ", this.whiteRGBA.toString());

    Pixel redHSL = PixelUtils.convertRGBAtoHSL(this.redRGBA);
    Pixel cyanHSL = PixelUtils.convertRGBAtoHSL(this.cyanRGBA);
    Pixel whiteHSL = PixelUtils.convertRGBAtoHSL(this.whiteRGBA);

    assertEquals("0.0 1.0 0.5 ", redHSL.toString());
    assertEquals("0.0 0.0 1.0 ", whiteHSL.toString());
    assertEquals(this.cyanHSL.toString(), cyanHSL.toString());
  }

  @Test
  public void convertHSLtoRGBA() {
    HSLPixel redHSL = PixelUtils.convertRGBAtoHSL(this.redRGBA);
    HSLPixel whiteHSL = PixelUtils.convertRGBAtoHSL(this.whiteRGBA);
    Pixel mystery = PixelUtils.convertHSLtoRGBA(
        new HSLPixel(14.0, 0.813, 0.624));

    assertEquals("0.0 1.0 0.5 ", redHSL.toString());
    assertEquals("0.0 0.0 1.0 ", whiteHSL.toString());

    Pixel redRGBA = PixelUtils.convertHSLtoRGBA(redHSL);
    Pixel whiteRGBA = PixelUtils.convertHSLtoRGBA(whiteHSL);

    assertEquals("255 0 0 255 ", redRGBA.toString());
    assertEquals("255 255 255 255 ", whiteRGBA.toString());
    assertEquals("237 117 81 255 ", mystery.toString());
  }

  @Test
  public void validToString() {
    assertEquals("255 0 0 255 ", this.redRGBA.toString());
    assertEquals("0 255 0 255 ", this.greenRGBA.toString());
    assertEquals("0 0 255 255 ", this.blueRGBA.toString());
    assertEquals("0 255 255 255 ", this.cyanRGBA.toString());
    assertEquals("255 0 255 255 ", this.purpleRGBA.toString());
    assertEquals("255 255 255 128 ", this.transparentWhiteRGBA.toString());


    assertEquals("0.0 1.0 0.5 ", this.redHSL.toString());
    assertEquals("120.0 1.0 0.5 ", this.greenHSL.toString());
    assertEquals("240.0 1.0 0.5 ", this.blueHSL.toString());
    assertEquals("180.0 1.0 0.5 ", this.cyanHSL.toString());
    assertEquals("300.0 1.0 0.5 ", this.purpleHSL.toString());
  }
}