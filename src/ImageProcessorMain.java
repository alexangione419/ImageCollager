import controller.ImageProjectController;
import controller.ImageProjectGUIController;
import controller.PPMProjectController;

import java.io.BufferedReader;

import model.ImageProject;
import model.ProjectImpl;
import view.ImageProjectGUIView;
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

    Appendable viewToController = new StringBuilder();
    //ImageProjectView view = new PPMProjectTextView(project);
    ImageProjectGUIView view = new ImageProjectGUIViewImpl(project, viewToController);

    Readable rd = new BufferedReader(viewToController);
    //ImageProjectView view = new PPMProjectTextView(project, System.out);

    ImageProjectController controller = new ImageProjectGUIController(project, view, rd);

    controller.start();

  }
}
