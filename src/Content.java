import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by alxye on 16-May-18.
 */

public class Content extends JPanel implements ActionListener, KeyListener, MouseListener {
    private static Timer t;
    public static int tileSize = 70;
    private Random random = new Random();
    public static Player player = new Player(0,0,50, 0f, 0f);
    private static float filterOpacity = 0.5f;
    public static Filter filter = new Filter(new Color(0.2f, 0.5f, 1.0f, filterOpacity));

    private static BufferedImage seaweed;
    private static BufferedImage sandMedium;
    private static BufferedImage sandStylized;

    private static int renderDistanceX = 13;
    private static int renderDistanceY = 7;

    private static int XGridSize = (int) (tileSize * Levels.level1[0].length);
    private static int YGridSize = (int) (tileSize * Levels.level1.length);
//    public static int YOffset = (int) ((Main.HEIGHT + 8 * tileSize) / 2 - YGridSize + tileSize);
    public static int YOffset = Main.HEIGHT - YGridSize+tileSize*2;
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
        loadImages();
        randomizeTerrain();
    }

    private void loadImages() {
        try {
            seaweed = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\seaweed.png"));
            sandMedium = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\sand_medium.jpg"));
            sandStylized = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\sand_tile.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomizeTerrain() {
        for (int i = Levels.level1.length-1; i >=0; i--) {
            for (int j = 0; j < Levels.level1[i].length-1; j++) {
                if(Levels.level1[i][j] == 1) {
                    if(randInt(0,1) == 0) {
                        Levels.level1[i - 1][j] = 1;
                        Levels.level1[i][j+1] = 1;
                    }
                }
            }
        }
        for (int i = Levels.level1.length-1; i >=0; i--) {
            for (int j = 0; j < Levels.level1[i].length-1; j++) {
                if(Levels.level1[i][j] == 1) {
                    if(randInt(0,2) == 2 && Levels.level1[i-1][j] != 1) {
                        Levels.level1[i - 1][j] = 2;
                    }
                }
            }
        }
    }
    private int randInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < Levels.level1.length; i++) {
            for (int j = 0; j < Levels.level1[i].length; j++) {
                if(j+renderDistanceX > Math.abs(getCurrentLocationX()) && j-renderDistanceX < Math.abs(getCurrentLocationX()) &&
                        i+renderDistanceY > Math.abs(getCurrentLocationY()) && i-renderDistanceY < Math.abs(getCurrentLocationY())) {
                    if(Levels.level1[i][j] == 0)
                         g.drawRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);
                    if(Levels.level1[i][j] == 1)
                        g.drawImage(sandStylized, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(Levels.level1[i][j] == 2)
                        g.drawImage(seaweed, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);

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
        depth = YOffset/20;
        filter.setColor(new Color(0.0f, 0.5f,0.8f-((float)Math.abs(YOffset)/30000f), filterOpacity));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(player.getY());
//        System.out.println(((float)player.getY()/1000f));
//        System.out.println(Math.abs(getCurrentLocationY()) + " " + Math.abs(getCurrentLocationX()));
//        System.out.println(getTileType(getCurrentLocationX(), getCurrentLocationY()));
        XOffset += player.getVelX();
        YOffset += player.getVelY();
        update();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setVelY(player.getVelY() - 1);
            if(player.getVelY() > 0)
                player.setVelY(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.setVelY(player.getVelY() + 1);
            if(player.getVelY() < 0)
                player.setVelY(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setVelX(player.getVelX() - 1);
            if(player.getVelX() > 0)
                player.setVelX(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setVelX(player.getVelX() + 1);
            if(player.getVelX() < 0)
                player.setVelX(0);
        }


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