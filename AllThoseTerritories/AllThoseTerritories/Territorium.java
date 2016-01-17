package AllThoseTerritories;

import AllThoseTerritories.Armee;
import AllThoseTerritories.Landfläche;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static Main.Tools.brightenColor;
import static Main.Tools.drawLine;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Territorium {
    private List<Landfläche> Countries;
    public String Name;
    public Point Capital;
    public List<Territorium> Neighbours;
    public Armee Occupation;

    public Territorium(Landfläche country, String name, Point capital, List<Territorium> neighbours, Armee occupation) {
        this.Countries = new LinkedList<>();
        if (country != null) {
            this.Countries.add(country);
        }
        this.Name = name;
        this.Capital = capital;
        if (neighbours != null) {
            this.Neighbours = neighbours;
        } else {
            this.Neighbours = new LinkedList<>();
        }
        this.Occupation = occupation;
    }

    public void addCountry(Landfläche toAdd) {
        if (this.Countries != null && toAdd != null) {
            this.Countries.add(toAdd);
        }
    }

    public void addNeighour(Territorium terr) {
        if (this.Neighbours != null && !this.Neighbours.contains(terr)) {
            this.Neighbours.add(terr);
            terr.addNeighour(this);
        }
    }

    public void drawConnections(Graphics graphics, Dimension screenDimension) {
        // draw the connections to other Territories
        if (this.Neighbours != null && this.Neighbours.size() > 0) {
            for (int i = 0; i < this.Neighbours.size(); i++) {
                drawLine((Graphics2D) graphics, this.Capital, calculateShortestWay(this.Capital, this.Neighbours.get(i).Capital, screenDimension)/*this.Neighbours.get(i).Capital*/, 2f, Color.WHITE); // may change the stroke width.
            }
        }
    }

    private Point calculateShortestWay(Point point1, Point point2, Dimension dimensions) {
        // TODO: Return the shortest path from point1 to point2. (With screen wrap!)
        return point2;
    }

    public void draw(Graphics graphics, JFrame frame, Color player1, Color player2) {
        // draw all countries above the connections.
        if (Countries != null) {
            boolean hover = false;
            for (Landfläche c :
                    this.Countries) {
                if (c.cursorHover(frame)) {
                    hover = true;
                    break;
                }
            }

            for (Landfläche c :
                    this.Countries) {
                c.draw(graphics, hover ? // That '?' statement will determine the color of the country
                        (this.Occupation.State == null ?
                                brightenColor(Color.LIGHT_GRAY, 0.25) :
                                this.Occupation.State ?
                                        brightenColor(player1, 0.25) :
                                        brightenColor(player2, 0.25)) :
                        (this.Occupation.State == null ?
                                Color.LIGHT_GRAY :
                                this.Occupation.State ?
                                        player1 :
                                        player2));
            }
        }

        if (this.Occupation != null) {
            AttributedString text = new AttributedString(this.Occupation.Count + "");
            text.addAttribute(TextAttribute.BACKGROUND, Color.WHITE, 0, (this.Occupation.Count + "").length());
            graphics.drawString(text.getIterator(), this.Capital.x, this.Capital.y);
        }
    }

    public boolean isSet() {
        return this.Occupation.State != null;
    }

    public void set(Boolean state, int membercount) {
        this.Occupation.State = state;
        this.Occupation.Count = membercount;
    }

    public boolean ContainsPoint(Point point, JFrame frame) {
        if (this.Countries == null) {
            return false;
        }

        for (Landfläche lf :
                this.Countries) {
            if (lf.cursorHover(frame)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsTerritorium(String name) {
        for (Territorium ter :
                this.Neighbours) {
            if (ter.Name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Territorium getNextStepToEnemie(boolean player1, int armieCount) {
        return calculateNextStep(player1, 0, armieCount, null);
    }

    private Territorium calculateNextStep(boolean player1, int layer, int armieCount, List<Territorium> checked) {  // Cool path finding algorithm
        if (checked == null) {
            checked = new LinkedList<>();
            checked.add(this);
        }

        for (Territorium ter :
                this.Neighbours) {
            if (!checked.contains(ter)) {
                if (ter.Occupation.State != null && ter.Occupation.State != player1 && ter.Occupation.Count < armieCount) { // Fouund enemy
                    return this;
                } else {
                    checked.add(ter);
                    Territorium temp = ter.calculateNextStep(player1, layer + 1, armieCount, checked);
                    if (temp != null) {
                        return layer == 0 ? temp : this;
                    }
                }
            }
        }

        return null;
    }
}
