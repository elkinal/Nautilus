/**
 * Created by alxye on 26-Jul-18.
 */
public class ItemLibrary {
    InventoryItem[] inventory;

    public ItemLibrary(boolean init) {
        if(init) {
            this.inventory = new InventoryItem[]{
                    new InventoryItem("Coral", 1, Content.coralFile.getAbsolutePath()),
                    new InventoryItem("Fabricator", 1, Content.coralFile.getAbsolutePath()),
                    new InventoryItem("Flotation Device", 1, Content.coralFile.getAbsolutePath()),
                    new InventoryItem("Medium Plant 1", 1, Content.mediumPlantFile.getAbsolutePath()),
                    new InventoryItem("Medium Plant 2", 1, Content.mediumPlant2File.getAbsolutePath()),
                    new InventoryItem("Seaweed", 1, Content.seaweedFile.getAbsolutePath())
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
