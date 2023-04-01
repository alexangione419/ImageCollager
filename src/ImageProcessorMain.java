import controller.ImageProjectGUIController;
import java.io.InputStreamReader;
import model.ImageProject;
import model.ProjectImpl;
import view.ImageProjectGUIViewImpl;

/**
 * The main class of this ImageProcessor. The program starts in this class.
 */
public class ImageProcessorMain {

  /**
   * The main method of the program.
   *
   * @param args arguments to give
   */
  public static void main(String[] args) {
    ImageProject project = new ProjectImpl();

    //ImageProjectView view = new PPMProjectTextView(project, System.out);
    ImageProjectGUIViewImpl view = new ImageProjectGUIViewImpl(project);

    Readable rd = new InputStreamReader(System.in);

    //ImageProjectController controller = new ControllerImpl(project, view, rd);
    ImageProjectGUIController controller = new ImageProjectGUIController(project, rd);
    controller.start(view);

  }
}
