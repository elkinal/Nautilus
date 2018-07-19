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
    public static int tileSize = 70;

    public static Player player = new Player(0,0,50);
    private static float filterOpacity = 0.5f;
    public static Filter filter = new Filter(new Color(0.2f, 0.5f, 1.0f, filterOpacity));

    private static int renderDistanceX = 13;
    private static int renderDistanceY = 7;

    private static int XGridSize = (int) (tileSize * Levels.level1[0].length);
    private static int YGridSize = (int) (tileSize * Levels.level1.length);
    public static int YOffset = (int) ((Main.HEIGHT + 8 * tileSize) / 2 - YGridSize + tileSize);
    public static int XOffset = Main.WIDTH / 2 - XGridSize / 2;

    public static int XPlayerOffset = Main.WIDTH / 2 - player.getSize() / 2;
    public static int YPlayerOffset = Main.HEIGHT / 2 - player.getSize() / 2;
    private static int depth = YOffset;


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
        for (int i = 0; i < Levels.level1.length; i++) {
            for (int j = 0; j < Levels.level1[i].length; j++) {
                if(j+renderDistanceX > Math.abs(getCurrentLocationX()) && j-renderDistanceX < Math.abs(getCurrentLocationX()) &&
                        i+renderDistanceY > Math.abs(getCurrentLocationY()) && i-renderDistanceY < Math.abs(getCurrentLocationY())) {
                    g.drawRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);
                }
            }
        }
        Graphics2D graphics = (Graphics2D) g;
        player.draw(graphics);
        filter.draw(graphics);
        graphics.setColor(Color.black);
        graphics.drawString("Depth: " + depth, Main.WIDTH-70, 20);

        //drawing various markers
        graphics.setColor(Color.green);
        graphics.drawRect(player.getCenterX(), player.getCenterY(), 1, 1);
        /*graphics.drawRect(Content.XPlayerOffset,Content.YPlayerOffset,1,1); //upper left corner
        graphics.drawRect(Content.XPlayerOffset,Content.YPlayerOffset+player.getSize(),1,1); //lower left corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize(),Content.YPlayerOffset,1,1); //upper right corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize(),Content.YPlayerOffset+player.getSize(),1,1); //lower right corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize()/2,Content.YPlayerOffset+player.getSize()/2,1,1); //center*/
    }
    public static int getCurrentLocationY() {
        //YOffset - YPlayerOffset - literal cartesian displacement
        return (YOffset - YPlayerOffset-player.getSize()/2)/tileSize; //Y
    }
    public static int getCurrentLocationX() {
        //XOffset - XPlayerOffset - literal cartesian displacement
        return (XOffset - XPlayerOffset-player.getSize()/2)/tileSize; //X
    }
    public static int getTileType(int x, int y) {
        return Levels.level1[Math.abs(y)][Math.abs(x)];

        /*catch (ArrayIndexOutOfBoundsException e) {
            gameOver();
            resetPos();
            return 0;
        }*/
    }
    private void update() {
        depth = YOffset;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(player.getY());
        filter.setColor(new Color(0.0f, 0.0f,0.8f-((float)YOffset/10000f), filterOpacity));
//        System.out.println(((float)player.getY()/1000f));
        System.out.println(Math.abs(getCurrentLocationY()) + " " + Math.abs(getCurrentLocationX()));
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            YOffset-=10;
        else if(e.getKeyCode() == KeyEvent.VK_UP)
            YOffset+=10;
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            XOffset-=10;
        else if(e.getKeyCode() == KeyEvent.VK_LEFT)
            XOffset+=10;


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