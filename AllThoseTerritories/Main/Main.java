package Main;

import Forms.MainMenuScreen;
import Forms.SplashScreen;

import javax.swing.*;
import java.awt.*;

/**
 * Main.Main class to initialize everything. This is where it all begins.
 */
public class Main {
    // We have to add 29 px to spacing issues with swing...
    public static final Dimension DEFAULT_FRAME_SIZE = new Dimension(1250, 679);

    public static JFrame CurrentFrame;

    public static void setCurrentFrame(JFrame frame) {
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
        setCurrentFrame(new SplashScreen("resources/Sprites/AllThoseTerritories.png"));

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setCurrentFrame(new MainMenuScreen());
    }
}
