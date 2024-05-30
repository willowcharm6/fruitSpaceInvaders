import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

// MR HOPPS
public class Main extends JPanel {

    private Timer timer;  // fires an event to trigger updating the animation.
    private Sprite player;
    private ArrayList<Fruit> enemies;
    private ArrayList<Knife> knives;
    private ArrayList<Waterdrops> waterdrops;
    private ArrayList<shields> shields;
    private int knifedelay, lives;
    private boolean[] keys;
    private boolean isGameRun, isDead, isWon;

    public Main(int w, int h){
        setSize(w, h);
        lives = 3;
        isGameRun = false;
        isDead = false;
        isWon = false;
        player = new Sprite(Resources.plate, new Point(400, 700));
        knifedelay = 40;
        setupKeys();
        //set up enemies
        setupEnemies();
        setupShields();
        knives = new ArrayList<>();
        keys = new boolean[256];
        waterdrops = new ArrayList<>();
        timer = new Timer(1000/60, e->update());
        timer.start();
    }
    public void setupEnemies(){
        enemies = new ArrayList<>();
        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                int n = (int) (Math.random() * 6);
                if (n == 0)
                    enemies.add(new Fruit(Resources.apple, new Point(r*100 + 175, c*72+10), true));
                if (n == 1)
                    enemies.add(new Fruit(Resources.banana, new Point(r*100 + 160, c*72+10), true));
                if (n == 2)
                    enemies.add(new Fruit(Resources.cherries, new Point(r*100 + 150, c*72+10), true));
                if (n == 3)
                    enemies.add(new Fruit(Resources.orange, new Point(r*100 + 175, c*72+10), true));
                if (n == 4)
                    enemies.add(new Fruit(Resources.peach, new Point(r*100 + 175, c*72+10), true));
                if (n == 5)
                    enemies.add(new Fruit(Resources.strawberry, new Point(r*100 + 175, c*72+5), true));
            }
        }
    }
    public void setupShields(){
        shields = new ArrayList<>();
        shields.add(new shields(Resources.shield1, new Point(70, 500), 8));
        shields.add(new shields(Resources.shield1, new Point(320, 500), 8));
        shields.add(new shields(Resources.shield1, new Point(600, 500), 8));
    }

