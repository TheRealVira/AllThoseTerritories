package Main;

import Forms.MainMenuScreen;
import Forms.SplashScreen;

import javax.swing.*;

/**
 * Main.Main class to initialize everything. This is where it all begins.
 */
public class Main {
    public static JFrame CurrentFrame;

    public static void SetCurrentFrame(JFrame frame) {
        // Set location of new frame relative to last frame, before disposing the latter.
        if (frame != null)
            frame.setLocationRelativeTo(CurrentFrame);

        if (CurrentFrame != null) {
            CurrentFrame.setVisible(false);
            CurrentFrame.dispose(); // Clean exit on last frame.
        }

        if (frame == null) {
            System.exit(0);
        }

        CurrentFrame = frame;
        if (CurrentFrame != null) {
            CurrentFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        SetCurrentFrame(new SplashScreen("resources/Sprites/AllThoseTerritories.png"));

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SetCurrentFrame(new MainMenuScreen());
    }
}
