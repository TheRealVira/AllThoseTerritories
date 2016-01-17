package AllThoseTerritories;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static Main.Tools.Drawline;
import static Main.Tools.GetCursorLocation;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Landfläche {
    private Polygon Boundary;

    public Landfläche(List<Point> boundary) {
        if (boundary != null) {
            int x[] = new int[boundary.size()], y[] = new int[boundary.size()];
            for (int i = 0; i < boundary.size(); i++) {
                x[i] = (int) boundary.get(i).getX();
                y[i] = (int) boundary.get(i).getY();
            }
            this.Boundary = new Polygon(x, y, boundary.size());
        }
    }

    public void Draw(Graphics graphics, Color color) {
        if (this.Boundary != null) {
            graphics.setColor(color);
            graphics.fillPolygon(this.Boundary);
            graphics.setColor(Color.DARK_GRAY);
            Graphics2D g2D = ((Graphics2D) graphics);
            g2D.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.drawPolygon(this.Boundary);
        }
    }

    public boolean CursorHover(JFrame frame) {
        return this.Boundary != null && this.Boundary.contains(GetCursorLocation(frame));
    }
}
