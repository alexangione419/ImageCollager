package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
    }
    catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(-3, 2);
    }
    catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(-3, -2);
    }
    catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }

    try {
      this.project.createNewProject(30, -20);
    }
    catch (IllegalArgumentException e) {
      assertEquals("A project's width or height cannot be less than or equal"
          + "to zero.", e.getMessage());
    }
  }

  @Test
  public void badAddLayer() {
    try {
      this.project.addLayer("Layer 2");
      fail("Tried to add a layer with no loaded Project");
    }
    catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("");
      fail("Tried to create a Layer with an empty string as the name");
    }
    catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("     ");
      fail("Tried to create a Layer with just whitespace as the name");
    }
    catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("\n");
      fail("Tried to create a Layer with just a newline as the name");
    }
    catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer(System.lineSeparator());
      fail("Tried to create a Layer with a lineSeparator as the name");
    }
    catch (IllegalArgumentException e) {
      assertEquals("A layer name cannot be an empty string or just whitespace.",
          e.getMessage());
    }
  }

  @Test
  public void badRemoveLayer() {
    try {
      this.project.removeLayer("Layer 1");
      fail("Tried to remove a layer with no loaded Project");
    }
    catch (IllegalStateException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.removeLayer("Layer 1");
      fail("Tried to remove the only layer off this project");
    }
    catch (IllegalStateException e) {
      assertEquals("There is only 1 layer. Operation cannot be done.",
          e.getMessage());
    }

    this.init();

    try {
      this.project.createNewProject(32, 32);
      this.project.addLayer("Layer 2");
      this.project.removeLayer("Layer ");
      fail("Tried to remove a layer that doesn't exist");
    }
    catch (IllegalArgumentException e) {
      assertEquals("Tried to remove layer \"Layer \" but that layer doesn't exist "
              + "in this project.",
          e.getMessage());
    }
  }



  @Test
  public void badSetFilter() {
    try {
      this.project.setFilter("Some Filter", "Layer 1");
      fail("No loaded Project");
    }
    catch (IllegalArgumentException e) {
      assertEquals("There's currently no open project.",
          e.getMessage());
    }
  }
}