import java.util.ArrayList;

/**
 * Created by alxye on 19-Jul-18.
 */
public class Inventory {
    ArrayList<InventoryItem> items;

    public Inventory(ArrayList<InventoryItem> items) {
        this.items = items;
    }



    public ArrayList<InventoryItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<InventoryItem> items) {
        this.items = items;
    }
    public void addItems(InventoryItem item) {
        boolean flag = false;
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getName().equals(item.getName())) {
                items.get(i).amount++;
                flag = true;
                break;
            }
        }
        if(!flag)
             this.items.add(item);

    }
}
