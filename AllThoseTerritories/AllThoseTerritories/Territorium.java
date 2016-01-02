package AllThoseTerritories;

import java.awt.*;
import java.util.List;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Territorium {
    private List<Landfläche>Countries;
    private String Name;
    private Point Capital;
    private List<Territorium>Neighbours;
    private Armee Occupation;

    /*
    Securely create a copy of the capital, so it is secure from beeing chaged outside.
     */
    public Point GetCapital(){
        return (Point)this.Capital.clone();
    }

    public Territorium(List<Landfläche> countries, String name, Point capital, List<Territorium> neighbours, Armee occupation){
        this.Countries=countries;
        this.Name=name;
        this.Capital=capital;
        this.Neighbours=neighbours;
        this.Occupation=occupation;
    }

    public void Draw(Color player1, Color player2){
        // Draw the connections to other Territories
        if(this.Neighbours!=null&&this.Neighbours.size()>1){
            Point last=this.Neighbours.get(0).GetCapital();
            for (int i=1;i<this.Neighbours.size();i++){
                // TODO: Implement -> Drawline(last,this.Boundary.get(i),10f,Color.WHITE); // may change the stroke width.
                last=this.Neighbours.get(i).GetCapital();
            }
        }

        // Draw all countries above the connections.
        if(Countries!=null){
            for (Landfläche c :
                    this.Countries) {
                c.Draw(player1,player2);
            }
        }

        if(this.Occupation!=null) {
            // TODO: Draw the count of the Occupation to the point of the capital.
            // DrawString(this.Occupation.GetCount(),this.Capital);
        }
    }
}
