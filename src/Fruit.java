import java.awt.*;
import java.awt.image.BufferedImage;

public class Fruit extends Sprite{
    private BufferedImage image;
    private Point location;
    private boolean right;

    public Fruit(BufferedImage image, Point location, boolean right) {
        super(image, location);
        this.right = right;
    }

    public void update(){
        int y = 0;
        if (right){
            move(4, y);
        }
        if (!right)
            move(-4, y);
        if (getX() < 100) {
            y += 5;
            right = true;
        }
        if (getX() > 700) {
            y += 5;
            right = false;
        }
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
