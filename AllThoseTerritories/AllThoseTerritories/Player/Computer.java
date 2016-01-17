package AllThoseTerritories.Player;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;
import Forms.BattleScreen;

import java.awt.*;
import java.util.Random;

/**
 * Created by Thomas on 03/01/2016.
 */
public class Computer extends Player {
    public Computer(java.awt.Color color, boolean player1) {
        super(color, player1);
    }

    @Override
    public String Update(int gameState, Territorium target, Random rand) {
        switch (gameState) {
            case 1: // Phase Landerwerb
                if (!target.IsSet()) {
                    if (this.LastSelected != null) {
                        this.LastSelected.Set(null, 0);
                    }

                    target.Set(this.ImPlayer1, 1);
                    this.LastSelected = target;
                    return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "claimed " + target.Name + ".";
                }
                break;
            case 2: // Select Country
                MovedThisTurn = false;
                return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "now has " + this.Verstärkung.Count + " reinforcements.";
            case 3: // Select Country
                this.LastSelected = target;
                return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "selected " + target.Name + ".";
            case 4: // Select Country
                if (LastSelected.ContainsTerritorium(target.Name) || target.Name.equals(LastSelected.Name)) {  // Claim
                    if (target == this.LastSelected && target != null && target.Occupation.State == this.ImPlayer1) {
                        target.Occupation.Count += this.Verstärkung.Count;
                        this.Verstärkung.Count = 0;
                        // TODO: Maybe add something smarter...

                        return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.Name + " and the backhand.";
                    } else if (!MovedThisTurn && target.Occupation.State != null && target.Occupation.State == this.Verstärkung.State && target.Occupation.State == LastSelected.Occupation.State) { // Transfer
                        target.Occupation.Count += LastSelected.Occupation.Count - 1;
                        LastSelected.Occupation.Count = 1;
                        // TODO: maybe add something smarter...

                        MovedThisTurn = true;
                        String lastName = LastSelected.Name;
                        LastSelected = null;
                        return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.Name + " and " + lastName + ".";
                    } else if (!MovedThisTurn && target.Occupation.State != null && target.Occupation.State == this.Verstärkung.State && target.Occupation.State == LastSelected.Occupation.State) {
                        return "Cann't move again this turn";
                    } else if (target.Occupation.State != null && target.Occupation.State != LastSelected.Occupation.State) { // Attack
                        Armee attackOfTheTitans = new Armee(this.ImPlayer1, 0);
                        Territorium myTerritorium = target.Occupation.State == this.ImPlayer1 ? target : this.LastSelected; // Get sure, which ones are the players armees
                        if (myTerritorium.Occupation.Count > 1) {
                            Territorium atackedOne = myTerritorium == target ? this.LastSelected : target;
                            attackOfTheTitans.Count = myTerritorium.Occupation.Count - 1;
                            myTerritorium.Occupation.Count = 1;

                            BattleScreen.FastBattle(attackOfTheTitans, atackedOne.Occupation, rand);
                            if (atackedOne.Occupation.Count < 1) {
                                atackedOne.Occupation = attackOfTheTitans;
                            }

                            return myTerritorium.Name + " attacked " + atackedOne.Name + "!";
                        } else {
                            return myTerritorium.Name + " has to less armies and can't fight!";
                        }
                    }
                } else if (!LastSelected.ContainsTerritorium(target.Name)) {
                    return "They aren't neigbors...";
                }

                return "Canceled moves";

        }

        return "";
    }
}
