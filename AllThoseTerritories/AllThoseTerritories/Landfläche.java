package AllThoseTerritories;

import java.awt.*;
import java.util.List;

import static Main.Tools.Drawline;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Landfläche {
    private List<Point> Boundary;

    public Landfläche(List<Point> boundary){
        this.Boundary=boundary;
    }

    public void Draw(Graphics graphics, Color color){
        if(this.Boundary!=null&&this.Boundary.size()>1) {
            /* TODO: Implement -> FillPolygon(this.Boundary,Color)
            FillPolygon(this.Boundary,color);
             */

            Point last=this.Boundary.get(0);
            for (int i=1;i<this.Boundary.size();i++){
                Drawline((Graphics2D)graphics, last, this.Boundary.get(i), 5f, Color.DARK_GRAY); // may change the stroke width.
                last=this.Boundary.get(i);
            }
        }
    }

    public boolean CursorHover(){
        return false; // TODO: Implement -> IsPointInPolygon(this.Boundary,Cursor)
    }
}
