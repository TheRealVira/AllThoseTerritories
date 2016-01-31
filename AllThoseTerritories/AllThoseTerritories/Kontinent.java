package AllThoseTerritories;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class Kontinent {
    private List<Territorium> territories;
    private int bonus;
    public String name;

    public int getBonus() {
        return this.bonus;
    }

    public void addTerritory(Territorium territorium) {
        if (this.territories != null && territorium != null) {
            this.territories.add(territorium);
        }
    }

    public Kontinent(String name, int bonus) {
        this.territories = new LinkedList<>();
        this.name = name;
        this.bonus = bonus;
    }

    public void draw(Graphics graphics, JFrame frame, Color player1, Color player2) {
        if (this.territories != null) {
            for (Territorium terr :
                    this.territories) {
                terr.draw(graphics, frame, player1, player2);
            }
        }
    }

    public void drawConnections(Graphics graphics, Dimension screenDimension) {
        if (this.territories != null) {
            for (Territorium terr :
                    this.territories) {
                terr.drawConnections(graphics, screenDimension);
            }
        }
    }

    public boolean allTerritoriesAreSet() {
        if (this.territories == null) {
            return true;
        }

        for (Territorium ter :
                this.territories) {
            if (!ter.isSet()) {
                return false;
            }
        }

        return true;
    }

    public Territorium getTerritoriumFromPosition(Point point, JFrame frame) {
        if (this.territories == null) {
            return null;
        }

        for (Territorium terr :
                this.territories) {
            if (terr.ContainsPoint(point, frame)) {
                return terr;
            }
        }

        return null;
    }

    public int getCountOfOwnedTerritories(boolean player1) {
        if (this.territories == null) {
            return 0;
        }

        int toRet = 0;
        for (Territorium ter :
                this.territories) {
            toRet += ter.occupation.state != null && player1 == ter.occupation.state ? 1 : 0;
        }

        return toRet;
    }

    public boolean ownedBy(boolean player1) {
        return this.territories != null && getCountOfOwnedTerritories(player1) == this.territories.size();
    }

    @Nullable
    public Boolean getTheOwner() {
        return ownedBy(true) ? Boolean.TRUE : ownedBy(false) ? Boolean.FALSE : null;
    }

    public Territorium getFirstNotOwnedTerritorium() {
        if (this.territories == null || this.territories.size() == 0) {
            return null;
        }

        for (Territorium ter :
                this.territories) {
            if (ter.occupation.state == null) {
                return ter;
            }
        }

        return null; // Nothing found
    }

    public Territorium getHeaviestTerritory(boolean player1) {
        if (this.territories == null || this.territories.size() == 0) {
            return null;
        }

        Territorium toRet = null;
        for (Territorium ter :
                this.territories) {
            if (ter.occupation.state != null && ter.occupation.state == player1 && (toRet == null || toRet.occupation.count < ter.occupation.count)) {
                toRet = ter;
            }
        }

        return toRet;
    }
}
