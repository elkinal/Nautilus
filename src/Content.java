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

public class Content extends JPanel implements ActionListener, KeyListener {
    private static Timer t;
    public static int tileSize = 70;
    private Random random = new Random();
    public static Player player = new Player(0,0,50, 0f, 0f, false, 100f, 100, 100, false, 0, 0);
    private static float filterOpacity = 0.3f;
    public static Filter filter = new Filter(new Color(0.0f, 0.0f, 1.0f, filterOpacity));

    public static Level currentLevel = Levels.levelOne;
    private static float gravity = 0.1f;

    private DisplayController displayController = DisplayController.MENU;
    private byte selectedMenuOption = 0;
    private int scannerRadius = 1;
    //experimental - "files"

    public ItemLibrary itemLibrary = new ItemLibrary(false); // TODO: 08-Aug-18 Tidy This Up

    public static File coralFile;
    public static File seaweedFile;
    public static File mediumPlantFile;
    public static File mediumPlant2File;
    public static File sandStylizedFile;
    public static File flotationDeviceFile;

    public static BufferedImage seaweed;
    public static BufferedImage sandMedium;
    public static BufferedImage sandStylized;
    public static BufferedImage coralStylized;
    public static BufferedImage waterLight;
    public static BufferedImage mediumPlant;
    public static BufferedImage mediumPlant2;
    public static BufferedImage flotationDevice;
    public static BufferedImage cavePlant1;
    public static BufferedImage cavePlant2;

    public static BufferedImage menuOption1;
    public static BufferedImage menuOption2;
    public static BufferedImage menuOption3;
    public static BufferedImage menuOption4;
    public static BufferedImage menuWallpaper;
    public static BufferedImage inventoryIndicator;
    public static BufferedImage inventoryBackground;
    public static BufferedImage menuTitle;
    public static BufferedImage menuSelector;

    private int timerValue = 0;

    private Font font = new Font("Sans-Serif", Font.PLAIN, 40);
    private Font fontSmall = new Font("Roboto", Font.PLAIN, 30); //sort out the fonts and make them compatible with most computers
    private Font fontLarge = new Font("Sans-Serif", Font.PLAIN, 100);

    private static int renderDistanceX = 15;
    private static int renderDistanceY = 9;

    private static int XGridSize = (int) (tileSize * currentLevel.getContent()[0].length);
    private static int YGridSize = (int) (tileSize * currentLevel.getContent().length);
//    public static int YOffset = (int) ((Main.HEIGHT + 8 * tileSize) / 2 - YGridSize + tileSize);
    public static int YOffset = Main.HEIGHT-tileSize*9;
//    public static int YOffset = Main.HEIGHT;
    public static int XOffset = Main.WIDTH / 2 - XGridSize / 2;

    public static int XPlayerOffset = Main.WIDTH / 2 - player.getSize() / 2;
    public static int YPlayerOffset = Main.HEIGHT / 2 - player.getSize() / 2;
    private static int depth = YOffset;

    //FPS control
    private long current;
    private long last;

