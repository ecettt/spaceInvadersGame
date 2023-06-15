import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel implements KeyListener, ActionListener{
    static final int PANEL_WIDTH = 700;
    static final int PANEL_HEIGHT = 700;
    private int spaceShipX = 325; //initial x position of the spaceship
    private int spaceShipY = 560; //initial y position of the spaceship
    private int spaceShipHealth = 3;
    private final int spaceshipWidth = 50; // width of the spaceship
    private final int spaceshipHeight = 50; // height of the spaceship
    private int spaceShipSpeed = 10;
    private List<Rectangle> bullets;
    public static List<Alien> aliens;
    public static final Object syncObject = new Object();

    public static int score = 0;
    public static boolean gameOver = false;
    int delay = 2000;
    int level = 1;
    static String activeUser;

    Image backgroundImage = new ImageIcon("images/spaceImage.png").getImage();
    public List<Alien> getAliens(){
        return aliens;
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        //spaceship
        Image spaceshipImage = new ImageIcon("images/spaceship.png").getImage();
        int spaceshipCenterX = spaceShipX + spaceshipWidth / 2;
        int spaceshipImageCenterX = spaceshipImage.getWidth(null) / 2;
        int spaceshipOffsetX = spaceshipCenterX - spaceshipImageCenterX;
        // Draw the spaceship with the adjusted x position
        g.drawImage(spaceshipImage, spaceshipOffsetX, spaceShipY, null);
        //bullets
        g.setColor(Color.WHITE);
        for(Rectangle bullet : bullets){
            g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
        }
        //aliens
        for(Alien alien : aliens){
            alien.draw(g);
        }
        //score
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(String.valueOf(score), 350, 20);

        //spaceship health
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("\u2665"+" x"+ spaceShipHealth,150, 20);

        //level
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Level: "+ level,40, 20);


    }
    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        addKeyListener(this);
        bullets = new ArrayList<>();
        aliens = new ArrayList<>();
        Timer timer = new Timer(20, this);
        timer.start();
        spawnAliens();
    }

    private void nextLevel() { //Score her 500 arttığında level artışı ve uzaylıların çıkış hızının artışı
        if (score % 500 == 0 && score >= 500) {
            level++;
            Alien.alienSpeed += 0.5;
            delay -= 300;
            System.out.println(Alien.alienSpeed);
            System.out.println(level);
            System.out.println(delay);
        }
    }
    private void spawnAliens(){
        Timer alienSpawnTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!GamePanel.gameOver){
                    synchronized (syncObject){
                        aliens.add(new Alien());
                    }
                }
            }
        });
        alienSpawnTimer.start();
    }
    private void updateCharacterPosition(){
        if (isKeyPressed(KeyEvent.VK_UP)) {
            spaceShipY -= spaceShipSpeed;  // Move character up
        } else if (isKeyPressed(KeyEvent.VK_DOWN)) {
            spaceShipY += spaceShipSpeed;  // Move character down
        } else if (isKeyPressed(KeyEvent.VK_LEFT)) {
            spaceShipX -= spaceShipSpeed;  // Move character left
        } else if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            spaceShipX += spaceShipSpeed;  // Move character right
        }

        spaceShipX = Math.max(0, Math.min(spaceShipX, getWidth() - spaceshipWidth));
        spaceShipY = Math.max(0, Math.min(spaceShipY, getWidth() - spaceshipHeight));
        repaint();  // Redraw the panel to update the character's position

    }

    private void updateBulletPosition(){
        Iterator<Rectangle> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext()){
            Rectangle bullet = bulletIterator.next();
            bullet.y -= 5;

            if(bullet.y + bullet.height < 0 ){ //remove bullets that go off the top of the panel
                bulletIterator.remove();
            }
        }
    }

    private void checkCollisions(){
        Iterator<Rectangle> bulletIterator = bullets.iterator();
        while(bulletIterator.hasNext()){
            Rectangle bullet = bulletIterator.next();

            Iterator<Alien> alienIterator = aliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien = alienIterator.next();

                if(bullet.intersects(alien.getX(), alien.getY(), Alien.alienWidth, Alien.alienHeight)){
                    alien.takeDamage();
                    bulletIterator.remove();

                    if(alien.isDestroyed()){
                        if(alien.alienImage.equals(Alien.alienImages.get(0).getImage())||
                           alien.alienImage.equals(Alien.alienImages.get(1).getImage())||
                           alien.alienImage.equals(Alien.alienImages.get(2).getImage())){
                            alienIterator.remove();
                            score+=10; //increase the score by 10 if one of the purple aliens
                        }else {
                            alienIterator.remove();
                            score+=5; //increase the score by 5 if one of the green aliens
                        }
                        nextLevel();
                    }
                }
            }
        }

        Rectangle spaceShipRectangle = new Rectangle(spaceShipX, spaceShipY, spaceshipWidth, spaceshipHeight);
        for(Alien alien : aliens){
            if(spaceShipRectangle.intersects(alien.getX(), alien.getY(), Alien.alienWidth, Alien.alienHeight))
                gameOver=true;
            else if(alien.hasCollision(spaceShipRectangle)){
                spaceShipHealth--;
                if(spaceShipHealth == 0)
                    gameOver=true;

            }
        }

    }
    private void checkGameOver() throws IOException {
        if(gameOver){
            for (Alien a: aliens) {
                a.stop();
            }
            synchronized (syncObject){
                aliens.clear();
            }
            bullets.clear();

            MyFrame frame = (MyFrame) SwingUtilities.getWindowAncestor(this);
            frame.getContentPane().remove(this);  // Remove the current GamePanel from the frame

            GameOverPanel gameOverPanel = new GameOverPanel();
            frame.setContentPane(gameOverPanel);  // Set the GameOverPanel as the new content pane

            frame.pack();
            frame.revalidate();
            frame.repaint();

            FileUtil.updateScore(activeUser, StringUtil.padRight(String.valueOf(score), 5, ' '));

        }
    }
    private boolean isKeyPressed(int keyCode){
        return keyPressedSet.contains(keyCode);
    }

    private final HashSet<Integer> keyPressedSet = new HashSet<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyPressedSet.add(keyCode);
        if(keyCode==KeyEvent.VK_SPACE){
            int bulletX = spaceShipX + spaceshipWidth / 2 - 2;
            Rectangle bullet = new Rectangle(bulletX, spaceShipY, 4, 10);
            bullets.add(bullet);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        keyPressedSet.remove(keyCode);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver){
            updateCharacterPosition();
            updateBulletPosition();
            synchronized (syncObject){
                for(Alien alien: aliens){
                    alien.updateAlienPosition();
                }
            }
            checkCollisions();

            try {
                checkGameOver();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
        }
    }
}







