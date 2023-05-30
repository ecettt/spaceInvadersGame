import org.w3c.dom.css.Rect;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class Alien {
    private int x, y;
    private Timer shootTimer;
    private int bulletDelay;
    public static final int alienWidth = 50;
    public static final int alienHeight = 50;
    public static double alienSpeed = 2.0;
    private int health = 3;
    public ImageIcon alienImage;
    public static List<ImageCoordinator> alienImages;
    public List<Rectangle> alienBullets;
    private static final Random random = new Random();
    int alienImageIndex;

    static {
        alienImages = new ArrayList<>();
        alienImages.add(new ImageCoordinator("images/alien_1.png", "images/alien_2.png"));
        alienImages.add(new ImageCoordinator("images/alien2_1.png", "images/alien2_2.png"));
        alienImages.add(new ImageCoordinator("images/alien3_1.png","images/alien3_2.png" ));
        alienImages.add(new ImageCoordinator("images/alien4_1.png", "images/alien4_2.png"));
        alienImages.add(new ImageCoordinator("images/alien8_1.png", "images/alien8_2.png"));
        alienImages.add(new ImageCoordinator("images/alien9_1.png", "images/alien9_2.png"));
        alienImages.add(new ImageCoordinator("images/alien10_1.png", "images/alien10_2.png"));
        alienImages.add(new ImageCoordinator("images/alien11_1.png", "images/alien11_2.png"));
    }

    public Alien() {
        x = (int) (Math.random() * (GamePanel.PANEL_WIDTH - Alien.alienWidth));
        y = -alienHeight;
        alienBullets = new ArrayList<>();
        alienImageIndex = getRandomAlienImageIndex();
        alienImage = alienImages.get(alienImageIndex).getImage();
        bulletDelay = random.nextInt(10000); //Random delay between 5 and 13 seconds
        shootTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!GamePanel.gameOver) {
                    shootBullet();

                }
            }
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                shootBullet();
                shootTimer.start();
            }
        };
        new java.util.Timer().schedule(task, bulletDelay);
    }

    public void stop() {
        shootTimer.stop();
        alienBullets.clear();
    }

    public boolean hasCollision(Rectangle rect) {
        Iterator<Rectangle> alienBulletIterator = alienBullets.iterator();
        while (alienBulletIterator.hasNext()) {
            Rectangle bullet = alienBulletIterator.next();
            if (bullet.intersects(rect)) {
                alienBulletIterator.remove();
                return true;
            }
        }
        return false;
    }

    //public Rectangle getAlien(){return alien;}
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage() {
        health--;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void draw(Graphics g) {
        int drawX = x + (Alien.alienWidth - alienImage.getIconWidth()) / 2;
        int drawY = y + (Alien.alienHeight - alienImage.getIconHeight()) / 2;
        alienImage.paintIcon(null, g, drawX, drawY);

        //alien bullets
        g.setColor(Color.PINK);
        for (Rectangle bullet : alienBullets) {
            g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        }

    }

    private void shootBullet() {
        int bulletX = x + alienWidth / 2 - 2;
        int bulletY = y + alienHeight;
        Rectangle bullet = new Rectangle(bulletX, bulletY, 4, 10);
        alienBullets.add(bullet);
    }

    int k = 0;
    int xDirecton;

    public void updateAlienPosition() {
        y += alienSpeed;
        k++;
        if(k % 50 == 0)
        {
            alienImage = alienImages.get(this.alienImageIndex).switchImage();//to make aliens' arms up and down
        }
        if (k > 100) {
            k = 0;

            if (random.nextInt(10) > 5) {
                xDirecton = 1;
            } else
                xDirecton = -1;
        }
        x = x + xDirecton;

        if (y > GamePanel.PANEL_HEIGHT) {
            health = 0;
        }
        updateAlienBulletsPosition();
    }

    private void updateAlienBulletsPosition() {
        Iterator<Rectangle> bulletIterator = alienBullets.iterator();
        while (bulletIterator.hasNext()) {
            Rectangle bullet = bulletIterator.next();
            bullet.y += 5;

            if (bullet.y + bullet.height > GamePanel.PANEL_HEIGHT) { // Remove bullets that go off the bottom of the panel
                bulletIterator.remove();
            }
        }
    }



    private static int getRandomAlienImageIndex() {

        Random random = new Random();
        int index = random.nextInt(alienImages.size());
        return index;
    }
}


