package AllThoseTerritories;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.LinkedList;
import java.util.List;

import static Main.Tools.brightenColor;
import static Main.Tools.drawLineWithScreenWrap;


public class Territorium {
    private List<Landfläche> countries;
    public String name;
    public Point capital;
    public List<Territorium> neighbours;
    public Armee occupation;

    public Territorium(Landfläche country, String name, Point capital, List<Territorium> neighbours, Armee occupation) {
        this.countries = new LinkedList<>();
        if (country != null) {
            this.countries.add(country);
        }
        this.name = name;
        this.capital = capital;
        if (neighbours != null) {
            this.neighbours = neighbours;
        } else {
            this.neighbours = new LinkedList<>();
        }
        this.occupation = occupation;
    }

    public void addCountry(Landfläche toAdd) {
        if (this.countries != null && toAdd != null) {
            this.countries.add(toAdd);
        }
    }

    public void addNeighour(Territorium terr) {
        if (this.neighbours != null && !this.neighbours.contains(terr)) {
            this.neighbours.add(terr);
            terr.addNeighour(this);
        }
    }

    public void drawConnections(Graphics graphics, Dimension screenDimension) {
        // draw the connections to other Territories, with screen wrap.
        if (this.neighbours != null && this.neighbours.size() > 0) {
            for (int i = 0; i < this.neighbours.size(); i++) {
                drawLineWithScreenWrap((Graphics2D) graphics, this.capital, calculateShortestWay(this.capital, this.neighbours.get(i).capital, screenDimension), screenDimension/*this.neighbours.get(i).capital*/, 2f, Color.WHITE); // may change the stroke width.
            }
        }
    }

    private Point calculateShortestWay(Point point1, Point point2, Dimension dimensions) {
        int distanceXWithout = Math.abs(point2.x - point1.x); // Without Screen Wrap
        int distanceXWith = Math.abs(distanceXWithout - dimensions.width); // With Screen Wrap

        int distanceYWithout = Math.abs(point2.y - point1.y);
        int distanceYWith = Math.abs(distanceYWithout - dimensions.height);

        return new Point(
                distanceXWith < distanceXWithout ? point2.x - dimensions.width : point2.x,
                distanceYWith < distanceYWithout ? point2.y - dimensions.height : point2.y
        );
    }

    public void draw(Graphics graphics, JFrame frame, Color player1, Color player2) {
        // draw all countries above the connections.
        if (countries != null) {
            boolean hover = false;
            for (Landfläche c :
                    this.countries) {
                if (c.cursorHover(frame)) {
                    hover = true;
                    break;
                }
            }

            for (Landfläche c :
                    this.countries) {
                c.draw(graphics, hover ? // That '?' statement will determine the color of the country
                        (this.occupation.state == null ?
                                brightenColor(Color.LIGHT_GRAY, 0.25) :
                                this.occupation.state ?
                                        brightenColor(player1, 0.25) :
                                        brightenColor(player2, 0.25)) :
                        (this.occupation.state == null ?
                                Color.LIGHT_GRAY :
                                this.occupation.state ?
                                        player1 :
                                        player2));
            }
        }

        if (this.occupation != null) {
            AttributedString text = new AttributedString(this.occupation.count + "");
            text.addAttribute(TextAttribute.BACKGROUND, Color.WHITE, 0, (this.occupation.count + "").length());
            graphics.drawString(text.getIterator(), this.capital.x, this.capital.y);
        }
    }

    public boolean isSet() {
        return this.occupation.state != null;
    }

    public void set(Boolean state, int membercount) {
        this.occupation.state = state;
        this.occupation.count = membercount;
    }

    public boolean ContainsPoint(Point point, JFrame frame) {
        if (this.countries == null) {
            return false;
        }

        for (Landfläche lf :
                this.countries) {
            if (lf.cursorHover(frame)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsTerritorium(String name) {
        for (Territorium ter :
                this.neighbours) {
            if (ter.name.equals(name)) {
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
                this.neighbours) {
            if (!checked.contains(ter)) {
                if (ter.occupation.state != null && ter.occupation.state != player1 && ter.occupation.count < armieCount) { // Found enemy
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
