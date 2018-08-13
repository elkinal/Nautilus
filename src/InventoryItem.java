import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.Arrays;

/**
 * Created by alxye on 19-Jul-18.
 */
public class InventoryItem {
    String name;
    int amount;
    InventoryItem[] ingredients;
    BufferedImage imageFile;


    public InventoryItem(String name, int amount, BufferedImage imageFile) {
        this.name = name;
        this.amount = amount;
        this.imageFile = imageFile;
    }
    public InventoryItem(String name, int amount, BufferedImage imageFile, InventoryItem[] ingredients) {
        this.name = name;
        this.amount = amount;
        this.imageFile = imageFile;
        this.ingredients = ingredients;
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
    public void increaseAmount(int i) {
        this.amount += i;
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

    public BufferedImage getImageFile() {
        return imageFile;
    }

    public void setImageFile(BufferedImage imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "ingredients=" + Arrays.toString(ingredients) +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
