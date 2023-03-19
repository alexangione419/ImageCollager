import controller.ImageProjectController;
import controller.PPMProjectController;
import java.io.InputStreamReader;
import model.ImageProject;
import model.ProjectImpl;
import view.ImageProjectView;
import view.PPMProjectTextView;

/**
 * The main class of this ImageProcessor. The program starts in this class.
 */
public class ImageProcessorMain {

  /**
   * The main method of the program.
   * @param args arguments to give
   */
  public static void main(String[] args) {

    Readable rd = new InputStreamReader(System.in);
    ImageProject project = new ProjectImpl();
    ImageProjectView view = new PPMProjectTextView(project, System.out);
    ImageProjectController controller = new PPMProjectController(project, view, rd);

    controller.start();

  }
}
