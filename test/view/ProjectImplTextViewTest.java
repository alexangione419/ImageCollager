package view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import model.ImageProject;
import model.ProjectImpl;
import org.junit.Before;
import org.junit.Test;

/**
 * A testing class for {@code ImageProjectView}s, specifically the {@code PPMProjectTextView}
 * implementation.
 */
public class ProjectImplTextViewTest {

  private ImageProjectView view;

  @Before
  public void init() {
    Appendable a = new StringBuilder();
    ImageProject model = new ProjectImpl();
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
}