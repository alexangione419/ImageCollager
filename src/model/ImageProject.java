package model;

import java.io.IOException;

/**
 * An interface for creating image projects that contain {@code Layers}.
 */
public interface ImageProject {

    /**
     * Saves the final image created from all the layers to a specified file path as a .ppm file.
     * @param filePath the file path to save the image
     * @throws IOException if the file path is invalid.
     */
    void saveImagePPM(String filePath) throws IOException;

    /**
     * Saves this project to a specified file path as a .collage file.
     * @param filePath the file path to save the image
     * @throws IOException if the file path is invalid.
     */
    void saveProject(String filePath) throws IOException;

    /**
     * Loads a project at a given file path.
     * @param filePath the file path of the file being loaded
     * @throws IOException if the file path is invalid OR is not a .collage file.
     */
    void loadProject(String filePath) throws IOException;

    /**
     * Creates a new project with the specified width and height.
     * @param width the width of the canvas
     * @param height the height of the canvas
     * @throws IllegalArgumentException if the width or height is less than 0.
     */
    void createNewProject(int width, int height) throws IllegalArgumentException;

    /**
     * Adds a new layer to this project with the specified name.
     * @param name the name to give the layer
     * @throws IllegalArgumentException if the name is an empty String or null
     */
    void addLayer(String name) throws IllegalArgumentException;

    /**
     * Removes the specified layer from this project.
     * @param layer the layer to remove.
     * @throws IllegalArgumentException if the given layer is NOT in the current project
     */
    void removeLayer(Layer layer);

    /**
     * Sets a filter to a specified layer from this project.
     * @param filter the filter to set to the given layer
     * @param layer the layer receiving the given filter
     * @throws IllegalArgumentException if the given layer is NOT in the current project
     */
    void setFilter(Filter filter, Layer layer);
}
