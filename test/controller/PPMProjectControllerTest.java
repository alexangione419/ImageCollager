package controller;

import static org.junit.Assert.*;

import java.io.StringReader;
import model.ImageProject;
import model.PPMProject;
import org.junit.Before;
import org.junit.Test;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * A testing class for the {@code PPMProjectController}.
 */
public class PPMProjectControllerTest {

  protected ImageProjectController controller;
  protected ImageProjectView view;
  protected ImageProject g;

  @Test
  public void invalidConstruction() {

    try {
      this.controller = new PPMProjectController(null, null, null);
      fail("Model is null");
    } catch (IllegalArgumentException e) {
      assertEquals("PPMProject for the PPMProjectController cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new PPMProject();
      this.controller = new PPMProjectController(this.g, null, null);
      fail("View is null");
    } catch (IllegalArgumentException e) {
      assertEquals("PPMProjectTextView for the PPMProjectController cannot be null.",
          e.getMessage());
    }

    try {
      this.g = new PPMProject();
      this.view = new PPMProjectTextView(this.g, System.out);
      this.controller = new PPMProjectController(this.g, this.view, null);
      fail("Output is null");
    } catch (IllegalArgumentException e) {
      assertEquals("Readable for the PPMProjectController cannot be null.",
          e.getMessage());
    }
  }

}