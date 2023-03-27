import controller.ImageProjectController;
import java.io.InputStreamReader;
import model.ImageProject;
import model.ProjectImpl;
import controller.ControllerImpl;
import view.ImageProjectView;
import view.PPMProjectTextView;

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
    Appendable viewToController = new StringBuilder();
    //ImageProjectGUIView view = new ImageProjectGUIViewImpl(project, viewToController);

    //Readable rd = new BufferedReader(viewToController);
    Readable rd = new InputStreamReader(System.in);

    //ImageProjectController controller = new ImageProjectGUIController(project, view, rd);
    ImageProjectView view = new PPMProjectTextView(project, System.out);

    //ImageProjectController controller = new PPMProjectController(project, view, rd);
    ImageProjectController controller = new ControllerImpl(project, view, rd);
    controller.start();

  }
}
