package AllThoseTerritories;

import java.awt.*;
import java.util.List;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Kontinent {
    private List<Territorium>Territories;

    public Kontinent(List<Territorium>territories){
        this.Territories=territories;
    }

    public void Draw(Graphics graphics, Color player1, Color player2){
        if(this.Territories!=null){
            for (Territorium terr:
                 this.Territories) {
                terr.Draw(graphics, player1,player2);
            }
        }
    }
}
