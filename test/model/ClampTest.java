package model;

import static org.junit.Assert.*;

import model.filters.Clamp;
import org.junit.Test;

/**
 * A testing class for the functional {@code Clamp} class.
 */
public class ClampTest {


  @Test
  public void lowerBound() {
    assertEquals(0, Clamp.execute(-1, 0, 100));
    assertEquals(50, Clamp.execute(20, 50, 100));
  }

  @Test
  public void upperBound() {
    assertEquals(100, Clamp.execute(120, 0, 100));
    assertEquals(1000000, Clamp.execute(10000000, 1000, 1000000));
  }

  @Test
  public void value() {
    assertEquals(50, Clamp.execute(50, 0, 100));
    assertEquals(80, Clamp.execute(80, 50, 100));
    assertEquals(64, Clamp.execute(64, 0, 100));
    assertEquals(777777, Clamp.execute(777777, 1000, 1000000));
  }



}