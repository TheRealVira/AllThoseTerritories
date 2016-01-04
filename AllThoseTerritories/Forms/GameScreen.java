package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Thomas on 03/01/2016.
 */
public class GameScreen extends JFrame{
    private JPanel GameScreen;
    private JFrame This=this;
    private List<Kontinent>Continents;
    private Player Player1,Player2;

    private static final int DELAY=60;
    private boolean GameIsRunning=true;

    public GameScreen(Player player1, Player player2, List<Kontinent> continents){
        super("AllThoseTerritories");
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);
        this.Continents=continents;
        this.Player1=player1;
        this.Player2=player2;

        this.GameScreen=new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (continents != null) {
                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.DrawConnections(g, new Dimension(1250,680));
                        }
                    }

                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.Draw(g, This, Player1.Color, Player2.Color);
                        }
                    }
                }
            }
        };

        Thread drawThread=new Thread(){
            public void run(){
                do {
                    if (GameScreen != null) {
                       GameScreen.repaint();
                    }
                    try {
                        Thread.sleep(60l); // I had damp it because it would eat all the cpu performance xD
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (GameIsRunning) ;
            }
        };
        drawThread.start();

        getContentPane().add(this.GameScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
