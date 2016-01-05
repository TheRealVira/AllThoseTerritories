package Forms;

import AllThoseTerritories.Kontinent;
import AllThoseTerritories.Player.Player;
import AllThoseTerritories.Territorium;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import static Main.Tools.GetCursorLocation;

/**
 * Created by Thomas on 03/01/2016.
 */
public class GameScreen extends JFrame{
    private JPanel Map=new JPanel();

    private JFrame This=this;
    private List<Kontinent>Continents;
    private Player Player1,Player2;
    public JLabel Console=new JLabel();
    public JLabel Suggestions=new JLabel();
    public JLabel WhosTurn=new JLabel();
    public JLabel Reinforcement=new JLabel();
    private JButton NextRound=new JButton();
    private JButton CancelSelection=new JButton();

    private static final int DELAY=60;
    private boolean Player1sTurn=true;

    private Random Rand;

    /*
    0=Exit
    1=Landerwerb
    2=Get Bonus
    3=Select Terretorium
    4=Select second Terretorium (and do stuff)
     */
    private int StatesOfPlaying=1;

    public int GetGameState(){
        return this.StatesOfPlaying;
    }

    public GameScreen(Player player1, Player player2, List<Kontinent> continents){
        super("AllThoseTerritories");
        setLayout(null);
        setSize(1250,680); // We have to add 30 px to spacing issues with swing...
        setResizable(false);

        this.Map =new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (continents != null) {
                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.DrawConnections(g, new Dimension(getWidth(),getHeight()));
                        }
                    }

