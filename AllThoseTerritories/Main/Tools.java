package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Tools {

    /*
    Returns a new color multiplied by the multiplier.
     */
    public static Color brightenColor(Color color, double multiplier) {
        return new Color(
                (int) Math.round(Math.min(255, color.getRed() + 255 * multiplier)),
                (int) Math.round(Math.min(255, color.getGreen() + 255 * multiplier)),
                (int) Math.round(Math.min(255, color.getBlue() + 255 * multiplier)),
                color.getAlpha()
        );
    }

    public static Color negateColor(Color color) {
        return new Color(
                255 - color.getRed(),
                255 - color.getGreen(),
                255 - color.getBlue(),
                color.getAlpha()
        );
    }

    /*
    Draws a line.
     */
    public static void drawLine(Graphics2D graphics, Point point1, Point point2, float strokeWidth, Color color) {
        if (point1 != null && point2 != null) {
            SetGraphicsHints(graphics, strokeWidth, color);

            graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
        }
    }
    public static void drawLineWithScreenWrap(Graphics2D graphics, Point point1, Point point2, Dimension dimension, float strokeWidth, Color color) {
        if (point1 != null && point2 != null) {
            SetGraphicsHints(graphics, strokeWidth, color);

            if (point2.x >= 0 && point2.y >= 0)
                graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
            else if (point2.x < 0 && point2.y >= 0) {
                graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
                graphics.drawLine((int) point1.getX() + dimension.width, (int) point1.getY(), (int) point2.getX() + dimension.width, (int) point2.getY());
            }
            else if (point2.x >= 0 && point2.y < 0) {
                graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
                graphics.drawLine((int) point1.getX(), (int) point1.getY() + dimension.height, (int) point2.getX(), (int) point2.getY() + dimension.height);
            }
            else if (point2.x < 0 && point2.y < 0) {
                graphics.drawLine((int) point1.getX(), (int) point1.getY(), (int) point2.getX(), (int) point2.getY());
                graphics.drawLine((int) point1.getX() + dimension.width, (int) point1.getY() + dimension.height, (int) point2.getX() + dimension.width, (int) point2.getY() + dimension.height);
            }
        }
    }
    private static void SetGraphicsHints(Graphics2D graphics, float strokeWidth, Color color) {
        if (graphics != null && color != null) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics.setColor(color);
        }
    }

    /*
    Returns the location of the mouse
     */
    public static Point getCursorLocation(JFrame frame) {
        if (frame == null)
            return new Point(0, 0);

        return new Point(
                MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x,
                MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y - 25);
    }

    public static String hexColorCode(Color color) {
        if (color != null) {
            return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }

        return "#000000";
    }
}
