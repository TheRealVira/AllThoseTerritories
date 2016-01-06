package Forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Thomas on 01/01/2016.
 */
public class WinnerScreen extends JFrame {
    public WinnerScreen(boolean Winner){
        setLayout(null);
        setSize(500,275);
        setLocationRelativeTo(null);

        setContentPane(new JLabel(new ImageIcon(BattleScreen.class.getClassLoader().getResource("resources/Sprites/WinnerBg.png"))));

        JLabel winnerLabel=new JLabel();
        winnerLabel.setText(Winner?"Player1":"Player2");
        winnerLabel.setBounds(getWidth()/2-35,10,70,35);
        add(winnerLabel);

        JButton axidentlyButton=new JButton();
        axidentlyButton.setText("Exit");
        axidentlyButton.setBounds(25,getHeight()-85,90,35);
        axidentlyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Main.SetCurrentFrame(null); // Exit the game
            }
        });
        add(axidentlyButton);

        JButton mainMenuButton=new JButton();
        mainMenuButton.setText("Menu");
        mainMenuButton.setBounds(getWidth()-115,getHeight()-85,90,35);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.Main.SetCurrentFrame(new MainMenuScreen()); // Exit the game
            }
        });
        add(mainMenuButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // If someone gets to nervous xD
    }
}
