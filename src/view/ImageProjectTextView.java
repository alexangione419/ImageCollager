package view;

import java.io.IOException;
import model.ImageProject;

/**
 * A text-based implementation of {@code ImageProjectView}. This class allows anyone to view
 * a {@code ImageProject} model.
 */
public class ImageProjectTextView implements ImageProjectView {

  private ImageProject model;
  private Appendable output;

  /**
   * Constructs a {@code ImageProjectTextView} and sets output to {@code System.out}.
   * @param m the {@code ImageProject} to view
   */
  public ImageProjectTextView(ImageProject m) {
    if (m == null) {
      throw new IllegalArgumentException("ImageProject cannot be null.");
    }

    this.model = m;
    this.output = System.out;
  }

  /**
   * Constructs a new {@code ImageProjectTextView} with a specified {@code Appendable}.
   * @param m the {@code ImageProject} to view
   * @param a the {@code Appendable} to write to
   */
  public ImageProjectTextView(ImageProject m, Appendable a) {
    if ((m == null) || (a == null)) {
      throw new IllegalArgumentException("ImageProject and/or Appendable cannot be null.");
    }

    this.model = m;
    this.output = a;
  }

  @Override
  public void currentCanvas() throws IOException {

  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }


}
