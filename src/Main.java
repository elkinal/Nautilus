import javax.swing.*;
import java.awt.*;

/**
 * Created by alxye on 18-Jul-18.
 */
public class Main {
    public static int HEIGHT = 800;
    public static int WIDTH = 1000;
    public Main() {
        JFrame frame = new JFrame();
        frame.setTitle("Walrus Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new Content());
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        HEIGHT = (int) screenSize.getHeight();
        WIDTH = (int) screenSize.getWidth();
    }
    public static void main(String[] args) {
        new Main();
    }
}
