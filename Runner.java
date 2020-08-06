import java.util.Scanner;
import javax.swing.JFrame;
import java.io.*;
//jar cvfm PixelArtSoftware.jar manifest.txt *.class
public class Runner {
    public static void main(String[] args) {
        Screen sc = new Screen();
        JFrame frame = new JFrame("Pixel Art Software");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(sc);
        frame.pack();
        frame.setVisible(true);
        Boolean done = false;

    }
}
