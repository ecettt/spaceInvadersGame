import javax.swing.*;
import java.awt.*;
public class GameOverPanel extends JPanel {

    final int PANEL_WIDTH = 700;
    final int PANEL_HEIGHT = 700;

    Image backgroundImage;

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }
    GameOverPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);
        backgroundImage = new ImageIcon("images/gameOver.png").getImage(); //game over screen background
        this.setFocusable(true);

    }
}
