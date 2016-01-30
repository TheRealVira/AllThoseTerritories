package AllThoseTerritories;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static Main.Tools.drawLine;
import static Main.Tools.getCursorLocation;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Landfläche {
    private Polygon boundary;

    public Landfläche(List<Point> boundary) {
        if (boundary != null) {
            int x[] = new int[boundary.size()], y[] = new int[boundary.size()];
            for (int i = 0; i < boundary.size(); i++) {
                x[i] = (int) boundary.get(i).getX();
                y[i] = (int) boundary.get(i).getY();
            }
            this.boundary = new Polygon(x, y, boundary.size());
        }
    }

    public void draw(Graphics graphics, Color color) {
        if (this.boundary != null) {
            graphics.setColor(color);
            graphics.fillPolygon(this.boundary);
            graphics.setColor(Color.DARK_GRAY);
            Graphics2D g2D = ((Graphics2D) graphics);
            g2D.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.drawPolygon(this.boundary);
        }
    }

    public boolean cursorHover(JFrame frame) {
        return this.boundary != null && this.boundary.contains(getCursorLocation(frame));
    }
}
