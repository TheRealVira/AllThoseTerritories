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
        this.Color = color;
        this.ImPlayer1 = player1;
        this.Verstärkung = new Armee(player1, 0);
    }

    public Color Color;
    public boolean ImPlayer1;

    public abstract String update(GameScreen.StateOfPlaying gameState, Territorium target, Random rand);

    public Armee Verstärkung;
    public Territorium LastSelected;
    private ArmeeTransferScreen ArmeeTransfer;
    public boolean MovedThisTurn;

    public boolean finishedTransfering() {
        return this.ArmeeTransfer.isFinished;
    }

    public void transferArmee(String header, String description, Armee armee1, Armee armee2, boolean armee2CanBe0) {
        if (ArmeeTransfer == null || ArmeeTransfer.isFinished) {
            ArmeeTransfer = new ArmeeTransferScreen(header, description, armee1, armee2, armee2CanBe0);
        }
    }

    public void addBonus(int value) {
        this.Verstärkung.Count += value;
    }
}
