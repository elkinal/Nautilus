import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by alxye on 17-Jul-18.
 */
public class Player implements Shape {
    private int x; //center location
    private int y;
    private int size;
    private BufferedImage skin;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public int getLowerLeftX() {
        return Content.XPlayerOffset;
    }
    public int getLowerLeftY() {
        return Content.YPlayerOffset+this.size;
    }
    public int getLowerRightX() {
        return Content.XPlayerOffset+this.size;
    }
    public int getLowerRightY() {
        return Content.YPlayerOffset+this.size;
    }
    public int getUpperLeftX() {
        return Content.XPlayerOffset;
    }
    public int getUpperLeftY() {
        return Content.YPlayerOffset;
    }
    public int getUpperRightX() {
        return Content.XPlayerOffset+this.size;
    }
    public int getUpperRightY() {
        return Content.YPlayerOffset;
    }
    public int getCenterX() {
        return Content.XPlayerOffset+this.size/2;
    }
    public int getCenterY() {
        return Content.YPlayerOffset+this.size/2;
    }
    public BufferedImage getSkin() {
        return skin;
    }

    public void setSkin(BufferedImage skin) {
        this.skin = skin;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void draw(Graphics2D gr) {
        gr.drawRect(Content.XPlayerOffset,Content.YPlayerOffset,this.size,this.size);
    }
}
