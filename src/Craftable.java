import java.util.ArrayList;

/**
 * Created by alxye on 21-Jul-18.
 */
public class Craftable {
    private String name;
    private int number;
    private InventoryItem[] requirements;

    public Craftable(String name, int number, InventoryItem[] requirements) {
        this.name = name;
        this.number = number;
        this.requirements = requirements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public InventoryItem[] getRequirements() {
        return requirements;
    }

    public void setRequirements(InventoryItem[] requirements) {
        this.requirements = requirements;
    }
}
