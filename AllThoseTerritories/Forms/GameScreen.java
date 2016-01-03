package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Thomas on 03/01/2016.
 */
public class GameScreen extends JFrame{
    private JPanel GameScreen;

    public GameScreen(Player player1, Player player2, List<Kontinent> continents){
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
                            c.Draw(g, player1.Color,player2.Color);
                        }
                    }
                }
            }
        };

        getContentPane().add(this.GameScreen);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
