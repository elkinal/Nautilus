import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by alxye on 19-Jul-18.
 */
public class InventoryItem {
    String name;
    int amount;
    InventoryItem[] ingredients;
    String imagePath;

    public InventoryItem(String name, int amount, String imagePath) {
        this.name = name;
        this.amount = amount;
        this.imagePath = imagePath;
    }
    public InventoryItem(String name, int amount, String imagePath, InventoryItem[] ingredients) {
        this.name = name;
        this.amount = amount;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
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

    public InventoryItem[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(InventoryItem[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
