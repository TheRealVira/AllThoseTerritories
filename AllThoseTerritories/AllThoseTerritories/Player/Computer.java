package AllThoseTerritories.Player;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Territorium;
import Forms.BattleScreen;
import Forms.GameScreen;

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
                if (lastSelected.containsTerritorium(target.name) || target.name.equals(lastSelected.name)) {  // Claim
                    if (target == this.lastSelected && target != null && target.occupation.state == this.imPlayer1) {
                        target.occupation.count += this.verstärkung.count;
                        this.verstärkung.count = 0;
                        // TODO: Maybe add something smarter...

                        return (this.imPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.name + " and the backhand.";
                    } else if (!movedThisTurn && target.occupation.state != null && target.occupation.state == this.verstärkung.state && target.occupation.state == lastSelected.occupation.state) { // Transfer
                        target.occupation.count += lastSelected.occupation.count - 1;
                        lastSelected.occupation.count = 1;
                        // TODO: maybe add something smarter...

                        movedThisTurn = true;
                        String lastname = lastSelected.name;
                        lastSelected = null;
                        return (this.imPlayer1 ? "Player1 " : "Player2 ") + "is transferring some reinforcements between " + target.name + " and " + lastname + ".";
                    } else if (!movedThisTurn && target.occupation.state != null && target.occupation.state == this.verstärkung.state && target.occupation.state == lastSelected.occupation.state) {
                        return "Can't move again this turn";
                    } else if (target.occupation.state != null && target.occupation.state != lastSelected.occupation.state) { // Attack
                        Armee attackOfTheTitans = new Armee(this.imPlayer1, 0);
                        Territorium myTerritorium = target.occupation.state == this.imPlayer1 ? target : this.lastSelected; // Get sure, which ones are the players armees
                        if (myTerritorium.occupation.count > 1) {
                            Territorium atackedOne = myTerritorium == target ? this.lastSelected : target;
                            attackOfTheTitans.count = myTerritorium.occupation.count - 1;
                            myTerritorium.occupation.count = 1;

                            BattleScreen.fastBattle(attackOfTheTitans, atackedOne.occupation, rand);
                            if (atackedOne.occupation.count < 1) {
                                atackedOne.occupation = attackOfTheTitans;
                            }

                            return myTerritorium.name + " attacked " + atackedOne.name + "!";
                        } else {
                            return myTerritorium.name + " has to less armies and can't fight!";
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
