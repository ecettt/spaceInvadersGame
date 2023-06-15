import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegisterPanel extends JPanel implements ActionListener {
    boolean isAnimating = true;
    final int PANEL_WIDTH = 700;
    final int PANEL_HEIGHT = 700;
    int imageY = 10;
    Thread alienAnimationThread;
    Image alienImage;

    Image backgroundImage;
    JButton registerButton = new JButton("Register");
    JButton resetButton = new JButton("Reset");
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JLabel usernameLabel = new JLabel("Username: ");
    JLabel passwordLabel = new JLabel("Password: ");
    JLabel gameNameLabel = new JLabel("SPACE INVADERS");
    boolean onlyRegister;

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(alienImage, 300, imageY, 90, 85, null);
    }

    RegisterPanel(boolean onlyRegister){

        this.onlyRegister = onlyRegister;
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);
        backgroundImage = new ImageIcon("images/spaceImage.png").getImage(); //register screen screen background
        alienImage = new ImageIcon("images/alien9_2.png").getImage();
        this.setFocusable(true);
        gameNameLabel.setForeground(Color.white);
        gameNameLabel.setFont(new Font("Arial", Font.ITALIC, 28));
        gameNameLabel.setBounds(230, 250, 500, 40);
        usernameLabel.setForeground(Color.white);
        usernameLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        usernameLabel.setBounds(190, 320, 100, 30);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setBounds(190, 400, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        usernameField.setBounds(300, 310, 200, 30);
        passwordField.setBounds(300, 390, 200, 30);
        registerButton.setBounds(300, 470, 120, 35);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        resetButton.setBounds(430, 470, 120, 35);
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);
        this.add(gameNameLabel);
        this.add(usernameLabel);
        this.add(passwordLabel);
        this.add(usernameField);
        this.add(passwordField);
        this.add(registerButton);
        this.add(resetButton);
        alienAnimationThread = new Thread(new AnimationRunnable());
        alienAnimationThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetButton){
            usernameField.setText("");
            passwordField.setText("");
        }
        if(e.getSource()==registerButton){
            isAnimating = false;
            this.setVisible(false);
            try {
                alienAnimationThread.join(); //threadin bitişini bekle
            } catch (InterruptedException ex) {

            }
            FileUtil.createFile();
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            if(username.length() > 20)
                username = username.substring(0,20);
            else
                username = StringUtil.padRight(username, 20, ' ');
            if(password.length() > 20)
                password = password.substring(0,20);
            else
                password = StringUtil.padRight(password, 20, ' ');
            FileUtil.addOrUpdate(username, password, "00000");
            //FileUtil.updateScore("test1", "00012");
            GamePanel.activeUser = username;
        }
    }

    private class AnimationRunnable implements Runnable {
        private static int SPEED = 5;
        @Override
        public void run() {
            try {
                while (isAnimating) {
                    imageY += SPEED;
                    if (imageY >= 150 || imageY <= 0) {
                        SPEED *= -1;  // Reverse the direction when reaching the upper or lower limit
                    }
                    repaint();

                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
