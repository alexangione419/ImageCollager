import controller.ImageProjectController;
import controller.PPMProjectController;
import java.io.InputStreamReader;
import model.ImageProject;
import model.PPMProject;
import view.ImageProjectView;
import view.PPMProjectTextView;

public class ImageProcessorMain {
  public static void main(String[] args) {

    Readable rd = new InputStreamReader(System.in);
    ImageProject project = new PPMProject();
    ImageProjectView view = new PPMProjectTextView(project, System.out);
    ImageProjectController controller = new PPMProjectController(project, view, rd);

    project.createNewProject(4, 4);
    project.getActiveLayer().addImageToLayer("smol.ppm", 0, 0);
    controller.start();

//    project.createNewProject(800, 800);
//    project.getActiveLayer().addImageToLayer("tako.ppm", 0, 0);
  }
}
