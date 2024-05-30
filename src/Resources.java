import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Resources {
    // to add an image to the environment:
    // 1. put the file into the res folder.
    // 2. Declare a variable before the static block.
    // 3. Initialize the variable by copying and pasting and modifying the
    //    ImageIO line.


    public static BufferedImage apple, banana, cherries,
    orange, peach, strawberry, plate, knife, waterdrop, background, shield1, shield2, shield3, shield4, fruit_basket;



    static{
        try{
//            test = ImageIO.read(new File("./res/test.png"));
//            ghost = ImageIO.read(new File("./res/ghost.png"));
            apple = ImageIO.read(new File("./res/apple.png"));
            banana = ImageIO.read(new File("./res/banana.png"));
            cherries = ImageIO.read(new File("./res/cherries.png"));
            orange = ImageIO.read(new File("./res/orange.png"));
            peach = ImageIO.read(new File("./res/peach.png"));
            strawberry = ImageIO.read(new File("./res/strawberry.png"));
            plate = ImageIO.read(new File("./res/plate.png"));
            knife = ImageIO.read(new File("./res/knife.png"));
            waterdrop = ImageIO.read(new File("./res/water_drop.png"));
            background = ImageIO.read(new File("./res/background.png"));
            shield1 = ImageIO.read(new File("./res/bush_shield.png"));
            shield2 = ImageIO.read(new File("./res/bush_shield2.png"));
            shield3 = ImageIO.read(new File("./res/bush_shield3.png"));
            shield4 = ImageIO.read(new File("./res/bush_shield4.png"));
            fruit_basket = ImageIO.read(new File("./res/fruit_basket.png"));

        }catch(Exception e){e.printStackTrace();}
    }
}