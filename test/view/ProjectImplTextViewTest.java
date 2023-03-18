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
  public void currentCanvas4x4() {
    this.model.createNewProject(4, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

    assertEquals("Layer1", this.model.getActiveLayer().getName());

    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

  }

  @Test
  public void currentCanvas4x2() {
    this.model.createNewProject(4, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 000 ", this.view.currentCanvas());
  }

  @Test
  public void currentCanvas3x4() {
    this.model.createNewProject(3, 4);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 000 000 000 000 ", this.view.currentCanvas());
  }

  @Test
  public void currentCanvas2x2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

    assertEquals("Layer1", this.model.getActiveLayer().getName());

    this.model.addLayer("Layer 2");
    assertEquals("Layer 2", this.model.getActiveLayer().getName());
    this.model.setActiveLayer(0);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

  }

  @Test
  public void currentCanvasWithOpacityBlend() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

    this.model.addLayer("Blue");
    this.model.addLayer("Green");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 0 , 255);
    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 0, 255 , 128);

    assertEquals("127 000 063 255 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());
    this.model.setActiveLayer(2);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 0,200);

    assertEquals("027 043 013 255 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

  }

  @Test
  public void currentCanvasWithOpacityBlend2() {
    this.model.createNewProject(2, 2);
    assertEquals("Layer1", this.model.getActiveLayer().getName());

    assertEquals("000 000 000 000 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

    this.model.addLayer("Purple");
    this.model.setActiveLayer(0);
    this.model.getActiveLayer().setPixelColor(0, 0, 0, 255, 255, 255);
    assertEquals("000 255 255 255 000 000 000 000 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());

    this.model.setActiveLayer(1);
    this.model.getActiveLayer().setPixelColor(1, 0, 255, 0, 255, 128);

    assertEquals("000 255 255 255 127 000 127 128 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());
    assertEquals(2, this.model.getNumberOfLayers());
    this.model.getActiveLayer().setPixelColor(0, 0, 255, 0, 255, 128);

    assertEquals("063 127 190 255 127 000 127 128 \n"
        + "000 000 000 000 000 000 000 000 ", this.view.currentCanvas());
  }

  @Test
  public void testRenderMessage() {

  }

}