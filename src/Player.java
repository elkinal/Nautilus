import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by alxye on 17-Jul-18.
 */
public class Player implements Shape, Storage {
    private int x; //center location
    private int y;
    private int size;
    private BufferedImage skin;
    private float velX;
    private float velY;
    private boolean showInventory;
    private ArrayList<InventoryItem> startingInventory = new ArrayList<>();
    private ArrayList<InventoryItem> startingInventory2 = new ArrayList<>();
    private Inventory inventory;
    private Inventory selectedItems;
    private float maxSpeed;
    private int oxygen;
    private int maxOxygen;
    private boolean torchStatus;
    private int selectedItemX;
    private int selectedItemY;

    public Player(int x, int y, int size, float velX, float velY, boolean showInventory, float maxSpeed, int oxygen, int maxOxygen, boolean torchStatus, int selectedItemX, int selectedItemY) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velX = velX;
        this.velY = velY;
        this.showInventory = showInventory;
        this.inventory = new Inventory(startingInventory);
        this.maxSpeed = maxSpeed;
        this.oxygen = oxygen;
        this.maxOxygen = maxOxygen;
        this.torchStatus = torchStatus;
        this.selectedItemX = selectedItemX;
        this.selectedItemY = selectedItemY;
        this.selectedItems = new Inventory(startingInventory2);
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getLowerLeftX() {
        return Content.XPlayerOffset;
    }
    public int getLowerLeftY() {
        return Content.YPlayerOffset+this.size;
    }
    public int getLowerRightX() {
        return Content.XPlayerOffset+this.size;
    }
    public int getLowerRightY() {
        return Content.YPlayerOffset+this.size;
    }
    public int getUpperLeftX() {
        return Content.XPlayerOffset;
    }
    public int getUpperLeftY() {
        return Content.YPlayerOffset;
    }
    public int getUpperRightX() {
        return Content.XPlayerOffset+this.size;
    }
    public int getUpperRightY() {
        return Content.YPlayerOffset;
    }
    public int getCenterX() {
        return Content.XPlayerOffset+this.size/2;
    }
    public int getCenterY() {
        return Content.YPlayerOffset+this.size/2;
    }
    public BufferedImage getSkin() {
        return skin;
    }

    public boolean isShowInventory() {
        return showInventory;
    }

    public void setShowInventory(boolean showInventory) {
        this.showInventory = showInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<InventoryItem> getStartingInventory() {
        return startingInventory;
    }

    public void setStartingInventory(ArrayList<InventoryItem> startingInventory) {
        this.startingInventory = startingInventory;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getOxygen() {
        return oxygen;
    }

    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public int getMaxOxygen() {
        return maxOxygen;
    }

    public void setMaxOxygen(int maxOxygen) {
        this.maxOxygen = maxOxygen;
    }

    public boolean isTorchStatus() {
        return torchStatus;
    }

    public void setTorchStatus(boolean torchStatus) {
        this.torchStatus = torchStatus;
    }

    public int getSelectedItemX() {
        return selectedItemX;
    }

    public void setSelectedItemX(int selectedItemX) {
        this.selectedItemX = selectedItemX;
    }

    public int getSelectedItemY() {
        return selectedItemY;
    }

    public void setSelectedItemY(int selectedItemY) {
        this.selectedItemY = selectedItemY;
    }

    public Inventory getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(Inventory selectedItems) {
        this.selectedItems = selectedItems;
    }

    /*    public ArrayList<InventoryItem> getCraftableItems() {
        ArrayList<InventoryItem> availableItems = new ArrayList<>();
        CraftMenu oxygen = new CraftMenu("Oxygen", 1, new InventoryItem[]{new InventoryItem("Seaweed", 1), new InventoryItem("Coral", 1)});
        boolean craftStatus = true;
        for (int i = 0; i < this.inventory.getItems().size()-1; i++) {
            for (int j = 0; j < oxygen.getRequirements().length-1; j++) {
                if(this.inventory.getItems().get(i) != oxygen.getRequirements()[j])
                    craftStatus = false;
                else
                    craftStatus = true;
            }
        }
        if(craftStatus == true)
            inventory.addItems(new InventoryItem("Oxygen", 1));
        *//*for (int i = 0; i < this.inventory.getItems().size(); i++) {
            if(this.inventory.getItems().get(i).getName().equals("Seaweed") &&
                    this.inventory.getItems().get(i).getName().equals("Coral")) {
                    //this makes sure that the following items are in the player's inventory...
                availableItems.add(new InventoryItem("Food", 1));
            }
        }*//*
        return availableItems;
    }*/

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }


    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void toggleInventory() {
        this.showInventory = !this.showInventory;
    }

    public boolean getShowInventory() {
        return showInventory;
    }

    @Override
    public void draw(Graphics2D gr) {
        gr.fillRect(Content.XPlayerOffset,Content.YPlayerOffset,this.size,this.size);
    }

    @Override
    public void drawInventory(Graphics2D gr) {
        gr.setColor(new Color(0.3f, 0.5f, 0.7f, 0.8f));
        gr.fillRect(Main.WIDTH/2-350, Main.HEIGHT/2-350, 700, 700);
        gr.setColor(Color.green);
        //drawing the inventory grids


        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                gr.drawRect(Main.WIDTH/2-350 + 440 + 70*k, Main.HEIGHT/2-350 + 50 + 70*j, 70, 70);
            }
        }
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 5; k++) {
                gr.drawRect(Main.WIDTH/2-350 + 50 + 70*k, Main.HEIGHT/2-350 + 50 + 70*j, 70, 70);
            }
        }
        //drawing the item currently selected by the user
        gr.drawOval(Main.WIDTH/2-350 + 50 + 70*this.selectedItemX, Main.HEIGHT/2-350 + 50 + 70*this.selectedItemY, 70, 70);


        int control = 0;
        int count = 0;
        for (int i = 0; i < this.inventory.getItems().size(); i++) {
            /*gr.drawString(
                    (this.inventory.getItems().get(i).getImagePath() +
                    " x" +
                    this.inventory.getItems().get(i).getName()), 650, i*50 + Main.HEIGHT/2-300);*/
//                    gr.drawImage(ImageIO.read(new File(this.inventory.getItems().get(i).getImagePath())), Main.WIDTH / 2 - 350 + i * 100, Main.HEIGHT / 2 - 350 + 100 * control, 100, 100, null);

                if(i % 5 == 0) {
                    control++;
                    count = 0;
                }
                else count++;
                gr.drawImage(this.inventory.getItems().get(i).getImageFile(), Main.WIDTH / 2 - 350 + 50 + count * 70, Main.HEIGHT / 2 - 350 - 20 + 70 * control, 70, 70, null);
                gr.drawString(String.valueOf(this.inventory.getItems().get(i).getAmount()), Main.WIDTH / 2 - 350 + 25 + 80 + count * 70, Main.HEIGHT / 2 - 350 + 5 + 70 * control);
        }
        count = 0;
        control = 0;
        for (int i = 0; i < this.selectedItems.getItems().size(); i++) {
            if(i % 3 == 0) {
                control++;
                count = 0;
            }
            else count++;
            gr.drawImage(this.selectedItems.getItems().get(i).getImageFile(), Main.WIDTH/2-350 + count*70 + 10 + 430, Main.HEIGHT/2-350 + 70*control - 20, 70, 70, null);
            gr.drawString(String.valueOf(this.selectedItems.getItems().get(i).getAmount()), Main.WIDTH/2-350 + 440 + 55 + count*70, Main.HEIGHT/2-350 + 5 + 70*control);
        }
    }
}
