package model;

import static org.junit.Assert.*;

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
}