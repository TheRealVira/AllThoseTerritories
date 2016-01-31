package Forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WinnerScreen extends JFrame {
    public WinnerScreen(boolean isPlayer1Winner) {
        super((isPlayer1Winner ? "Player1" : "Player2") + " Won!!!");
        initFrame();
        initComponents(isPlayer1Winner);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If someone gets to nervous xD
    }

    private void initFrame() {
        setLayout(null);
        setSize(500, 275);
        setLocationRelativeTo(Main.Main.CurrentFrame);
        setResizable(false);
    }

    private void initComponents(boolean isPlayer1Winner) {
        setContentPane(new JLabel(new ImageIcon(BattleScreen.class.getClassLoader().getResource("resources/Sprites/WinnerBg.png"))));

        JLabel winnerLabel = new JLabel();
        winnerLabel.setText(isPlayer1Winner ? "Player1" : "Player2");
        winnerLabel.setBounds(getWidth() / 2 - 35, 10, 70, 35);
        add(winnerLabel);

        JButton exitButton = new JButton();
        exitButton.setText("Exit");
        exitButton.setBounds(25, getHeight() - 85, 90, 35);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Main.setCurrentFrame(null); // Exit the game
            }
        });
        add(exitButton);

        JButton mainMenuButton = new JButton();
        mainMenuButton.setText("Menu");
        mainMenuButton.setBounds(getWidth() - 115, getHeight() - 85, 90, 35);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Main.setCurrentFrame(new MainMenuScreen()); // Show Main Menu.
            }
        });
        add(mainMenuButton);
    }
}
