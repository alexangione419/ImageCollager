package view;

import static org.junit.Assert.*;

import java.io.IOException;
import model.ImageProject;
import model.PPMProject;
import org.junit.Before;
import org.junit.Test;

public class ImageProjectTextViewTest {

  private ImageProjectView view;
  private ImageProject model;
  private Appendable a;

//  private Readable readable;

  @Before
  public void init() {
    this.a = new StringBuilder();
    this.model = new PPMProject();
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
  public void currentCanvas() {
    this.model.createNewProject(4, 4);
    try {
      assertEquals("0 0 0 0  0 0 0 0  0 0 0 0  0 0 0 0  \n"
          + "0 0 0 0  0 0 0 0  0 0 0 0  0 0 0 0  \n"
          + "0 0 0 0  0 0 0 0  0 0 0 0  0 0 0 0  \n"
          + "0 0 0 0  0 0 0 0  0 0 0 0  0 0 0 0  ", this.view.currentCanvas());
    }
    catch (IOException e) {

    }
  }

  @Test
  public void testRenderMessage() {

  }

}