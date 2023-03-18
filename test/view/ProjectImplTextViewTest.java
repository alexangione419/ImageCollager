package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.ImageProject;
import model.ProjectImpl;
import org.junit.Before;
import org.junit.Test;

public class ProjectImplTextViewTest {

  private ImageProjectView view;
  private ImageProject model;
  private Appendable a;

// private Readable readable;

  @Before
  public void init() {
    this.a = new StringBuilder();
    this.model = new ProjectImpl();
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
  public void testRenderMessage() {

  }

}