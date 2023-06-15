import javax.swing.*;
import java.awt.*;
import java.io.*;

public class HighScorePanel extends JPanel {
    JTextArea textField;

    final int PANEL_WIDTH = 700;
    final int PANEL_HEIGHT = 700;
    JLabel highScoreLabel = new JLabel("HIGH SCORES");
    static String filePath = "/Users/ecetipici/IdeaProjects/spaceInvadersGame/scores.txt";


    Image backgroundImage;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
    }

    HighScorePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);
        backgroundImage = new ImageIcon("images/spaceImage.png").getImage();
        this.setFocusable(true);
        highScoreLabel.setForeground(Color.white);
        highScoreLabel.setFont(new Font("Arial", Font.ITALIC, 28));
        highScoreLabel.setBounds(250, 100, 500, 40);
        this.add(highScoreLabel);
        textField = new JTextArea();
        textField.setBounds(120, 150,490, 370);
        textField.setBackground(Color.white);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setText(FileUtil.readUsernameAndScore(filePath));
        this.add(textField);
    }
}