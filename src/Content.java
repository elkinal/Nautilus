import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * Created by alxye on 16-May-18.
 */

public class Content extends JPanel implements ActionListener, KeyListener, MouseListener {
    private static Timer t;
    public static Player player = new Player(400,0,550);
    private static float filterOpacity = 0.5f;
    public static Filter filter = new Filter(new Color(0.2f, 0.5f, 1.0f, filterOpacity));

    public static float tileSize = 25;


    public Content() {
        t = new Timer(5, this);
        t.start();
        super.setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        player.draw(graphics);
        filter.draw(graphics);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(player.getY());
        filter.setColor(new Color(0.2f, 0.5f,1.0f-((float)player.getY()/100000f), filterOpacity));
//        System.out.println(((float)player.getY()/1000f));
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            player.setY(player.getY()+100);
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            player.setY(player.getY()-10);
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            player.setX(player.getX()+10);
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            player.setX(player.getX()-10);

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}