package controller;

public enum ImageFileFormats {

  PPM("ppm"),
  PNG("png"),
  JPEG("jpeg"),
  JPG("jpg");

  private final String rep;

  ImageFileFormats(String rep) {
    this.rep = rep;
  }

  public static boolean validFileFormat(String fileExtension) {
    for (ImageFileFormats f : ImageFileFormats.values()) {
      if (fileExtension.equalsIgnoreCase(f.rep)) {
        return true;
      }
    }
    return false;
  }
}
