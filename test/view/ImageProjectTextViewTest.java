package view;

import static org.junit.Assert.*;

import model.ImageProject;
import org.junit.Before;
import org.junit.Test;

public class ImageProjectTextViewTest {

  private ImageProjectView view;
  private ImageProject model;
  private Appendable a;

//  private Readable readable;

  @Before
  public void init() {
    this.view = new ImageProjectTextView(model, a);
  }

  @Test
  public void badConstructors() {
    try {
      this.view = new ImageProjectTextView(null);
      fail("Model is null");
    }
    catch (IllegalArgumentException e) {
      assertEquals("ImageProject cannot be null.", e.getMessage());
    }

    try {
      this.view = new ImageProjectTextView(null, null);
      fail("Model and Appendable is null");
    }
    catch (IllegalArgumentException e) {
      assertEquals("ImageProject and/or Appendable cannot be null.", e.getMessage());
    }


  }

  @Test
  public void testRenderMessage() {

  }

}