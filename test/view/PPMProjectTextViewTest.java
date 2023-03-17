package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import model.ImageProject;
import model.PPMProject;
import org.junit.Before;
import org.junit.Test;

public class PPMProjectTextViewTest {

  private ImageProjectView view;
  private ImageProject model;
  private Appendable a;

//  private Readable readable;

  @Before
  public void init() {
    this.a = new StringBuilder();
    this.model = new PPMProject();
    this.view = new PPMProjectTextView(model, a);
  }

  @Test
  public void badConstructors() {
    try {
      this.view = new PPMProjectTextView(null);
      fail("Model is null");
    } catch (IllegalArgumentException e) {
      assertEquals("ImageProject cannot be null.", e.getMessage());
    }

    try {
      this.view = new PPMProjectTextView(null, null);
      fail("Model and Appendable is null");
    } catch (IllegalArgumentException e) {
      assertEquals("ImageProject and/or Appendable cannot be null.", e.getMessage());
    }


  }

  @Test
  public void currentCanvas() {
    this.model.createNewProject(4, 4);
    assertEquals("Layer 1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  ", this.view.currentCanvas());

    assertEquals("Layer 1", this.model.getActiveLayer().getName());

    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer 1", this.model.getActiveLayer().getName());

    assertEquals("0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  \n"
        + "0 0 0 255  0 0 0 255  0 0 0 255  0 0 0 255  ", this.view.currentCanvas());

    assertEquals("Layer 1", this.model.getActiveLayer().getName());

  }

  @Test
  public void testRenderMessage() {

  }

}