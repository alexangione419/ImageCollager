package controller;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing for our ImageFileFormat enum.
 */
public class ImageFileFormatsTest {



  @Test
  public void invalidFileFormat() {
    assertFalse(ImageFileFormats.validFileFormat("gif"));
    assertFalse(ImageFileFormats.validFileFormat("collage"));
    assertFalse(ImageFileFormats.validFileFormat("psd"));
    assertFalse(ImageFileFormats.validFileFormat("gimp"));
    assertFalse(ImageFileFormats.validFileFormat("ase"));
  }

  @Test
  public void validFileFormat() {
    assertTrue(ImageFileFormats.validFileFormat("png"));
    assertTrue(ImageFileFormats.validFileFormat("jpg"));
    assertTrue(ImageFileFormats.validFileFormat("jpeg"));
    assertTrue(ImageFileFormats.validFileFormat("ppm"));
  }

}