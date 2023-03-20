package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * A utility class for reading and writing to files that {@code ImageProject}s can make.
 */
public final class ImageProjectFileUtils {

  public static boolean isFileNameValid(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("File name cannot be null.");
    }

    if (name.isBlank() || name.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be whitespace.");
    }

    if (name.contains(".")) {
      throw new IllegalArgumentException("Name must be valid filename with no file extension.");
    }

    return (name != null) && (!name.isBlank())
        && (!name.isEmpty()) && (!name.contains("."));
  }

  public static void createFile(String fileName) throws IOException {
    try {
      File projectFile = new File(fileName);
      projectFile.createNewFile();
    } catch (IOException io) {
      throw new IOException("File error occurred");
    }
  }

  public static void writeToFile(String fileName, String content) throws IOException {
    FileWriter writer;

    try {
      writer = new FileWriter(fileName);
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }

    try {
      writer.write(content);
    } catch (IOException io) {
      throw new IOException("File writer error encountered");
    }
    writer.close();
  }

  public static boolean isProjectFile(String filePath) {
    if (!filePath.contains(".")) {
      return false;
    }

    //code obtained from https://stackoverflow.com/a/16578721
    return (filePath.split(Pattern.quote("."))[1].equals("collage"));
  }
}
