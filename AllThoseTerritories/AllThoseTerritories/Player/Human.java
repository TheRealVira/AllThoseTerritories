package AllThoseTerritories.Player;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;
import Forms.BattleScreen;
import Forms.GameScreen;

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
                    if (this.lastSelected != null) {
                        this.lastSelected.set(null, 0);
                    }

                    target.set(this.imPlayer1, 1);
                    this.lastSelected = target;
                    return (this.imPlayer1 ? "Player1 " : "Player2 ") + "claimed " + target.name + ".";
                }
                break;
            case Reinforcing: // Select Country
                movedThisTurn = false;
                return (this.imPlayer1 ? "Player1 " : "Player2 ") + "now has " + this.verstärkung.count + " reinforcements.";
            case SelectFirstTerritory: // Select Country
                this.lastSelected = target;
                return (this.imPlayer1 ? "Player1 " : "Player2 ") + "selected " + target.name + ".";
            case SelectSecondTerritory: // Select Country
                if (lastSelected != null && lastSelected.containsTerritorium(target.name) || target.name.equals(lastSelected.name)) {  // Claim
                    if (target == this.lastSelected && target != null && target.occupation.state == this.imPlayer1) {
                        transferArmee(
                                "Set the reinforcement of your backhand or " + target.name,
                                "Please select, how many will be in " + target.name + ":",
                                target.occupation,
                                this.verstärkung,
                                true);

                        return (this.imPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.name + " and the backhand.";
                    } else if (!movedThisTurn && target.occupation.state != null && target.occupation.state == this.verstärkung.state && target.occupation.state == lastSelected.occupation.state) { // Transfer
                        transferArmee(
                                "Set the reinforcement of " + lastSelected.name + " or " + target.name,
                                "Please select, how many should be in " + target.name + ":",
                                target.occupation,
                                lastSelected.occupation,
                                false);

                        movedThisTurn = true;
                        String lastName = lastSelected.name;
                        lastSelected = null;
                        return (this.imPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.name + " and " + lastName + ".";
                    } else if (!movedThisTurn && target.occupation.state != null && target.occupation.state == this.verstärkung.state && target.occupation.state == lastSelected.occupation.state) {
                        return "Cann't move again this turn";
                    } else if (target.occupation.state != null && target.occupation.state != lastSelected.occupation.state) { // Attack
                        Armee attackOfTheTitans = new Armee(this.imPlayer1, 0);
                        Territorium myTerritorium = target.occupation.state == this.imPlayer1 ? target : this.lastSelected; // Get sure, which ones are the players armees
                        if (myTerritorium.occupation.count > 1) {
                            Territorium attackedOne = myTerritorium == target ? this.lastSelected : target;
                            transferArmee("Attack " + attackedOne.name, "How many armies should attack " + attackedOne.name + "?", attackOfTheTitans, myTerritorium.occupation, false);

                            Thread attackThread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    while (!finishedTransferring()) {
                                        try {
                                            Thread.sleep(100l); // It was too fast and never got out of the loop, so it had to be slower...
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    } // Wait till finished selecting

                                    if (attackOfTheTitans.count != 0) {
                                        BattleScreen.fight(myTerritorium, attackedOne, attackOfTheTitans, attackedOne.occupation, rand);
                                    }
                                }
                            };
                            attackThread.start();

                            return myTerritorium.name + " attacked " + attackedOne.name + "!";
                        } else {
                            return myTerritorium.name + " has too few armies and can't fight!";
                        }
                    }
                } else if (!lastSelected.containsTerritorium(target.name)) {
                    return "They aren't neighbors...";
                }

                return "Canceled moves";

        }

        return "";
    }
}
