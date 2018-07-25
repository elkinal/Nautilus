import java.util.ArrayList;

/**
 * Created by alxye on 19-Jul-18.
 */
public class InventoryItem {
    String name;
    int amount;

    public InventoryItem(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
