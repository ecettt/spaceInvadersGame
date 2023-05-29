import org.w3c.dom.css.Rect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Alien {
    public static final int alienWidth = 50;
    public static final int alienHeight = 50;
    public static int alienSpeed = 2;
    public static int alienHealth = 3;
    public int x;
    public int y;
    private int health;
    public ImageIcon alienImage;
    public static List<ImageIcon> alienImages;
    public static List<Rectangle> alienBullets;

    private static Random random = new Random();

    public Alien(int x, int y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
        alienBullets = new ArrayList<>();
        alienImage = getRandomAlienImage();
    }

    //public Rectangle getAlien(){return alien;}
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getHealth(){
        return health;
    }

    public void takeDamage(){
        health--;
    }
    public boolean isDestroyed(){
        return health <= 0;
    }

    public void draw(Graphics g){
        int drawX = x + (Alien.alienWidth - alienImage.getIconWidth()) / 2;
        int drawY = y + (Alien.alienHeight - alienImage.getIconHeight()) / 2;
        alienImage.paintIcon(null, g, drawX, drawY);

    }

    private static void loadAlienImages(){
        alienImages = new ArrayList<>();
        alienImages.add(new ImageIcon("images/alien_1.png"));
        alienImages.add(new ImageIcon("images/alien2_1.png"));
        alienImages.add(new ImageIcon("images/alien3_1.png"));
        alienImages.add(new ImageIcon("images/alien4_1.png"));
        alienImages.add(new ImageIcon("images/alien8_1.png"));
        alienImages.add(new ImageIcon("images/alien9_1.png"));
        alienImages.add(new ImageIcon("images/alien10_1.png"));
        alienImages.add(new ImageIcon("images/alien11_1.png"));
    }
    private static ImageIcon getRandomAlienImage() {
        if (alienImages == null) {
            loadAlienImages();
        }
        Random random = new Random();
        int index = random.nextInt(alienImages.size());
        return alienImages.get(index);
    }
    public static void spawnAliens(){
        Timer alienSpawnTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!GamePanel.gameOver){
                    int randomX = (int) (Math.random() * (GamePanel.PANEL_WIDTH - alienWidth));
                    //Rectangle alien = new Rectangle(randomX, -alienHeight, alienWidth, alienHeight);
                    GamePanel.aliens.add(new Alien(randomX, -alienHeight, alienHealth));
                }
            }
        });
        alienSpawnTimer.start();
    }


    public static void updateAlienPosition(){
        Iterator<Alien> alienIterator = GamePanel.aliens.iterator();
        while(alienIterator.hasNext()){
            Alien alien = alienIterator.next();
            alien.y += alienSpeed; //move enemy downwards

            if(alien.y > GamePanel.PANEL_HEIGHT){
                alienIterator.remove();
            }
        }
    }

}




