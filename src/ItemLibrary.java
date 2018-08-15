import java.awt.image.VolatileImage;

/**
 * Created by alxye on 26-Jul-18.
 */
public class ItemLibrary {
    private InventoryItem[] inventory;

    public ItemLibrary(boolean init) { //make a separate array called "craftables to avoid the NullPointerException"
        if(init) {
            this.inventory = new InventoryItem[]{
                    new InventoryItem("Coral", 1, Content.coralStylized),
                    new InventoryItem("Fabricator", 1, Content.coralStylized),
                    new InventoryItem("Flotation Device", 1, Content.waterLight),// new InventoryItem[]{inventory[0], inventory[5]}),
                    new InventoryItem("Medium Plant 1", 1, Content.mediumPlant),
                    new InventoryItem("Medium Plant 2", 1, Content.mediumPlant2),
                    new InventoryItem("Seaweed", 1, Content.seaweed),
                    new InventoryItem("Cave Plant 1", 1, Content.cavePlant1),
                    new InventoryItem("Cave Plant 2", 1, Content.cavePlant2)
            };
        }
    }
    // FIXME: 10-Aug-18 make sure the amount of all the inventoryItems is 0, and test the result

    public InventoryItem[] getInventory() {
        return this.inventory;
    }

    public void setInventory(InventoryItem[] inventory) {
        this.inventory = inventory;
    }

    public void addIngredients() {
        this.inventory[2].setIngredients(new InventoryItem[]{
                this.inventory[0],
                this.inventory[5]
        });
    }
}
