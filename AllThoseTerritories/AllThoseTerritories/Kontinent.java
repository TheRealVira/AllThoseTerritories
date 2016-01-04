package AllThoseTerritories;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Kontinent {
    private List<Territorium>Territories;
    private int Verstärkung;
    public String Name;

    public int GetVerstärkung(){
        return this.Verstärkung;
    }

    public void AddTerritory(Territorium territorium){
        if(this.Territories!=null&&territorium!=null){
            this.Territories.add(territorium);
        }
    }

    public Kontinent(String name, int verstärkung){
        this.Territories=new LinkedList<>();
        this.Name=name;
        this.Verstärkung=verstärkung;
    }

    public Kontinent(List<Territorium>territories,String name, int verstärkung){
        this.Territories=territories;
        this.Verstärkung=verstärkung;
        this.Name=name;
    }

    public Kontinent(Territorium territorium,String name,int verstärkung){
        this.Territories=new LinkedList<>();
        this.Verstärkung=verstärkung;
        this.Name=name;

        if(territorium!=null) {
            this.Territories.add(territorium);
        }
    }

    public void Draw(Graphics graphics, JFrame frame, Color player1, Color player2){
        if(this.Territories!=null){
            for (Territorium terr:
                    this.Territories) {
                terr.Draw(graphics, frame, player1,player2);
            }
        }
    }

    public void DrawConnections(Graphics graphics, Dimension screenDimension){
        if(this.Territories!=null) {
            for (Territorium terr :
                    this.Territories) {
                terr.DrawConnections(graphics, screenDimension);
            }
        }
    }
}
