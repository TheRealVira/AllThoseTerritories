package Forms;

import Main.Main;

import javax.swing.*;

/**
 * Created by Thomas on 01/01/2016.
 */
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
