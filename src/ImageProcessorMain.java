import controller.ImageProjectController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ImageProjectGUIController;
import model.ImageProject;
import model.ProjectImpl;
import controller.ControllerImpl;
import view.ImageProjectGUIViewImpl;
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

    //ImageProjectView view = new PPMProjectTextView(project, System.out);
    ImageProjectGUIViewImpl view = new ImageProjectGUIViewImpl(project, viewToController);

    //Readable rd = new InputStreamReader(System.in);
    Readable rd = new StringReader(viewToController.toString());

    //ImageProjectController controller = new ControllerImpl(project, view, rd);
    ImageProjectGUIController controller = new ImageProjectGUIController(project, view, rd);
    controller.start();

  }
}
