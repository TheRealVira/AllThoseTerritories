package AllThoseTerritories;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Armee {
    /*
    false: Player2 (could be Pc or human)
    null:  "Verstärkung"
    true:  Player1
     */
    public Boolean state;
    public int count;

    public Armee(Boolean state/*we only need three stats, so a nullable boolean should work wonders*/, int count) {
        this.state = state;
        this.count = count;
    }
}
