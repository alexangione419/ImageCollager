package view;

import java.io.IOException;
import model.ImageProject;
import model.ImageProjectState;

/**
 * A text-based implementation of {@code ImageProjectView}. This class allows anyone to view a
 * {@code ImageProject} model.
 */
public class PPMProjectTextView implements ImageProjectView {

  private ImageProjectState model;
  private Appendable output;

  /**
   * Constructs a {@code PPMProjectTextView} and sets output to {@code System.out}.
   *
   * @param m the {@code ImageProject} to view
   */
  public PPMProjectTextView(ImageProjectState m) {
    if (m == null) {
      throw new IllegalArgumentException("ImageProject cannot be null.");
    }

    this.model = m;
    this.output = System.out;
  }

  /**
   * Constructs a new {@code PPMProjectTextView} with a specified {@code Appendable}.
   *
   * @param m the {@code ImageProject} to view
   * @param a the {@code Appendable} to write to
   */
  public PPMProjectTextView(ImageProjectState m, Appendable a) {
    if ((m == null) || (a == null)) {
      throw new IllegalArgumentException("ImageProject and/or Appendable cannot be null.");
    }

    this.model = m;
    this.output = a;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }


}
