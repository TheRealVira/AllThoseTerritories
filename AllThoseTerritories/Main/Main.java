package Main;

import Forms.MainMenu;
import Forms.SplashScreen;

import javax.swing.*;

/**
 * Main.Main class to initialize everything. This is where it all begins.
 */
public class Main {
    private static JFrame CurrentFrame;

    public static void SetCurrentFrame(JFrame frame){
        if(CurrentFrame!=null) {
            CurrentFrame.setVisible(false);
            CurrentFrame.dispose(); // Clean exit on last frame.
        }

        CurrentFrame=frame;
        if(CurrentFrame!=null){
            CurrentFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SetCurrentFrame(new SplashScreen("resources/AllThoseTerritories.png"));

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SetCurrentFrame(new MainMenu());
    }
}