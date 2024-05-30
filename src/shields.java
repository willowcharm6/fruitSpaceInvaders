import java.awt.*;
import java.awt.image.BufferedImage;

public class shields extends Sprite{
    private BufferedImage image;
    private Point location;
    private int lives;

    public shields(BufferedImage image, Point location, int lives) {
        super(image, location);
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }


}
