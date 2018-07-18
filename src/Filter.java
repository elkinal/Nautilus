import java.awt.*;

/**
 * Created by alxye on 18-Jul-18.
 */
public class Filter implements Shape {
    Color color;

    public Filter(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D gr) {
        gr.setColor(this.color);
        gr.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
    }
}
