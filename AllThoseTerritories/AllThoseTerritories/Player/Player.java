package AllThoseTerritories.Player;

import java.awt.*;

/**
 * Created by Thomas on 03/01/2016.
 */
public abstract class Player {
    public Player(Color color){
        this.Color=color;
    }

    public Color Color;
    public abstract void Update();
}
