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
    private int Bonus;
    public String Name;

    public int GetBonus(){
        return this.Bonus;
    }

    public void AddTerritory(Territorium territorium){
        if(this.Territories!=null&&territorium!=null){
            this.Territories.add(territorium);
        }
    }

    public Kontinent(String name, int bonus){
        this.Territories=new LinkedList<>();
        this.Name=name;
        this.Bonus = bonus;
    }

    public Kontinent(List<Territorium>territories,String name, int bonus){
        this.Territories=territories;
        this.Bonus = bonus;
        this.Name=name;
    }

    public Kontinent(Territorium territorium,String name,int bonus){
        this.Territories=new LinkedList<>();
        this.Bonus = bonus;
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

    public boolean AllTerritoriesAreSet(){
        if(this.Territories==null){
            return true;
        }

        for (Territorium ter :
             this.Territories) {
            if(!ter.IsSet()){
                return false;
            }
        }

        return true;
    }

    public Territorium GetTerritoriumFromPosition(Point point, JFrame frame){
        if(this.Territories==null){
            return null;
        }

        for (Territorium terr :
                this.Territories) {
            if(terr.ContainsPoint(point,frame)){
                return terr;
            }
        }

        return null;
    }

    public int GetCountOfOwnedTerritories(boolean player1){
        if(this.Territories==null){
            return 0;
        }

        int toRet=0;
        for (Territorium ter :
                this.Territories) {
            toRet+=ter.Occupation.State!=null&&player1==ter.Occupation.State?1:0;
        }

        return toRet;
    }

    public boolean OwnedBy(boolean player1){
        return this.Territories!=null&&GetCountOfOwnedTerritories(player1)==this.Territories.size();
    }
}
