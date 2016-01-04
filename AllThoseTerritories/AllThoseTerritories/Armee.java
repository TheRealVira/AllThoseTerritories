package AllThoseTerritories;

/**
 * Created by Thomas on 02/01/2016.
 */
public class Armee {
    private Territorium MyTerr; // TODO: maybe delete variable, because it isn't necessary.

    /*
    false: Player2 (could be Pc or human)
    null:  "Verstärkung"
    true:  Player1
     */
    public Boolean State;
    private int Count;

    public int GetCount(){
        return this.Count;
    }

    public Armee(Territorium terr/*if null than Verstärkungstrupp*/,Boolean state/*we only need three stats, so a nullable boolean should work wonders*/, int count){
        this.MyTerr=terr;
        this.State=state;
        this.Count=count;
    }
}
