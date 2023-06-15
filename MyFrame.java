import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


public class MyFrame extends JFrame implements ActionListener, ComponentListener {
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
    GamePanel gamePanel;
    RegisterPanel register;
    HighScorePanel highScore;
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
        registerItem.addActionListener(this);
        highScoreItem.addActionListener(this);
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
        else if(e.getSource() == playGameItem || e.getSource() == startButton) {
            loginPanel.setVisible(false);
            register = new RegisterPanel(false);
            register.addComponentListener(this);
            this.setContentPane(register);
            revalidate();
        }

        else if(e.getSource() == registerItem){
            loginPanel.setVisible(false);
            register = new RegisterPanel(true);
            register.addComponentListener(this);
            this.setContentPane(register);
            revalidate();
        }
        else if(e.getSource() ==highScoreItem){
            loginPanel.setVisible(false);
            highScore = new HighScorePanel();
            highScore.addComponentListener(this);
            this.setContentPane(highScore);
            revalidate();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        if(!register.onlyRegister){
            LevelStartPanel levelStartPanel = new LevelStartPanel();
            this.setContentPane(levelStartPanel);
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    levelStartPanel.setVisible(false);
                    ((Timer) e.getSource()).stop();
                    gamePanel = new GamePanel();
                    setContentPane(gamePanel);
                    revalidate();
                    gamePanel.requestFocusInWindow();
                }
            });
            // Start the timer
            timer.start();
        }
        if(register.onlyRegister){
            loginPanel.setVisible(true);
            this.setContentPane(loginPanel);
            revalidate();

        }


        }

}






