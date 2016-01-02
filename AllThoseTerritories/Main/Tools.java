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
}
