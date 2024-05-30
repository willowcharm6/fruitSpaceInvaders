import java.awt.*;
import java.awt.image.BufferedImage;

public class Knife extends Sprite{
    private BufferedImage image;
    private Point location;

    public Knife(BufferedImage image, Point location) {
        super(image, location);
    }
}
