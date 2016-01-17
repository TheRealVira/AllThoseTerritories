package Forms;

import javax.swing.*;

/**
 * Created by Thomas on 01/01/2016.
 */
public class SplashScreen extends JFrame {

    public SplashScreen(String resource) {
        super("AllThoseTerritories");
        setSize(1250, 679); // We have to add 30 px to spacing issues with swing...
        setResizable(false);
        add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource(resource))));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
