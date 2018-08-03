import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by alxye on 16-May-18.
 */

public class Content extends JPanel implements ActionListener, KeyListener, MouseListener {
    private static Timer t;
    public static int tileSize = 70;
    private Random random = new Random();
    public static Player player = new Player(0,0,50, 0f, 0f, false, 100f, 50, 100, false, 0, 0);
    private static float filterOpacity = 0.3f;
    public static Filter filter = new Filter(new Color(0.0f, 0.0f, 1.0f, filterOpacity));

    public static Level currentLevel = Levels.levelOne;



    //experimental - "files"

    public ItemLibrary itemLibrary = new ItemLibrary(false);

    public static File coralFile;
    public static File seaweedFile;
    public static File mediumPlantFile;
    public static File mediumPlant2File;
    public static File sandStylizedFile;


    public static BufferedImage seaweed;
    public static BufferedImage sandMedium;
    public static BufferedImage sandStylized;
    public static BufferedImage coralStylized;
    public static BufferedImage waterLight;
    public static BufferedImage mediumPlant;
    public static BufferedImage mediumPlant2;

    private int timerValue = 0;

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

        //attempting to create a timer that will decrease the player's oxygen
/*        Runnable runnable = () -> System.out.println("Hello !!");

        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);*/
        //delete this later if you find a better alternatives
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

            coralFile = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\large_coral.png");
            seaweedFile = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\seaweed.png");
            mediumPlantFile = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\cluster_coral.png");
            mediumPlant2File = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\dead_coral.png");
            sandStylizedFile = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\sand_medium.jpg");

            seaweed = ImageIO.read(seaweedFile);
            sandStylized = ImageIO.read(sandStylizedFile);
            coralStylized = ImageIO.read(coralFile);
            mediumPlant = ImageIO.read(mediumPlantFile);
            mediumPlant2 = ImageIO.read(mediumPlant2File);

            itemLibrary = new ItemLibrary(true);


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
                        else
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
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(graphics);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set anti-alias for text
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        for (int i = 0; i < currentLevel.getContent().length; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                if(j+renderDistanceX > Math.abs(getCurrentLocationX()) && j-renderDistanceX < Math.abs(getCurrentLocationX()) &&
                        i+renderDistanceY > Math.abs(getCurrentLocationY()) && i-renderDistanceY < Math.abs(getCurrentLocationY())) {
                    /*if(currentLevel.getContent()[i][j] == -1)
                         g.drawImage(waterLight, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);*/
                    if(currentLevel.getContent()[i][j] == -1)
                         g.fillRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);
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
        if(player.isTorchStatus()) { //the filter makes some game tiles blurry - // FIXME: 29-Jul-18 - HURRY UP
            Point2D center = new Point2D.Float(player.getCenterX(), player.getCenterY());
            float[] distance = {0.0f, 1.0f};
            Color[] colors = {new Color(1.0f, 1.0f, 0.0f, 0.0f), filter.getColor()};
            RadialGradientPaint p = new RadialGradientPaint(center, 300, distance, colors);

            //torch status regulated here
            graphics.setPaint(p);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1)); // set this to .95f
//            graphics.setColor(filter.getColor());
            graphics.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

        }
        else {
            filter.draw(graphics);
        }




        //control the view of the inventory
        if(player.getShowInventory()) {
            player.drawInventory(graphics);
        }

        graphics.dispose(); //optional?
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
//        System.out.println(depth);
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
        if(player.getOxygen() < 0)
            player.setOxygen(0);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        update();
//        System.out.println(player.getSelectedItems().getItems().size());
        //Player collision detection ___________________________________________________________________________
        //Collision detection system the same as in older project - needs more testing as there may be some issues...
        //The player morphs into tiles if he is travelling at a very high speed. Fix this // FIXME: 31-Jul-18

        if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset-player.getSize())/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize,(YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelY(0);
            YOffset += 1;
        }
        else if(getTileType((XOffset - XPlayerOffset)/tileSize,(YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize,(YOffset - YPlayerOffset)/tileSize) == 1) {
            player.setVelY(0);
            YOffset -= 1;
        }
        if(getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset)/tileSize, (YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelX(0);
            XOffset-=1;

        }
        else if(getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize, (YOffset - YPlayerOffset)/tileSize) == 1 ||
                getTileType((XOffset - XPlayerOffset-player.getSize())/tileSize, (YOffset - YPlayerOffset-player.getSize())/tileSize) == 1) {
            player.setVelX(0);
            XOffset+=1;

        }


        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 2) {
            player.getInventory().addItems(itemLibrary.getInventory()[5]);
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 3) {
            player.getInventory().addItems(itemLibrary.getInventory()[0]);
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 4) {
            player.getInventory().addItems(itemLibrary.getInventory()[3]);
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == 5) {
            player.getInventory().addItems(itemLibrary.getInventory()[4]);
            currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
        }
        //refilling oxygen if the player is in the presence of air
        if(getTileType(getCurrentLocationX(), getCurrentLocationY()) == -1 && player.getOxygen() != player.getMaxOxygen()) {
            player.setOxygen(player.getOxygen()+1);
        }
        XOffset += player.getVelX();
        YOffset += player.getVelY();

        timerOperations();
        repaint();
        limitCheck();



    }

    private void timerOperations() {
        //timer activation
        timerValue++;
        if(timerValue % 200 == 0) {
//            System.out.println(timerValue); //make sure that this does not overflow
            if(depth > -100)
                player.setOxygen(player.getOxygen()-1);
            if(depth < -100)
                 player.setOxygen(player.getOxygen()-2);
        }
        if(player.getOxygen() == 0)
            System.out.println("LOl u DIed M8ey");
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

        if(e.getKeyCode() == KeyEvent.VK_W)
            player.setSelectedItemY(player.getSelectedItemY() - 1);
        if(e.getKeyCode() == KeyEvent.VK_A)
            player.setSelectedItemX(player.getSelectedItemX() - 1);
        if(e.getKeyCode() == KeyEvent.VK_S)
            player.setSelectedItemY(player.getSelectedItemY() + 1);
        if(e.getKeyCode() == KeyEvent.VK_D)
            player.setSelectedItemX(player.getSelectedItemX() + 1);
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//            System.out.println(player.getSelectedItemX() + 1 + " " + (player.getSelectedItemY() + 1));
//            System.out.println("Tile Number" + (player.getSelectedItemY() * 5 + player.getSelectedItemX()));
//            System.out.println(player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getName());
//            int currentSelectedItem =
            player.getSelectedItems().addItems(new InventoryItem(player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getName()
                    ,
                    1
                    ,
                    player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getImageFile()
                    ));


            System.out.println("Items in the currently selecting crafting menu");
            System.out.println("There are " + player.getSelectedItems().getItems().size() + " items currently in the inventory");
            for (int i = 0; i < player.getSelectedItems().getItems().size(); i++) {
                System.out.println(player.getSelectedItems().getItems().get(i).getName() + player.getSelectedItems().getItems().get(i).getAmount());
            }

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