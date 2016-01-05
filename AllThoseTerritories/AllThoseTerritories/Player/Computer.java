package AllThoseTerritories.Player;

import AllThoseTerritories.Territorium;

import java.awt.*;
import java.util.Random;

/**
 * Created by Thomas on 03/01/2016.
 */
public class Computer extends Player {
    public Computer(java.awt.Color color, boolean player1) {
        super(color,player1);
    }

    @Override
    public String Update(int gameState, Territorium target, Random rand) {
        return "";
    }
}
