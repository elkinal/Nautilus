import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by alxye on 18-Jul-18.
 */
public class Main {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static int HEIGHT = (int) screenSize.getHeight();
    public static int WIDTH = (int) screenSize.getWidth();
    public static JFrame frame = new JFrame();


    public Main() {
        frame.setTitle("Walrus Game");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new Content());
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        //setting a blank cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        frame.getContentPane().setCursor(blankCursor);

    }
    public static void main(String[] args) {
        new Main();
    }
}