                    for (Kontinent c :
                            Continents) {
                        if (c != null) {
                            c.Draw(g, This, Player1.Color, Player2.Color);
                        }
                    }
                }

                NextRound.updateUI();
                WhosTurn.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+(Player1sTurn?"1st Players turn":"2nd Players turn")+"</span></html>");
                Reinforcement.setText("<html><span style=\"color: #000000; background-color: #FFFFFF\">"+"Reinforement:  "+(Player1sTurn?Player1.Verstärkung.Count:Player2.Verstärkung.Count)+"</span></html>");
                WhosTurn.updateUI();
                Reinforcement.updateUI();
                CancelSelection.updateUI();

                Territorium testTerr=GetTerritoriumFromPosition(GetCursorLocation(This));
                if(testTerr!=null){
                    if(testTerr.Occupation.State==null){
                        Suggestions.setText("Claim "+testTerr.Name);
                    }else if(StatesOfPlaying==3) {
                        Suggestions.setText("Select " + testTerr.Name);
                    }else if(StatesOfPlaying==4) {
                        if (testTerr.Occupation.State == Player1sTurn) {
                            Suggestions.setText("Move some reinformance to " + testTerr.Name);
                        } else {
                            Suggestions.setText("Atack " + testTerr.Name);
                        }
                    }
                }
                else{
                    Suggestions.setText("");
                }

                Suggestions.updateUI();
            }
        };

        this.Map.setBounds(0,0,getWidth(),getHeight());
        this.Map.setVisible(true);
        this.Map.setLayout(null);

        this.Continents=continents;
        this.Player1=player1;
        this.Player2=player2;
        this.Console.setBounds(100,this.Map.getHeight()-75,this.Map.getWidth()-200,35);
        this.Suggestions.setBounds(100,this.Map.getHeight()-105,this.Map.getWidth()-200,35);

        this.NextRound.setBounds(this.Map.getWidth()-185,getHeight()-70,150,35);
        this.NextRound.setText("Next Round");
        this.NextRound.setVisible(false);

        this.CancelSelection.setBounds(this.Map.getWidth()-185,getHeight()-105,150,35);
        this.CancelSelection.setText("Cancel selection");
        this.CancelSelection.setVisible(false);

        this.WhosTurn.setBounds(getWidth()/2-35,10,150,35);
        this.Reinforcement.setBounds(getWidth()/2-35,45,150,35);

        this.Map.add(this.NextRound);
        this.Map.add(this.Console);
        this.Map.add(this.WhosTurn);
        this.Map.add(this.Reinforcement);
        this.Map.add(this.Suggestions);
        this.Map.add(this.CancelSelection);

        getContentPane().add(this.Map);

        Rand=new Random();

        this.Map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Territorium testTerr=GetTerritoriumFromPosition(GetCursorLocation(This));
                int selectedTerCount=0;
                if(StatesOfPlaying==1){
                    if(Continents!=null){
                        for (Kontinent kon :
                                Continents) {
                            selectedTerCount += kon.GetCountOfOwnedTerritories(Player1sTurn);
                        }
                    }
                }

                if(testTerr!=null){
                    String bevor=Console.getText();
                    if(Player1sTurn){
                        Console.setText(Player1.Update(StatesOfPlaying,testTerr, Rand));
                    }
                    else{
                        Console.setText(Player2.Update(StatesOfPlaying,testTerr, Rand));
                    }

                    if(!bevor.equals(Console.getText())) {
                        if(StatesOfPlaying==1) {
                            CheckIfAllContinentsAreSet();

                            int check=0;
                            if(Continents!=null){
                                for (Kontinent kon :
                                        Continents) {
                                    check += kon.GetCountOfOwnedTerritories(Player1sTurn);
                                }
                            }

                            if(check!=selectedTerCount) {
                                NextRound.setVisible(true);
                            }
                        }else if(StatesOfPlaying==3){
                            CancelSelection.setVisible(true);
                            StatesOfPlaying++;
                        }else if(StatesOfPlaying==4){
                            StatesOfPlaying=3;
                            CancelSelection.setVisible(false);
                        }
                    }
                }
            }
        });

        this.NextRound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==NextRound){
                    StatesOfPlaying=StatesOfPlaying>1?2:1;
                    Player1sTurn=!Player1sTurn;

                    if(StatesOfPlaying==1) {
                        NextRound.setVisible(false);
                    }

                    Player1.LastSelected=null;
                    Player2.LastSelected=null;

                    if(StatesOfPlaying==2) {
                        if (Player1sTurn) {
                            Player1.AddBonus(GetBonus(Player1sTurn));
                        } else {
                            Player2.AddBonus(GetBonus(Player1sTurn));
                        }

                        StatesOfPlaying++;
                    }
                }
            }
        });

        Thread drawThread=new Thread(){
            public void run(){
               new Timer(100, new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       Map.repaint();
                   }
               }).start();
            }
        };
        drawThread.start();

        this.CancelSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()==CancelSelection){
                    Player1.LastSelected=null;
                    Player2.LastSelected=null;
                    StatesOfPlaying=3;
                    CancelSelection.setVisible(false);
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Territorium GetTerritoriumFromPosition(Point point){
        if(this.Continents==null){
            return null;
        }

        for (Kontinent k :
                this.Continents) {
            Territorium testTerr = k.GetTerritoriumFromPosition(point, this);
            if(testTerr!=null){
                return testTerr;
            }
        }

        return null;
    }

    private void CheckIfAllContinentsAreSet() {
        if (this.Continents != null) {
            for (Kontinent k :
                    this.Continents) {
                if (!k.AllTerritoriesAreSet()) {
                    return;
                }
            }
            this.StatesOfPlaying++;
            this.CancelSelection.setVisible(false);
        }
    }

    public int GetBonus(boolean player1){
        if(this.Continents==null){
            return 0;
        }

        int toRet=0;
        for (Kontinent con :
                this.Continents) {
            // toRet+=con.GetCountOfOwnedTerritories(player1)/3+(con.OwnedBy(player1)?con.GetBonus():0);
            // We changed the rules, so you don't have to have 3 countries to get one bonus.
            // Now you get for every country a bonus (with a 33% chance)
            for (int i=con.GetCountOfOwnedTerritories(player1);i>-1;i--){
                if(Rand.nextInt(3)==1){
                    toRet+=1;
                }
            }

            toRet+=(con.OwnedBy(player1)?con.GetBonus():0);
        }

        return toRet;
    }
}
