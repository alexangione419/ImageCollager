import model.ImageProject;
import model.PPMProject;

public class ImageProcessorMain {
  public static void main(String[] args) {

    ImageProject project = new PPMProject();
    project.createNewProject(800, 800);
    project.getActiveLayer().addImageToLayer("take.ppm", 0, 0);


  }
}
