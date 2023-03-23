package view;

import javax.swing.*;

public class GUITestMain {
  public static void main(String[] args) {
    ImageProcessorGUIView.setDefaultLookAndFeelDecorated(false);
    ImageProcessorGUIView frame = new ImageProcessorGUIView();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);


    try {
      // Set cross-platform Java L&F (also called "Metal")
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

    } catch (UnsupportedLookAndFeelException e) {
      // handle exception
    } catch (ClassNotFoundException e) {
      // handle exception
    } catch (InstantiationException e) {
      // handle exception
    } catch (IllegalAccessException e) {
      // handle exception
    } catch (Exception e) {
    }
  }
}
