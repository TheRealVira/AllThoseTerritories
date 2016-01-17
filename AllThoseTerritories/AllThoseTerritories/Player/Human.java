package AllThoseTerritories.Player;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;
import Forms.ArmeeTransferScreen;
import Forms.BattleScreen;
import Forms.GameScreen;

import java.awt.*;
import java.util.Random;

/**
 * Created by Thomas on 03/01/2016.
 */
public class Human extends Player {
    public Human(java.awt.Color color, boolean player1) {
        super(color, player1);
    }

    @Override
    public String update(GameScreen.StateOfPlaying gameState, Territorium target, Random rand) {
        switch (gameState) {
            case Expansion: // Phase Landerwerb
                if (!target.isSet()) {
                    if (this.LastSelected != null) {
                        this.LastSelected.set(null, 0);
                    }

                    target.set(this.ImPlayer1, 1);
                    this.LastSelected = target;
                    return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "claimed " + target.Name + ".";
                }
                break;
            case Reinforcing: // Select Country
                MovedThisTurn = false;
                return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "now has " + this.Verstärkung.Count + " reinforcements.";
            case SelectFirstTerritory: // Select Country
                this.LastSelected = target;
                return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "selected " + target.Name + ".";
            case SelectSecondTerritory: // Select Country
                if (LastSelected != null && LastSelected.containsTerritorium(target.Name) || target.Name.equals(LastSelected.Name)) {  // Claim
                    if (target == this.LastSelected && target != null && target.Occupation.State == this.ImPlayer1) {
                        transferArmee(
                                "Set the reinforcement of your backhand or " + target.Name,
                                "Please select, how many will be in " + target.Name + ":",
                                target.Occupation,
                                this.Verstärkung,
                                true);

                        return (this.ImPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.Name + " and the backhand.";
                    } else if (!MovedThisTurn && target.Occupation.State != null && target.Occupation.State == this.Verstärkung.State && target.Occupation.State == LastSelected.Occupation.State) { // Transfer
                        transferArmee(
                                "Set the reinforcement of " + LastSelected.Name + " or " + target.Name,
                                "Please select, how many should be in " + target.Name + ":",
                                target.Occupation,
                                LastSelected.Occupation,
                                false);

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
                            transferArmee("Attack " + atackedOne.Name, "How many armies should attack " + atackedOne.Name + "?", attackOfTheTitans, myTerritorium.Occupation, false);

                            Thread atack = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    while (!finishedTransfering()) {
                                        try {
                                            Thread.sleep(100l); // It was too fast and never got out of the loop, so I had to make it slower...
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    } // Wait till finished selecting

                                    if (attackOfTheTitans.Count != 0) {
                                        BattleScreen.fight(myTerritorium, atackedOne, attackOfTheTitans, atackedOne.Occupation, rand);
                                    }
                                }
                            };
                            atack.start();

                            return myTerritorium.Name + " attacked " + atackedOne.Name + "!";
                        } else {
                            return myTerritorium.Name + " has to less armies and can't fight!";
                        }
                    }
                } else if (!LastSelected.containsTerritorium(target.Name)) {
                    return "They aren't neigbors...";
                }

                return "Canceled moves";

        }

        return "";
    }
}
