import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by alxye on 17-Jul-18.
 */
public class Player implements Shape {
    private int x; //center location
    private int y;
    private int size;
    private BufferedImage skin;
    private float velX;
    private float velY;
    private boolean showInventory;
    private ArrayList<InventoryItem> startingInventory = new ArrayList<>();
    private Inventory inventory;
    private float maxSpeed;
    private int oxygen;
    private int maxOxygen;
    private boolean torchStatus;

    public Player(int x, int y, int size, float velX, float velY, boolean showInventory, float maxSpeed, int oxygen, int maxOxygen, boolean torchStatus) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.velX = velX;
        this.velY = velY;
        this.showInventory = showInventory;
        startingInventory.add(new InventoryItem("flotation device", 2)); //this is inefficient - remove later and make a library of InventoryItems
        startingInventory.add(new InventoryItem("fabricator", 1));
        this.inventory = new Inventory(startingInventory);
        this.maxSpeed = maxSpeed;
        this.oxygen = oxygen;
        this.maxOxygen = maxOxygen;
        this.torchStatus = torchStatus;
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

    /*    public ArrayList<InventoryItem> getCraftableItems() {
        ArrayList<InventoryItem> availableItems = new ArrayList<>();
        Craftable oxygen = new Craftable("Oxygen", 1, new InventoryItem[]{new InventoryItem("Seaweed", 1), new InventoryItem("Coral", 1)});
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
}