//    public void shrivel(){
//
//    }
    public void update(){
        if ((keys[KeyEvent.VK_R] && isDead) || (keys[KeyEvent.VK_R] && isWon)) {
            isGameRun = true;
            isDead = false;
            lives = 3;
            setupEnemies();
            setupShields();
            knives = new ArrayList<>();
            waterdrops = new ArrayList<>();
            isWon = false;
        }
        if (keys[KeyEvent.VK_SPACE] && !isDead)
            isGameRun = true;
        if (isGameRun) {
            if (keys[KeyEvent.VK_A]) { //move player left...
                player.move(-8, 0);
                if (player.getX() < 0)
                    player.setLocation(0, player.getY());
            }
            if (keys[KeyEvent.VK_D]) { //move player right...
                player.move(8, 0);
                if (player.getX() + player.getWidth() > getWidth())
                    player.setLocation(getWidth() - player.getWidth(), player.getY());
            }
            if (keys[KeyEvent.VK_K] && knifedelay > 40) {
                knives.add(new Knife(Resources.knife, new Point(player.getX(), player.getY())));
                knifedelay = 0;
            }

            knifedelay++;
            // bounce fruit arraylist
            {
                int y = 0;
                for (Fruit f : enemies) {
                    if (f.isRight()) {
                        f.move(4, y);
                    }
                    if (!f.isRight())
                        f.move(-4, y);

                }
                for (Fruit f : enemies) {
                    if (f.getX() < 25) {
                        for (Fruit i : enemies) {
                            i.setRight(true);
                            i.move(0, 1);
                        }
                        y += 5;
                    }
                    if (f.getX() > 700) {
                        for (Fruit i : enemies) {
                            i.setRight(false);
                            i.move(0, 1);
                        }
                        y += 5;
                    }
                }
            }
            for (Knife k : knives) {
                k.move(0, -5);
            }
            //if hit, delete enemies and knife
            {
                for (int f = 0; f < enemies.size(); f++) {
                    for (int k = 0; k < knives.size(); k++) {
                        if (enemies.get(f).intersects(knives.get(k))) {
                            enemies.remove(f);
                            knives.remove(k);
                            break;
                        }
                    }

                }
            }

            //create water drops
            {
                if (Math.random() < 0.03 && enemies.size() > 0) {
                    int rand = (int) (Math.random() * enemies.size());
                    int x = enemies.get(rand).getX();
                    int y = enemies.get(rand).getY();
                    waterdrops.add(new Waterdrops(Resources.waterdrop, new Point(x, y)));
                    for (int i = 0; i < waterdrops.size(); i++) {
                        int ty = waterdrops.get(i).getY();
                        if (ty > 800) {
                            waterdrops.remove(i);
                            i--;
                        }
                    }
                }
                for (Waterdrops w : waterdrops)
                    w.move(0, 5);
            }
            for (int w = 0; w < waterdrops.size(); w++) {
                if (waterdrops.get(w).intersects(player)) {
                    lives--;
                    waterdrops.remove(w);
                    break;
                }
            }
            if (lives <= 0) {
                isDead = true;
                isGameRun = false;
            }
            // if drops intersect with shields
            for (int s = 0; s < shields.size(); s++) {
                for (int w = 0; w < waterdrops.size(); w++) {
                    if (waterdrops.get(w).intersects(shields.get(s))){
                        shields.get(s).setLives(shields.get(s).getLives()-1);
                        waterdrops.remove(w);
                        break;
                    }
                }
                if (shields.get(s).getLives() == 6)
                    shields.get(s).setImage(Resources.shield2);
                if (shields.get(s).getLives() == 4)
                    shields.get(s).setImage(Resources.shield3);
                if (shields.get(s).getLives() == 2)
                    shields.get(s).setImage(Resources.shield4);
            }
            for (int s = 0; s < shields.size(); s++) {
                if (shields.get(s).getLives() <= 0){
                    shields.remove(s);
                    break;
                }
            }

            for (int s = 0; s < shields.size(); s++) {
                for (int k = 0; k < knives.size(); k++) {
                    if (knives.get(k).intersects(shields.get(s))){
                        knives.remove(k);
                        break;
                    }
                }
            }
        }

        if (enemies.size() == 0){
            isGameRun = false;
            isDead = false;
            isWon = true;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (isGameRun) {
            g2.setColor(new Color(56, 150, 229));
            g2.fillRect(0, 0, 800, 800);
            g2.drawImage(Resources.background, 0, 0, null);
            player.draw(g2);
            for (Fruit f : enemies) {
                f.draw(g2);
            }
            for (Knife k : knives)
                k.draw(g2);
            for (Waterdrops w : waterdrops)
                w.draw(g2);
            g2.setColor(new Color(0, 0, 0));
            g2.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g2.drawString("Lives: " + lives, 15, 25);
            g2.drawString("Kills:  " + (25 - enemies.size()), 680, 25);
            for (int i = 0; i < shields.size(); i++) {
                shields.get(i).draw(g2);
            }
        }
        if (!isGameRun && !isDead){
            g2.setColor(new Color(255, 184, 184));
            g2.fillRect(0, 0, 800, 800);
            g2.setColor(new Color(0, 0, 0));
            g2.setFont(new Font("Monospaced", Font.PLAIN, 50));
            g2.drawString("Click Space to start!", 105, 370);
            g2.drawString("awsd to move", 200, 430);
            g2.drawString("k to throw knives", 120, 490);
        }
        if (!isGameRun && isDead){
            g2.setColor(new Color(156, 232, 250));
            g2.fillRect(0, 0, 800, 800);
            g2.setColor(new Color(0, 0, 0));
            g2.setFont(new Font("Monospaced", Font.PLAIN, 35));
            g2.drawString("You died. Click R to restart.", 100, 400);
        }
        if (!isGameRun && isWon){
            g2.setColor(new Color(245, 208, 208));
            g2.fillRect(0, 0, 800, 800);
            g2.setColor(new Color(0, 0, 0));
            g2.setFont(new Font("Monospaced", Font.PLAIN, 50));
            g2.drawString("You Won!", 260, 375);
            g2.drawString("Click R to restart.", 100, 425);
            g2.drawImage(Resources.fruit_basket, 280, 500, null);
        }


    }

    public void setupKeys(){
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
        });
    }


    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 800;
        int height = 800;
        window.setBounds(0, 0, width, height + 22); //(x, y, w, h) 22 due to title bar.

        JPanel panel = new Main(width, height);
        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(true);
    }
}