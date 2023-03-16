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
  public void testRenderMessage() {

  }

}