    public Content() {
        t = new Timer(7, this); // FIXME: 13-Aug-18 ISSUE WITH INCONSISTENT FPS
        t.start();
        super.setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(this);
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
            flotationDeviceFile = new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\water_light.jpg");


            seaweed = ImageIO.read(seaweedFile);
            sandStylized = ImageIO.read(sandStylizedFile);
            coralStylized = ImageIO.read(coralFile);
            mediumPlant = ImageIO.read(mediumPlantFile);
            mediumPlant2 = ImageIO.read(mediumPlant2File);
            flotationDevice = ImageIO.read(flotationDeviceFile);

            menuOption1 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_option_1.png"));
            menuOption2 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_option_2.png"));
            menuOption3 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_option_3.png"));
            menuOption4 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_option_4.png"));
            menuWallpaper = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_wallpaper.png"));
            inventoryIndicator = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\selector.png"));
            inventoryBackground = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\inventory_background.png"));
            menuTitle = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_title.png"));
            menuSelector= ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\menu_selector.png"));
            cavePlant1 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\cave_plant_1.png"));
            cavePlant2 = ImageIO.read(new File("C:\\Users\\alxye\\Desktop\\NAUTILUS\\cave_plant_2.png"));


            itemLibrary = new ItemLibrary(true);
            itemLibrary.addIngredients();


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
        // TODO: 11-Aug-18 Attempting to generate caves
        /**
         * The general idea of this is that a certain (random) tile will have a chance
         * to turn into a "cave water" tile.
         *
         * This tile then has a chance to spread downwards, and sideways, creating a hollow
         * passageway that will create a cave. Certain species of plants should be specific
         * to the cave environment*/

        for (int i = 0; i < currentLevel.getContent().length - 1; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length - 1; j++) {
                if(currentLevel.getContent()[i+1][j] == 1 && currentLevel.getContent()[i-1][j] == 0 && randInt(0,100) == 0) {
                    currentLevel.getContent()[i+1][j] = 7;
                }
            }
        }
        for (int i = 2; i < currentLevel.getContent().length - 2; i++) {
            for (int j = 2; j < currentLevel.getContent()[i].length - 2; j++) {
                if(currentLevel.getContent()[i+1][j] == 7 && i < currentLevel.getContent().length-10) {
                    if(randInt(0,1) == 1) {
                        currentLevel.getContent()[i + 2][j] = 7;
                        currentLevel.getContent()[i + 2][j + 1] = 7;
                        currentLevel.getContent()[i + 2][j - 1] = 7;
                    }


                }
            }
        }
        //adding plants
        for (int i = currentLevel.getContent().length-1; i >=0; i--) {
            for (int j = 0; j < currentLevel.getContent()[i].length-1; j++) {
                if(currentLevel.getContent()[i][j] == 1 && currentLevel.getContent()[i-1][j] != 7) {
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
        //creating cave mushrooms
        for (int i = 0; i < currentLevel.getContent().length - 1; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length - 1; j++) {
                if(currentLevel.getContent()[i][j] == 7 && randInt(0, 8) == 0 && currentLevel.getContent()[i-1][j] == 1) {
                    if(randInt(0,5) < 4)
                        currentLevel.getContent()[i][j] = 6;
                    else
                        currentLevel.getContent()[i][j] = 8;
                }
            }
        }




    }
    private int randInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private void drawTiles(Graphics2D graphics) {
        for (int i = 0; i < currentLevel.getContent().length; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                if (j + renderDistanceX > Math.abs(getCurrentLocationX()) && j - renderDistanceX < Math.abs(getCurrentLocationX()) &&
                        i + renderDistanceY > Math.abs(getCurrentLocationY()) && i - renderDistanceY < Math.abs(getCurrentLocationY())) {
                    /*if(currentLevel.getContent()[i][j] == -1)
                         g.drawImage(waterLight, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);*/
                    if (currentLevel.getContent()[i][j] == -1)
                        graphics.fillRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);
                    if (currentLevel.getContent()[i][j] == 1)
                        graphics.drawImage(sandStylized, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 2)
                        graphics.drawImage(seaweed, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 3)
                        graphics.drawImage(coralStylized, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 4)
                        graphics.drawImage(mediumPlant, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 5)
                        graphics.drawImage(mediumPlant2, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 6)
                        graphics.drawImage(cavePlant1, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    if (currentLevel.getContent()[i][j] == 8)
                        graphics.drawImage(cavePlant2, j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize, null);
                    /*if (currentLevel.getContent()[i][j] == 7)
                        graphics.drawRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);*/

                }
            }

        }
    }
    private void drawScanner(Graphics2D graphics) {
        graphics.setColor(new Color(0.3f, 1.0f, 0.3f, 0.8f));
        for (int i = 0; i < currentLevel.getContent().length; i++) {
            for (int j = 0; j < currentLevel.getContent()[i].length; j++) {
                if (j + scannerRadius > Math.abs(getCurrentLocationX()) && j - scannerRadius < Math.abs(getCurrentLocationX()) &&
                        i + scannerRadius > Math.abs(getCurrentLocationY()) && i - scannerRadius < Math.abs(getCurrentLocationY())) {
                    if (currentLevel.getContent()[i][j] == 1)
                        graphics.fillRect(j * tileSize + XOffset, i * tileSize + YOffset, tileSize, tileSize);

                } //(x^2 + y^2) = radius^2
            }

        }

    }


    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        super.paintComponent(graphics);
        /*graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Set anti-alias for text
        */
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    if(displayController == DisplayController.GAME) {
        drawTiles(graphics);
        drawIndicators(graphics);


        //drawing various markers
        graphics.setColor(Color.green);
        player.draw(graphics);
        if (player.isTorchStatus()) { // FIXME: 29-Jul-18 - HURRY UP
            drawScanner(graphics);
            if(timerValue % 10 == 0)
                scannerRadius++;
            if(scannerRadius > 9)
                scannerRadius = 0;
        }

        if (player.getShowInventory()) {
            player.drawInventory(graphics);
        }
    }
    //control the view of the inventory // TODO: 06-Aug-18 make this look nice | add dialogs and options
    //menu here
        if(displayController == DisplayController.MENU) {
            drawMenu(graphics);
            graphics.setColor(new Color(33, 0, 127));
            graphics.drawRect(50, Main.HEIGHT - (500-selectedMenuOption*110), 500, 100);
            graphics.drawImage(menuSelector, 550, Main.HEIGHT - (500-selectedMenuOption*110), 30, 100, null);
        }
        if(displayController == DisplayController.CREDITS) {
            drawCredits(graphics);
        }

//        graphics.dispose(); //optional?
    }

    private void drawCredits(Graphics2D graphics) {
        graphics.setFont(fontLarge);
        graphics.drawString("The Credits will go here...", 100, 100);
    }

    private void drawMenu(Graphics2D graphics) {
        graphics.drawImage(menuWallpaper, 0, 0, Main.WIDTH, Main.HEIGHT, null);
        graphics.drawImage(menuTitle, Main.WIDTH/2 - 250, Main.HEIGHT/2 - 400, null);
        graphics.drawImage(menuOption1, 50, Main.HEIGHT - 500, 500, 100, null);
        graphics.drawImage(menuOption2, 50, Main.HEIGHT - 390, 500, 100, null);
        graphics.drawImage(menuOption3, 50, Main.HEIGHT - 280, 500, 100, null);
        graphics.drawImage(menuOption4, 50, Main.HEIGHT - 170, 500, 100, null);
    }

    private void drawIndicators(Graphics2D graphics) {
        graphics.setFont(fontSmall); //need to fix this
        if (depth < -200)
            graphics.setColor(Color.white);
        else
            graphics.setColor(Color.black);
        graphics.drawString("Depth: " + depth, Main.WIDTH - 200, 50);
        graphics.drawString("Oxygen: " + player.getOxygen() + "/" + player.getMaxOxygen(), Main.WIDTH - 250, 100);
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
        // TODO: 13-Aug-18 SHOW THE PLAYER'S CURRENT FPS
/*
        last = current;
        current = System.currentTimeMillis();

        System.out.println(1000/(current - last));*/




//        System.out.println(player.getSelectedItems().getItems().size());
        //Player collision detection ___________________________________________________________________________
        //Collision detection system the same as in older project - needs more testing as there may be some issues...
        //The player morphs into tiles if he is travelling at a very high speed. Fix this // 
        // FIXME: 14-Aug-18 Collision SYstem is broken
        if(displayController == DisplayController.GAME) {
            if (getTileType((XOffset - XPlayerOffset) / tileSize, (YOffset - YPlayerOffset - player.getSize()) / tileSize) == 1 ||
                    getTileType((XOffset - XPlayerOffset - player.getSize()) / tileSize, (YOffset - YPlayerOffset - player.getSize()) / tileSize) == 1) {
                player.setVelY(0);
                YOffset += 1;

            } else if (getTileType((XOffset - XPlayerOffset) / tileSize, (YOffset - YPlayerOffset) / tileSize) == 1 ||
                    getTileType((XOffset - XPlayerOffset - player.getSize()) / tileSize, (YOffset - YPlayerOffset) / tileSize) == 1) {
                player.setVelY(0);
                YOffset -= 1;
            }
            if (getTileType((XOffset - XPlayerOffset) / tileSize, (YOffset - YPlayerOffset) / tileSize) == 1 ||
                    getTileType((XOffset - XPlayerOffset) / tileSize, (YOffset - YPlayerOffset - player.getSize()) / tileSize) == 1) {
                player.setVelX(0);
                XOffset -= 1;

            } else if (getTileType((XOffset - XPlayerOffset - player.getSize()) / tileSize, (YOffset - YPlayerOffset) / tileSize) == 1 ||
                    getTileType((XOffset - XPlayerOffset - player.getSize()) / tileSize, (YOffset - YPlayerOffset - player.getSize()) / tileSize) == 1) {
                player.setVelX(0);
                XOffset += 1;
            }


            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 2) {
                player.getInventory().addItems(itemLibrary.getInventory()[5]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
            }
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 3) {
                player.getInventory().addItems(itemLibrary.getInventory()[0]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
            }
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 4) {
                player.getInventory().addItems(itemLibrary.getInventory()[3]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
            }
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 5) {
                player.getInventory().addItems(itemLibrary.getInventory()[4]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 0;
            }
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 6) {
                player.getInventory().addItems(itemLibrary.getInventory()[6]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 7;
            }
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == 8) {
                player.getInventory().addItems(itemLibrary.getInventory()[7]);
                currentLevel.getContent()[Math.abs(getCurrentLocationY())][Math.abs(getCurrentLocationX())] = 7;
            }
            //refilling oxygen if the player is in the presence of air
            if (getTileType(getCurrentLocationX(), getCurrentLocationY()) == -1 && player.getOxygen() != player.getMaxOxygen()) {
                player.setOxygen(player.getOxygen() + 1);
            }

            //taking the physics into account - and calculating the player's stats

            if(depth >= 0) {
                player.incrementVelY(-gravity);
            }





            /*if(depth > 0)
                player.setVelY(-10);*/
            // TODO: 11-Aug-18 Make sure that gravity affects the player if he is above the surface of the water
            XOffset += player.getVelX();
            YOffset += player.getVelY();

            timerOperations();
        }
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
        if(displayController == DisplayController.GAME) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                player.setVelY(player.getVelY() - 1);
                if (player.getVelY() > 0)
                    player.setVelY(0);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                player.setVelY(player.getVelY() + 1);
                if (player.getVelY() < 0)
                    player.setVelY(0);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.setVelX(player.getVelX() - 1);
                if (player.getVelX() > 0)
                    player.setVelX(0);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                player.setVelX(player.getVelX() + 1);
                if (player.getVelX() < 0)
                    player.setVelX(0);
            }
            if (e.getKeyCode() == KeyEvent.VK_T) {
                scannerRadius = 0;
                player.setTorchStatus(!player.isTorchStatus());
            }
            if (e.getKeyCode() == KeyEvent.VK_I) {
                player.toggleInventory();
            }

            if (e.getKeyCode() == KeyEvent.VK_W && player.getSelectedItemY() > 0)
                player.setSelectedItemY(player.getSelectedItemY() - 1);
            if (e.getKeyCode() == KeyEvent.VK_A && player.getSelectedItemX() > 0)
                player.setSelectedItemX(player.getSelectedItemX() - 1);
            if (e.getKeyCode() == KeyEvent.VK_S && player.getSelectedItemY() < 7)
                player.setSelectedItemY(player.getSelectedItemY() + 1);
            if (e.getKeyCode() == KeyEvent.VK_D && player.getSelectedItemX() < 4)
                player.setSelectedItemX(player.getSelectedItemX() + 1);
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {

//            System.out.println(player.getSelectedItemX() + 1 + " " + (player.getSelectedItemY() + 1));
//            System.out.println("Tile Number" + (player.getSelectedItemY() * 5 + player.getSelectedItemX()));
//            System.out.println(player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getName());
//            int currentSelectedItem =
                if (player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getAmount() > 0) {
                    player.getSelectedItems().addItems(new InventoryItem(player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getName()
                            ,
                            1
                            ,
                            player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).getImageFile()
                    ));
                    player.getInventory().getItems().get(player.getSelectedItemY() * 5 + player.getSelectedItemX()).increaseAmount(-1);
                }
                for (int i = 0; i < player.getInventory().getItems().size(); i++) {
                    if(player.getInventory().getItems().get(i).amount == 0) {
                        player.getInventory().getItems().remove(i);
                    }
                }
            /*for (int i = 0; i < player.getSelectedItems().getItems().size(); i++) {
                System.out.println(player.getSelectedItems().getItems().get(i).getName());
            }*/
                // FIXME: 05-Aug-18 Make sure that the item that has an "amount < 1" is removed
                // FIXME: 05-Aug-18 Make sure that "player.selectedItems is compared to player.getItem.ingredients to check if an object can be crafted"
            }
            if (e.getKeyCode() == KeyEvent.VK_C) {
            /*System.out.println("SELECTED ITEMS");
            for (int i = 0; i < player.getSelectedItems().getItems().size(); i++) {
                System.out.println(player.getSelectedItems().getItems().get(i));
            }
            System.out.println("POSSIBLE CRAFTEABLES");
            for (int i = 0; i < player.getSelectedItems().getItems().size(); i++) {
                if(Arrays.asList(itemLibrary.getInventory()[i]) == player.getSelectedItems().getItems().get(i)) {
                    System.out.println(player.getSelectedItems().getItems().get(i).getName());
                }
            }*/

            /*InventoryItem[] playerSelectedInventory = player.getSelectedItems().getItems().toArray(new InventoryItem[player.getSelectedItems().getItems().size()]);
            for (int i = 0; i < itemLibrary.getInventory().length; i++) {
                for (int j = 0; j < itemLibrary.getInventory()[i].getIngredients().length; j++) {

                }
            }*/
            /*int i = 0;
            while(itemLibrary.getInventory()[i] == null) {
                i++;
            }*/
                //potential algorithm

            /*ArrayList<InventoryItem> crafteables = new ArrayList<>();

            for (int i = 0; i < itemLibrary.getInventory().length; i++) {
                if(itemLibrary.getInventory()[i].getIngredients() != null) {
//                    System.out.println(itemLibrary.getInventory()[i].getName());
                    crafteables.add(itemLibrary.getInventory()[i]);
                }
            }*/
//            ArrayList<InventoryItem> selected = player.getSelectedItems().getItems();

            /*for (int i = 0; i < crafteables.size(); i++) {
                if(player.getSelectedItems().getItems().containsAll(Arrays.asList(crafteables.get(i).getIngredients())))
                    System.out.println("Great Success");
            }*/
            /*if(player.getSelectedItems().getItems().containsAll(Arrays.asList(itemLibrary.getInventory()[2].getIngredients()))) {
                System.out.println("success");
            }*/
            player.getInventory().getItems().remove(0);
/*                System.out.println("Selected Items: " + player.getSelectedItems().getItems().toString());
                System.out.println("Required Items: " + Arrays.asList(itemLibrary.getInventory()[2].getIngredients()).toString());
                System.out.println("\n");
                //for some reason the required items amount is always zero 000
                //// FIXME: 09-Aug-18 the actual amount of items in the itemlibrary is decreasing - "increaseAmount" is to blame

                System.out.println("The Inventory Contents:");
                for (int i = 0; i < itemLibrary.getInventory().length; i++) {
                    System.out.println(itemLibrary.getInventory()[i].toString());
                } // TODO: 09-Aug-18 make AMOUNT FIELD OPTIONAL

//            System.out.println("Crafting"); // FIXME: 08-Aug-18 CRAFTING SYSTEM*/
            }
        }
        if(displayController == DisplayController.MENU) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(selectedMenuOption == 0)
                    displayController = DisplayController.GAME;
                if(selectedMenuOption == 1)
                    System.out.println("New Game");
                if(selectedMenuOption == 2)
                    displayController = DisplayController.OPTIONS;
                if(selectedMenuOption == 3)
                    displayController = DisplayController.CREDITS;

            }
            if(e.getKeyCode() == KeyEvent.VK_DOWN && selectedMenuOption < 3 || e.getKeyCode() == KeyEvent.VK_S && selectedMenuOption < 3)
                selectedMenuOption++;
            if(e.getKeyCode() == KeyEvent.VK_UP && selectedMenuOption > 0 || e.getKeyCode() == KeyEvent.VK_W && selectedMenuOption > 0)
                selectedMenuOption--;
        }
        else {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                displayController = DisplayController.MENU;
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}