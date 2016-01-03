package AllThoseTerritories;

import java.awt.*;
import java.util.List;

import static Main.Tools.BrightenColor;
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
            boolean hover=false;
            for (Landfläche c :
                    this.Countries) {
                if(c.CursorHover()){
                    hover=true;
                    break;
                }
            }

            for (Landfläche c :
                    this.Countries) {
                c.Draw(graphics, hover? // That '?' statement will determine the color of the country
                        (this.Occupation.State==null?
                                BrightenColor(Color.LIGHT_GRAY,0.25):
                                this.Occupation.State?
                                        BrightenColor(player1,0.25):
                                        BrightenColor(player2,0.25)):
                        (this.Occupation.State==null?
                                Color.LIGHT_GRAY:
                                this.Occupation.State?
                                        player1:
                                        player2));;
            }
        }

        if(this.Occupation!=null) {
            DrawString(graphics, this.Occupation.GetCount()+"",this.Capital);
        }
    }
}
