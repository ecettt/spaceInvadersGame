import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame implements ActionListener {
    LoginPanel loginPanel;
    JMenuBar menuBar;
    JMenuItem registerItem;
    JMenuItem playGameItem;
    JMenuItem highScoreItem;
    JMenuItem quitItem;
    JMenuItem aboutItem;
    JMenu fileMenu;
    JMenu helpMenu;
    JButton startButton;
    Font f = new Font(Font.DIALOG, Font.BOLD, 23);
    MyFrame(){
        loginPanel = new LoginPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("CSE 212 Term Project - Space Invader Game");
        this.setLayout(new FlowLayout());
        this.add(loginPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setContentPane(loginPanel);

        startButton = new JButton("PUSH START");
        startButton.setFocusable(true);
        startButton.setBounds(250, 400, 200, 60);
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setForeground(Color.DARK_GRAY);
        startButton.setFont(f);
        startButton.addActionListener(this);
        this.getContentPane().add(startButton);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");
        registerItem = new JMenuItem("Register");
        playGameItem = new JMenuItem("Play Game");
        highScoreItem = new JMenuItem("High Score");
        quitItem = new JMenuItem("Quit");
        aboutItem = new JMenuItem("About");
        fileMenu.add(registerItem);
        fileMenu.add(playGameItem);
        fileMenu.add(highScoreItem);
        helpMenu.add(aboutItem);
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        playGameItem.addActionListener(this);
        quitItem.addActionListener(this);
        this.setJMenuBar(menuBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==quitItem) {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (option == JOptionPane.YES_OPTION)
                System.exit(0);
        }
        else if(e.getSource() == playGameItem || e.getSource() == startButton){
            loginPanel.setVisible(false);
            LevelStartPanel levelStartPanel = new LevelStartPanel();
            this.setContentPane(levelStartPanel);
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    levelStartPanel.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    GamePanel gamePanel = new GamePanel();
                    setContentPane(gamePanel);
                    revalidate();
                    gamePanel.requestFocusInWindow();
                }
            });
            // Start the timer
            timer.start();

        }
    }
}






