package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static Main.Tools.Drawline;

/**
 * Created by Thomas on 03/01/2016.
 */
public class Game extends JFrame{
    private JPanel GameScreen;

    public Game(Player player1, Player player2, List<Kontinent> continents){
        super("AllThoseTerritories");
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);

        this.GameScreen=new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                if(continents!=null){
                    for (Kontinent c :
                            continents) {
                        if (c != null) {
                            c.Draw(g, Color.GREEN,Color.RED);
                        }
                    }
                }
            }
        };

        getContentPane().add(this.GameScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
