package controller;

/**
 * Contains all valid file formats accepted by this project.
 */
public enum ImageFileFormats {

  PPM("ppm"),
  PNG("png"),
  JPEG("jpeg"),
  JPG("jpg");

  private final String rep;

  ImageFileFormats(String rep) {
    this.rep = rep;
  }

  /**
   * Returns whether the given extension is supported by our project.
   * @param fileExtension the extension to check
   * @return the boolean result of the check
   */
  public static boolean validFileFormat(String fileExtension) {
    for (ImageFileFormats f : ImageFileFormats.values()) {
      if (fileExtension.equalsIgnoreCase(f.rep)) {
        return true;
      }
    }
    return false;
  }
}
