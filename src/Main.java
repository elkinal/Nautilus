import javax.swing.*;
import java.awt.*;

/**
 * Created by alxye on 18-Jul-18.
 */
public class Main {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static int HEIGHT = (int) screenSize.getHeight();
    public static int WIDTH = (int) screenSize.getWidth();

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


    }
    public static void main(String[] args) {
        new Main();
    }
}
