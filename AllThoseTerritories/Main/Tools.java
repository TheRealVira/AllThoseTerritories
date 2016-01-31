package Main;

import javax.swing.*;
import java.awt.*;


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

            Point calculatedPoint = new Point(                                                          // Calculate a screenwraped point (if necessary):
                    (int) (Math.abs(point1.getX() - point2.getX()) >= (dimension.getWidth() / 2) ?              // if the difference of distance of the x coordinates is greater than the half dimension width
                            (point1.getX() < point2.getX()) ? (point2.getX() + dimension.getWidth()) :         // and if the second point is greater than the first -> than wrap around to the right side
                                    (dimension.getWidth() / 2 - point2.getX()) : point2.getX()),                      // or wrap around the left side (if p1.x is greater than p2.x) or just take the normal x coordinate of the second point.

                    (int) (Math.abs(point1.getY() - point2.getY()) >= (dimension.getHeight() / 2) ?             // Do the same as above but instead checking the x coordinates and the width,
                            (point1.getY() < point2.getY()) ? (point2.getY() + dimension.getHeight()) :        // we are checking the y coordinates and the height.
                                    (dimension.getHeight() / 2 - point2.getY()) : point2.getY())
            );

            if (calculatedPoint.getY() > dimension.getHeight()) {
                calculatedPoint.setLocation(calculatedPoint.getX(), dimension.getHeight());
            }

            drawLine(graphics, point1, calculatedPoint, strokeWidth, color);
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
