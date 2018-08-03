import java.awt.image.VolatileImage;

/**
 * Created by alxye on 26-Jul-18.
 */
public class ItemLibrary {
    InventoryItem[] inventory;

    public ItemLibrary(boolean init) {
        if(init) {
            this.inventory = new InventoryItem[]{
                    new InventoryItem("Coral", 1, Content.coralStylized),
                    new InventoryItem("Fabricator", 1, Content.coralStylized),
                    new InventoryItem("Flotation Device", 1, Content.waterLight),
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
