package AllThoseTerritories;

import java.awt.*;
import java.util.List;

import static Main.Tools.BrightenColor;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Landfläche {
    private List<Point> Boundary;

    public Landfläche(List<Point> boundary){
        this.Boundary=boundary;
    }

    public void Draw(Color player1, Color player2){
        if(this.Boundary!=null&&this.Boundary.size()>1) {
            Point last=this.Boundary.get(0);
            for (int i=1;i<this.Boundary.size();i++){
                // TODO: Implement -> Drawline(last,this.Boundary.get(i),10f,Color.DARK_GRAY); // may change the stroke width.
                last=this.Boundary.get(i);
            }

            // TODO: Implement -> FillPolygon(this.Boundary,Color)
            /* TODO: Implement -> IsPointInPolygon(this.Boundary,Cursor)

            (if implemented copy paste the code out of the documentation)

            FillPolygon(this.Boundary,
                    IsPointInPolygon(this.Boundary,Cursor)?
                    (this.Occupation.State==null?
                        BrightenColor(Color.LIGHT_GRAY,0.25):
                        this.Occupation.State?
                            BrightenColor(player1,0.25):
                            BrightenColor(player2,0.25)):
                    (this.Occupation.State==null?
                       Color.LIGHT_GRAY:
                       this.Occupation.State?
                            player1:
                            player2));
             */
        }
    }
}
