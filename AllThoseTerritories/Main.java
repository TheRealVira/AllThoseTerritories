import Forms.MainMenu;
import Forms.SplashScreen;

import javax.swing.*;

/**
 * Main class to initialize everything. This is where it all begins.
 */
public class Main {
    public static JFrame CurrentFrame;

    public static void main(String[] args) {
        CurrentFrame=new SplashScreen("resources/AllThoseTerritories.png");
        CurrentFrame.setVisible(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CurrentFrame.setVisible(false);
        CurrentFrame.dispose(); // Clean exit

        CurrentFrame=new MainMenu();
        CurrentFrame.setVisible(true);
    }
}
