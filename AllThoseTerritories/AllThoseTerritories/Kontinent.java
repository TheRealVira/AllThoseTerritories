package AllThoseTerritories;

import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Kontinent {
    private List<Territorium> Territories;
    private int Bonus;
    public String Name;

    public int getBonus() {
        return this.Bonus;
    }

    public void addTerritory(Territorium territorium) {
        if (this.Territories != null && territorium != null) {
            this.Territories.add(territorium);
        }
    }

    public Kontinent(String name, int bonus) {
        this.Territories = new LinkedList<>();
        this.Name = name;
        this.Bonus = bonus;
    }

    public Kontinent(List<Territorium> territories, String name, int bonus) {
        this.Territories = territories;
        this.Bonus = bonus;
        this.Name = name;
    }

    public Kontinent(Territorium territorium, String name, int bonus) {
        this.Territories = new LinkedList<>();
        this.Bonus = bonus;
        this.Name = name;

        if (territorium != null) {
            this.Territories.add(territorium);
        }
    }

    public void draw(Graphics graphics, JFrame frame, Color player1, Color player2) {
        if (this.Territories != null) {
            for (Territorium terr :
                    this.Territories) {
                terr.draw(graphics, frame, player1, player2);
            }
        }
    }

    public void drawConnections(Graphics graphics, Dimension screenDimension) {
        if (this.Territories != null) {
            for (Territorium terr :
                    this.Territories) {
                terr.drawConnections(graphics, screenDimension);
            }
        }
    }

    public boolean allTerritoriesAreSet() {
        if (this.Territories == null) {
            return true;
        }

        for (Territorium ter :
                this.Territories) {
            if (!ter.isSet()) {
                return false;
            }
        }

        return true;
    }

    public Territorium getTerritoriumFromPosition(Point point, JFrame frame) {
        if (this.Territories == null) {
            return null;
        }

        for (Territorium terr :
                this.Territories) {
            if (terr.ContainsPoint(point, frame)) {
                return terr;
            }
        }

        return null;
    }

    public int getCountOfOwnedTerritories(boolean player1) {
        if (this.Territories == null) {
            return 0;
        }

        int toRet = 0;
        for (Territorium ter :
                this.Territories) {
            toRet += ter.Occupation.State != null && player1 == ter.Occupation.State ? 1 : 0;
        }

        return toRet;
    }

    public boolean ownedBy(boolean player1) {
        return this.Territories != null && getCountOfOwnedTerritories(player1) == this.Territories.size();
    }

    @Nullable
    public Boolean getTheOwner() {
        return ownedBy(true) ? Boolean.TRUE : ownedBy(false) ? Boolean.FALSE : null;
    }

    public Territorium getFirstNotOwnedTerretorium() {
        if (this.Territories == null || this.Territories.size() == 0) {
            return null;
        }

        for (Territorium ter :
                this.Territories) {
            if (ter.Occupation.State == null) {
                return ter;
            }
        }

        return null; // Nothing found
    }

    public Territorium getHeaviestTerritory(boolean player1) {
        if (this.Territories == null || this.Territories.size() == 0) {
            return null;
        }

        Territorium toRet = null;
        for (Territorium ter :
                this.Territories) {
            if (ter.Occupation.State != null && ter.Occupation.State == player1 && (toRet == null || toRet.Occupation.Count < ter.Occupation.Count)) {
                toRet = ter;
            }
        }

        return toRet;
    }
}
