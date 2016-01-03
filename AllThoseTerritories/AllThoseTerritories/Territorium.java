package AllThoseTerritories;

import java.awt.*;
import java.util.List;

import static Main.Tools.DrawString;
import static Main.Tools.Drawline;

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

    public void Draw(Graphics graphics, Color player1, Color player2){
        // Draw the connections to other Territories
        if(this.Neighbours!=null&&this.Neighbours.size()>1){
            for (int i=1;i<this.Neighbours.size();i++){
                Drawline((Graphics2D)graphics,this.Capital,this.Neighbours.get(i).Capital,10f,Color.WHITE); // may change the stroke width.
            }
        }

        // Draw all countries above the connections.
        if(Countries!=null){
            for (Landfläche c :
                    this.Countries) {
                c.Draw(graphics, player1, player2);
            }
        }

        if(this.Occupation!=null) {
            DrawString(graphics, this.Occupation.GetCount()+"",this.Capital);
        }
    }
}
