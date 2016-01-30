package AllThoseTerritories.Player;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;
import Forms.ArmeeTransferScreen;
import Forms.GameScreen;

import java.awt.*;
import java.util.Random;

/**
 * Created by Thomas on 03/01/2016.
 */
public abstract class Player {
    public Player(Color color, boolean player1) {
        this.color = color;
        this.imPlayer1 = player1;
        this.verstärkung = new Armee(player1, 0);
    }

    public Color color;
    public boolean imPlayer1;

    public abstract String update(GameScreen.StateOfPlaying gameState, Territorium target, Random rand);

    public Armee verstärkung;
    public Territorium lastSelected;
    private ArmeeTransferScreen armeeTransfer;
    public boolean movedThisTurn;

    public boolean finishedTransferring() {
        return this.armeeTransfer.isFinished;
    }

    public void transferArmee(String header, String description, Armee armee1, Armee armee2, boolean armee2CanBe0) {
        if (armeeTransfer == null || armeeTransfer.isFinished) {
            armeeTransfer = new ArmeeTransferScreen(header, description, armee1, armee2, armee2CanBe0);
        }
    }

    public void addBonus(int value) {
        this.verstärkung.count += value;
    }
}
