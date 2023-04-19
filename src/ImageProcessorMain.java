import controller.ControllerImpl;
import controller.ImageProjectController;
import controller.ImageProjectGUIController;
import controller.ImageProjectGUIControllerImpl;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import model.ImageProject;
import model.ProjectImpl;
import view.ImageProjectGUIView;
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
  public static void main(String[] args) throws FileNotFoundException {
    ImageProject project = new ProjectImpl();

    if (args.length > 0) {
      if (args[0].equalsIgnoreCase("-text")) {
        ImageProjectView view = new PPMProjectTextView(project, System.out);
        Readable rd = new InputStreamReader(System.in);
        ImageProjectController controller = new ControllerImpl(project, view, rd);

        controller.start();
      }

      if (args[0].equalsIgnoreCase("-file")) {
        ImageProjectView view = new PPMProjectTextView(project, System.out);
        Readable rd;
        try {
          rd = new InputStreamReader(new FileInputStream(args[1]));
        } catch (IOException io) {
          throw new FileNotFoundException("Unable to open file");
        }
        ImageProjectController controller = new ControllerImpl(project, view, rd);

        controller.start();
      }
    }

    else {
      ImageProjectGUIView view = new ImageProjectGUIViewImpl(project);
      ImageProjectGUIController controller = new ImageProjectGUIControllerImpl(project);

      controller.start(view);
    }
  }
}
