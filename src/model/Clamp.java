package model;

/**
 * A functional class that takes in three number (int or double) arguments:
 * a lower-bound, upper-bound, and a value. The function will return back a double or int (based
 * on the types of arguments pass).
 * If value is greater than upper-bound, then the function will return upper-bound.
 * If value is lower than lower-bound, then the function will return lower-bound.
 * If value is in between upper-bound and lower-bound, value will get returned back.
 */
public class Clamp {

  public static double execute(double value, double lower, double upper) {
    if (value > upper) {
      return upper;
    } else if (value < lower) {
      return lower;
    } else {
      return value;
    }
  }

  public static int execute(int value, int lower, int upper) {
    if (value > upper) {
      return upper;
    } else if (value < lower) {
      return lower;
    } else {
      return value;
    }
  }
}
