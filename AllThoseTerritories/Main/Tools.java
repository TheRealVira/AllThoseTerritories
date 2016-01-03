package Main;

import java.awt.*;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Tools {

    /*
    Returns a new color multiplied by the multiplier.
     */
    public static Color BrightenColor(Color color, double multiplier){
        return new Color(
                (int)Math.round(Math.min(255,color.getRed()+255*multiplier)),
                (int)Math.round(Math.min(255,color.getGreen()+255*multiplier)),
                (int)Math.round(Math.min(255,color.getBlue()+255*multiplier)),
                color.getAlpha()
        );
    }

    /*
    Draws a string to via the graphics object.
     */
    public static void DrawString(Graphics graphics, String string, Point location){
        if(graphics!=null&&location!=null){
            graphics.drawString(string,(int)location.getX(),(int)location.getY());
        }
    }

    /*
    Draws a line.
     */
    public static void Drawline(Graphics2D graphics, Point point1, Point point2,float strokeWidth, Color color){
        if(graphics!=null&&point1!=null&&point2!=null&&color!=null){
            graphics.setStroke(new BasicStroke(strokeWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
            graphics.setColor(color);
            graphics.drawLine((int)point1.getX(),(int)point1.getY(),(int)point2.getX(),(int)point2.getY());
        }
    }
}
