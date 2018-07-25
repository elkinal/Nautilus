import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
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
    public static Player player = new Player(0,0,50, 0f, 0f, false, 100f, 50, 100, false);
    private static float filterOpacity = 0.3f;
    public static Filter filter = new Filter(new Color(0.0f, 0.0f, 1.0f, filterOpacity));

    public static Level currentLevel = Levels.levelOne;

    private static BufferedImage seaweed;
    private static BufferedImage sandMedium;
    private static BufferedImage sandStylized;
    private static BufferedImage coralStylized;
    private static BufferedImage waterLight;
    private static BufferedImage mediumPlant;
    private static BufferedImage mediumPlant2;

    private Font font = new Font("Sans-Serif", Font.PLAIN, 40);
    private Font fontSmall = new Font("Roboto", Font.PLAIN, 30); //sort out the fonts and make them compatible with most computers
    private Font fontLarge = new Font("Sans-Serif", Font.PLAIN, 100);

    private static int renderDistanceX = 15;
    private static int renderDistanceY = 9;

    private static int XGridSize = (int) (tileSize * currentLevel.getContent()[0].length);
    private static int YGridSize = (int) (tileSize * currentLevel.getContent().length);
//    public static int YOffset = (int) ((Main.HEIGHT + 8 * tileSize) / 2 - YGridSize + tileSize);
//    public static int YOffset = Main.HEIGHT - YGridSize+tileSize*2;
    public static int YOffset = Main.HEIGHT;
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
        fillTerrain();
        randomizeTerrain();
    }

    private void fillTerrain() {
        //fills the empty array with tiles
        for (int i = 0; i < currentLevel.getContent().length; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                currentLevel.getContent()[i][j] = 0;
                if(i == currentLevel.getContent().length-1)
                    currentLevel.getContent()[i][j] = 1;
                if(i == 0)
                    currentLevel.getContent()[i][j] = -1;
            }
        }
        for (int i = 0; i < currentLevel.getContent().length-30; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                if(j <= i*4) {
                    currentLevel.getContent()[i+30][j] = 1;
                }
            }
        }





    }

    private void loadImages() {
        try {
            seaweed = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\seaweed.png"));
            sandMedium = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\sand_medium.jpg"));
            sandStylized = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\sand_tile.png"));
            coralStylized = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\coral.png"));
            waterLight = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\water_light.jpg"));
            mediumPlant = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\medium_plant.png"));
            mediumPlant2 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\medium_plant_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void randomizeTerrain() {

        //create piles of sand
        for (int i = currentLevel.getContent().length-1; i >=0; i--) {
            for (int j = 0; j < currentLevel.getContent()[i].length-1; j++) {
                if(currentLevel.getContent()[i][j] == 1) {
                    if(randInt(0,1) == 0) {
                        currentLevel.getContent()[i - 1][j] = 1;
                        currentLevel.getContent()[i][j+1] = 1;
                    }
                }
            }
        }
        //adding plants
        for (int i = currentLevel.getContent().length-1; i >=0; i--) {
            for (int j = 0; j < currentLevel.getContent()[i].length-1; j++) {
                if(currentLevel.getContent()[i][j] == 1) {
                    if(randInt(0,2) == 2 && currentLevel.getContent()[i-1][j] != 1) {
                        if(randInt(0,1) == 1) {
                            currentLevel.getContent()[i - 1][j] = 2;
                        }
                        else if(randInt(0,2) == 1) {
                            currentLevel.getContent()[i - 1][j] = 3;
                        }

                    }
                    //creating "medium plants"
                    if(randInt(0,5) == 2 && currentLevel.getContent()[i-1][j] != 1 && i > 100) {
                        if(randInt(0,1) == 1)
                            currentLevel.getContent()[i - 1][j] = 4;
                        else if(randInt(0,1) == 1)
                            currentLevel.getContent()[i - 1][j] = 5;

                    }
                }
            }
        }
    }
    private int randInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        for (int i = 0; i < currentLevel.getContent().length; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                if(j+renderDistanceX > Math.abs(getCurrentLocationX()) && j-renderDistanceX < Math.abs(getCurrentLocationX()) &&
                        i+renderDistanceY > Math.abs(getCurrentLocationY()) && i-renderDistanceY < Math.abs(getCurrentLocationY())) {
                    if(currentLevel.getContent()[i][j] == -1)
                         g.drawImage(waterLight, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(currentLevel.getContent()[i][j] == 1)
                        graphics.drawImage(sandStylized, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(currentLevel.getContent()[i][j] == 2)
                        graphics.drawImage(seaweed, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(currentLevel.getContent()[i][j] == 3)
                        graphics.drawImage(coralStylized, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(currentLevel.getContent()[i][j] == 4)
                        graphics.drawImage(mediumPlant, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if(currentLevel.getContent()[i][j] == 5)
                        graphics.drawImage(mediumPlant2, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);

                }
            }
        }
        graphics.setFont(fontSmall); //need to fix this
        if(depth < -200)
            graphics.setColor(Color.white);
        else
            graphics.setColor(Color.black);
        graphics.drawString("Depth: " + depth, Main.WIDTH-200, 50);
        graphics.drawString("Oxygen: " + player.getOxygen() + "/" + player.getMaxOxygen(), Main.WIDTH-250, 100);

        //drawing various markers
        graphics.setColor(Color.green);
        player.draw(graphics);
        graphics.drawRect(player.getCenterX(), player.getCenterY(), 1, 1);
        filter.draw(graphics);




        //control the view of the inventory

        if(player.getShowInventory()) {
            graphics.setColor(new Color(0.3f, 0.5f, 0.7f, 0.8f));
            graphics.fillRect(Main.WIDTH/2-350, Main.HEIGHT/2-350, 700, 700);
            graphics.setColor(Color.green);
            for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                graphics.drawString((player.getInventory().getItems().get(i).getName() + " x" + player.getInventory().getItems().get(i).getAmount()), 650, i*50 + Main.HEIGHT/2-300);
            }
//            System.out.println(player.getCraftableItems().size());
//            for (int i = 0; i < player.getCraftableItems().size(); i++) {
//                graphics.drawString((player.getCraftableItems().get(i).getName() + " x" + player.getCraftableItems().get(i).getAmount()), 650, i*50 + Main.HEIGHT/2-300);
//            }


        }
        /*graphics.drawRect(Content.XPlayerOffset,Content.YPlayerOffset,1,1); //upper left corner
        graphics.drawRect(Content.XPlayerOffset,Content.YPlayerOffset+player.getSize(),1,1); //lower left corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize(),Content.YPlayerOffset,1,1); //upper right corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize(),Content.YPlayerOffset+player.getSize(),1,1); //lower right corner
        graphics.drawRect(Content.XPlayerOffset+player.getSize()/2,Content.YPlayerOffset+player.getSize()/2,1,1); //center*/

        //torch status regulated here
        if(player.isTorchStatus()) {
            System.out.println("The torch is on");
        }
        graphics.dispose();
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
        try {
            return currentLevel.getContent()[Math.abs(y)][Math.abs(x)];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You have gone out of bounds");
        }

        /*catch (ArrayIndexOutOfBoundsException e) {
            gameOver();
            resetPos();
            return 0;
        }*/
        return -1;
    }
    private void update() {
        depth = getCurrentLocationY();
//        filter.setColor(new Color(0.0f, 0.0f,1.0f-((float)Math.abs(YOffset)/20000f), filterOpacity));
//        filter.setColor(new Color(0.0f, 0.0f, 0.0f, filterOpacity - (float)depth/400));
        if((filterOpacity - (float)depth/400) < 0.98f)
            filter.setColor(new Color(0.0f, 0.0f, 0.0f, filterOpacity - (float)depth/400));
        else
            filter.setColor(new Color(0.0f, 0.0f, 0.0f, 0.98f));
//        System.out.println(Math.abs(getCurrentLocationX()) + "x" + Math.abs(getCurrentLocationY()));
        System.out.println(depth);
    }
    private void limitCheck() {
        if(player.getVelX() > player.getMaxSpeed())
            player.setVelX(player.getMaxSpeed());
        if(player.getVelY() > player.getMaxSpeed())
            player.setVelY(player.getMaxSpeed());
        if(player.getVelX() < -player.getMaxSpeed())
            player.setVelX(-player.getMaxSpeed());
        if(player.getVelY() < -player.getMaxSpeed())
            player.setVelY(-player.getMaxSpeed());

        if(player.getOxygen() > player.getMaxOxygen())
            player.setOxygen(player.getMaxOxygen());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println(player.getY());
//        System.out.println(((float)player.getY()/1000f));
//        System.out.println(Math.abs(getCurrentLocationY()) + " " + Math.abs(getCurrentLocationX()));
//        System.out.println(getTileType(getCurrentLocationX(), getCurrentLocationY()));
        XOffset += player.getVelX();
        YOffset += player.getVelY();
        limitCheck();
        update();

        //Player collision detection ___________________________________________________________________________
        //Collision detection system the same as in older project - needs more testing as there may be some issues...
        //need to make sure that the velocity of the player does not increase by such a large amount when a collision is triggered

        if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset-player.getSize())/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize,(YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelY(1);
        }
        else if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize,(YOffset - YPlayerOffset)/tileSize) == 1) {
            player.setVelY(-1);
        }
        if(getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelX(-1);
        }
        else if(getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize, (YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize, (YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelX(1);
        }


        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 2) {
            player.getInventory().addItems(new InventoryItem("Seaweed", 1));
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 3) {
            player.getInventory().addItems(new InventoryItem("Coral", 1));
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 4) {
            player.getInventory().addItems(new InventoryItem("Medium Plant 1", 1));
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 5) {
            player.getInventory().addItems(new InventoryItem("Medium Plant 2", 1));
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        //refilling oxygen if the player is in the presence of air
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == -1 && player.getOxygen() != player.getMaxOxygen()) {
            player.setOxygen(player.getOxygen()+1);
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //motion for the player
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
        if(e.getKeyCode() == KeyEvent.VK_T) {
            player.setTorchStatus(!player.isTorchStatus());
        }
        if(e.getKeyCode() == KeyEvent.VK_I) {
            player.toggleInventory();
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