/**
 * Created by alxye on 20-Jul-18.
 */
public class Level {
    private int minDepth;
    private byte[][] content;

    public Level(byte[][] content, int minDepth) {
        this.content = content;
        this.minDepth = minDepth;
    }

    public int getMinDepth() {
        return minDepth;
    }

    public void setMinDepth(int minDepth) {
        this.minDepth = minDepth;
    }

    public byte[][] getContent() {
        return content;
    }

    public void setContent(byte[][] content) {
        this.content = content;
    }
}
