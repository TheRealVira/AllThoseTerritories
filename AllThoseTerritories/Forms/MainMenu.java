package Forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Thomas on 01/01/2016.
 */
public class MainMenu extends JFrame{
    private JButton StartSPGame=new JButton("Start single Player Game");
    private JButton StartMPGame=new JButton("Start multy Player Game");
    private JButton Exit=new JButton("EXIT");

    public MainMenu() {
        super("AllThoseTerritories");
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("resources/ATT.png"))));
        setLayout(null);

        StartMPGame.setBounds(50,50,250,50);
        StartSPGame.setBounds(50,150,250,50);
        Exit.setBounds(50,570,100,50);
        add(StartMPGame);
        add(StartSPGame);
        add(Exit);

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Test");
                if(Exit.getModel().isPressed()){
                    // TODO: Find out how to close the window after pressing the exit button.
                    // dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}