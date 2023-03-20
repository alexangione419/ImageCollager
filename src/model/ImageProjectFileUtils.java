package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * A utility class for reading and writing to files that {@code ImageProject}s can make.
 */
public final class ImageProjectFileUtils {

  /**
   * Returns whether a file name is valid. A valid file name is a String that is
   * not null, is not just whitespace, and it does NOT contain a ".".
   * @param name the name of the file to check
   * @return whether the given file name is valid.
   * @throws IllegalArgumentException if the file name is null, contains only whitespace, is empty,
   *                                  or it does contain a ".".
   */
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

  /**
   * Creates a new file with the given name.
   * @param fileName the name to give the file
   * @throws IOException if the method is unable to create the file.
   */
  public static void createFile(String fileName) throws IOException {
    try {
      File projectFile = new File(fileName);
      projectFile.createNewFile();
    } catch (IOException io) {
      throw new IOException("File error occurred");
    }
  }

  /**
   * Writes the given content to the file with the given filename.
   * @param fileName the name of the file to write to
   * @param content the text to write to the file
   * @throws IOException if the method is unable to write to the file or if the filename is invalid
   */
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

  /**
   * Returns whether the file at the given file path is a project file. A project file
   * is a file with the file extension ".collage".
   * @param filePath the file path to check
   * @return whether the file at the given file path is a project
   */
  public static boolean isProjectFile(String filePath) {
    if (!filePath.contains(".")) {
      return false;
    }

    int file = filePath.split(Pattern.quote(".")).length;
    //code obtained from https://stackoverflow.com/a/16578721


    return (filePath.split(Pattern.quote("."))[file - 1].equals("collage"));
  }
}
