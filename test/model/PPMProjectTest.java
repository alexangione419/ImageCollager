package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class PPMProjectTest {

  ImageProject project;

  @Before
  public void init() {
    this.project = new PPMProject();
  }

  @Test
  public void badCreateNewProject() {
    try {
      this.project.createNewProject(0, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(-3, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(-3, -2);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(30, -20);
    } catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }
  }

  @Test
  public void badLoadProject() {
    try {
      this.project.loadProject(null);
      fail("Null passed as an argument");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void badSaveProject() {
    try {
      this.project.saveProject(null, null);
      fail("Null passed as an argument");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void badSaveImagePPM() {
    try {
      this.project.saveImagePPM(null, null);
      fail("Null passed as an argument");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Filepath cannot be null.", e.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void validGetMaxPixelValue() {
    this.project.createNewProject(32, 32);
    assertEquals(255, this.project.getMaxPixelValue());

    this.project.createNewProject(302, 2);
    assertEquals(255, this.project.getMaxPixelValue());
  }

  @Test
  public void badGetMaxPixelValue() {
    try {
      this.project.getMaxPixelValue();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void badGetWidth() {
    try {
      this.project.getWidth();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validGetWidth() {
    this.project.createNewProject(32, 32);
    assertEquals(32, this.project.getWidth());

    this.project.createNewProject(320, 32);
    assertEquals(320, this.project.getWidth());
  }

  @Test
  public void validGetHeight() {
    this.project.createNewProject(32, 64);
    assertEquals(64, this.project.getHeight());

    this.project.createNewProject(100, 100);
    assertEquals(100, this.project.getHeight());
  }

  @Test
  public void badGetHeight() {
    try {
      this.project.getHeight();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validGetLayerNumber() {
    this.project.createNewProject(32, 32);
    for (int i = 1; i <= 100; i++) {
      this.project.addLayer("Test Layer " + i);
    }
    assertEquals(101, this.project.getNumberOfLayers());
  }

  @Test
  public void badLayerNumber() {
    try {
      this.project.getNumberOfLayers();
      fail("Tried to access the width with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.", e.getMessage());
    }
  }

  @Test
  public void validAddLayer() {
    this.project.createNewProject(32, 32);
    this.project.addLayer("Test Layer");
    assertEquals(2, this.project.getNumberOfLayers());

    for (int i = 0; i < 32; i++) {
      this.project.addLayer("Test Layer " + i);
      assertEquals(3 + i, this.project.getNumberOfLayers());
    }

    assertEquals(34, this.project.getNumberOfLayers());
  }

  @Test
  public void badAddLayer() {
    try {
      this.project.addLayer("Layer 2");
      fail("Tried to add a layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("");
      fail("Tried to create a Layer with an empty string as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("     ");
      fail("Tried to create a Layer with just whitespace as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("\n");
      fail("Tried to create a Layer with just a newline as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer(System.lineSeparator());
      fail("Tried to create a Layer with a lineSeparator as the name");
    } catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void validRemoveLayer() {
    this.project.createNewProject(32, 32);

    for (int i = 0; i < 32; i++) {
      this.project.addLayer("Test Layer " + i);
      assertEquals(2 + i, this.project.getNumberOfLayers());
      assertEquals("Test Layer " + i, this.project.getActiveLayer().getName());
    }

    assertEquals(33, this.project.getNumberOfLayers());
    assertEquals("Test Layer 31", this.project.getActiveLayer().getName());
    this.project.setActiveLayer("Test Layer 3");

    this.project.removeLayer("Test Layer 3");
    this.project.removeLayer("Test Layer 7");
    this.project.removeLayer("Test Layer 17");

    assertEquals(30, this.project.getNumberOfLayers());

    assertEquals("Test Layer 2", this.project.getActiveLayer().getName());
    this.project.removeLayer("Test Layer 2");
    assertEquals("Test Layer 1", this.project.getActiveLayer().getName());
    this.project.removeLayer("Test Layer 1");
    this.project.removeLayer("Layer 1");
    assertEquals("Test Layer 0", this.project.getActiveLayer().getName());
    this.project.removeLayer("Test Layer 0");
    assertEquals("Test Layer 4", this.project.getActiveLayer().getName());

  }

  @Test
  public void badRemoveLayer() {
    try {
      this.project.removeLayer("Layer 1");
      fail("Tried to remove a layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.removeLayer("Layer 1");
      fail("Tried to remove the only layer off this project");
    } catch (IllegalStateException e) {
      assertEquals("There is only 1 layer. Operation cannot be done.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("Layer 2");
      this.project.removeLayer("Layer ");
      fail("Tried to remove a layer that doesn't exist");
    } catch (IllegalArgumentException e) {
      assertEquals("Tried to remove layer \"Layer \" but that layer doesn't exist "
              + "in this project.",
          e.getMessage());
    }

    try {
      this.project.createNewProject(32, 32);
      this.project.removeLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void validGetActiveLayer() {
    this.project.createNewProject(32, 32);
    assertEquals("Layer 1", this.project.getActiveLayer().getName());
  }

  @Test
  public void badGetActiveLayer() {
    try {
      this.project.getActiveLayer();
      fail("Tried to get the active layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }
  }

  @Test
  public void validSetActiveLayer() {
    this.project.createNewProject(32, 32);
    assertEquals("Layer 1", this.project.getActiveLayer().getName());
    this.project.addLayer("Layer 2");
    this.project.addLayer("Layer 3");
    this.project.addLayer("Layer 4");
    assertEquals("Layer 4", this.project.getActiveLayer().getName());

    this.project.setActiveLayer("Layer 3");
    assertEquals("Layer 3", this.project.getActiveLayer().getName());
    this.project.setActiveLayer("Layer 2");
    assertEquals("Layer 2", this.project.getActiveLayer().getName());
  }

  @Test
  public void badSetActiveLayer() {
    try {
      this.project.setActiveLayer("Layer 1");
      fail("Tried to get the active layer with no loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    try {
      this.project.createNewProject(32, 32);
      this.project.setActiveLayer(null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name cannot be null.",
          e.getMessage());
    }
  }

  @Test
  public void badSetFilter() {
    try {
      this.project.setFilter("Some Filter", "Layer 1");
      fail("No loaded Project");
    } catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.setFilter(null, null);
      fail("Null was passed as a argument");
    } catch (IllegalArgumentException e) {
      assertEquals("Layer name and/or Filter name cannot be null.",
          e.getMessage());
    }
  }
}