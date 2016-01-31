package Forms;

import Main.Main;

import javax.swing.*;

public class SplashScreen extends JFrame {

    public SplashScreen(String resource) {
        super("AllThoseTerritories");
        setSize(Main.DEFAULT_FRAME_SIZE);
        setResizable(false);
        add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(resource))));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
