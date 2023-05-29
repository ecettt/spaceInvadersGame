import javax.swing.*;
import java.awt.*;

public class LevelStartPanel extends JPanel {

    final int PANEL_WIDTH = 700;
    final int PANEL_HEIGHT = 700;
    JLabel label;
    Font labelFont;
    LevelStartPanel(){
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);
        this.setBackground(Color.black);
        label = new JLabel("LEVEL START");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        labelFont = new Font("Arial", Font.BOLD, 30); // Example font settings
        label.setFont(labelFont);
        label.setForeground(new Color(0xafff0f));
        label.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        this.add(label);
    }

}
