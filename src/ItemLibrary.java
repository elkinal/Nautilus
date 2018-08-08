import java.awt.image.VolatileImage;

/**
 * Created by alxye on 26-Jul-18.
 */
public class ItemLibrary {
    InventoryItem[] inventory;

    public ItemLibrary(boolean init) { //make a separate array called "craftables to avoid the NullPointerException"
        // TODO: 06-Aug-18 make a separate array called "craftables" to avoid the NullPointerException
        if(init) {
            this.inventory = new InventoryItem[]{ // FIXME: 05-Aug-18 make sure that all items have appropriate ingredients
                    new InventoryItem("Coral", 1, Content.coralStylized),
                    new InventoryItem("Fabricator", 1, Content.coralStylized),
                    new InventoryItem("Flotation Device", 1, Content.waterLight),// new InventoryItem[]{inventory[0], inventory[5]}),
                    new InventoryItem("Medium Plant 1", 1, Content.mediumPlant),
                    new InventoryItem("Medium Plant 2", 1, Content.mediumPlant2),
                    new InventoryItem("Seaweed", 1, Content.seaweed)
            };
        }
    }


    public InventoryItem[] getInventory() {
        return inventory;
    }

    public void setInventory(InventoryItem[] inventory) {
        this.inventory = inventory;
    }
}
